package top.kyqzwj.wx.modules.v1.message.service.impl;

import net.dongliu.commons.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.attribute.CommonAttribute;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.jpa.repository.NativeSql;
import top.kyqzwj.wx.modules.v1.message.domain.KzFile;
import top.kyqzwj.wx.modules.v1.message.domain.KzMessage;
import top.kyqzwj.wx.modules.v1.message.repository.KzFileRepository;
import top.kyqzwj.wx.modules.v1.message.repository.MessageRepository;
import top.kyqzwj.wx.modules.v1.message.service.MessageService;
import top.kyqzwj.wx.util.*;

import java.util.*;


/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 16:36
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Value("${weChat.basedir}")
    private String baseFileDir;

    @Value("${weChat.baseurl}")
    private String baseUrl;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    KzFileRepository fileRepository;

    @Override
    public ResponsePayload leaveMessage(Map<String, Object> paramMap) {
        KzMessage message = new KzMessage();
        message.setContent((String) paramMap.get("content"));
        message.setTargetUser((String) paramMap.get("targetUser"));
        message.setWriter((String) paramMap.get("writer"));

        List<String> images = (List) paramMap.get("images");
        if(ListUtil.isNotEmpty(images)){
            message.setImageFiles(images.stream().reduce((x,y)->x+"|"+y).get());
        }
        messageRepository.save(message);

        //TODO 异步消息，进行小程序模板消息发送
        return ResponsePayload.success();
    }

    @Override
    public ResponsePayload uploadImage(MultipartFile uploadImage) {
        String fileDestPath = "/msgImage/" +FileUtil.getyyyyMMddPath(new Date());
        String fileName = uploadImage.getOriginalFilename();

        FileUtil.saveFile(uploadImage, baseFileDir + fileDestPath);
        KzFile kzFile = new KzFile();
        kzFile.setFileSize(uploadImage.getSize());

        kzFile.setPath(fileDestPath+"/"+fileName);
        kzFile.setFileType(fileName.substring(fileName.lastIndexOf(".")));
        kzFile.setOriginalName(fileName);

        kzFile = fileRepository.save(kzFile);
        return ResponsePayload.success(kzFile);
    }

    @Override
    public ResponsePayload getMessages(String userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        List<KzMessage> messageList = messageRepository.findByTargetUserOrderByCreateTime(userId, pageable);

        //=== 以下对留言数据进行一下包装然后返回 ===
        List<Map<String, Object>> ret = new ArrayList<>();
        if(ListUtil.isNotEmpty(messageList)){
            //1.获取发布者信息和图片信息的key集合
            Set<String> writerSet = new HashSet<>();
            Set<String> imageSet = new HashSet<>();
            Set<String> msgIdSet = new HashSet<>();

            for(KzMessage message : messageList){
                writerSet.add(message.getWriter());
                msgIdSet.add(message.getId());
                String imageFiles = message.getImageFiles();
                if(StringUtil.isNotEmpty(imageFiles)){
                    String[] split = imageFiles.split(CommonAttribute.SPLIT_VERTICAL);
                    for(String imgId : split){
                        imageSet.add(imgId);
                    }
                }
            }

            //2.查询作者信息
            String queryWriterInfoSql = "select user_id id, avatar, nick_name from kz_user where user_id in ("+ StringUtil.joinForSqlIn(writerSet,",") +")";
            List<Map> writerList = NativeSql.findByNativeSQL(queryWriterInfoSql, null);
            Map<String, Object> writerMap = ListUtil.convertList2Map(writerList, "id");

            //3.获取图片信息
            Map<String, String> imageMap = new HashMap<>(16);
            if(imageSet.size()>0){
                String queryImgSql = "select id, path from kz_file where id in ("+ StringUtil.joinForSqlIn(imageSet,",") +")";
                List<Map> imgList = NativeSql.findByNativeSQL(queryImgSql, null);
                imageMap = ListUtil.convertList2Map(imgList,"id", "path");
            }

            //4.获取评论信息
            String queryCommentSql = "select message_id as id,count(*) as count from kz_comment where message_id in ("+StringUtil.joinForSqlIn(msgIdSet,",")+") GROUP BY message_id";
            List<Map> commentList = NativeSql.findByNativeSQL(queryCommentSql, null);
            Map<String, Object> commentMap =  ListUtil.convertList2Map(commentList,"id","count");

            for (KzMessage msg : messageList){
                Map<String, Object> msgMap = new HashMap<>(16);
                msgMap.put("id", msg.getId());
                msgMap.put("content", msg.getContent());
                msgMap.put("create_time", DateUtil.formatDate(msg.getCreateTime(),DateUtil.format_yyyy_MM_dd_HHmmss));
                //user
                msgMap.put("user", writerMap.get(msg.getWriter()));
                //img
                List<String> images = new ArrayList<>();
                if(StringUtil.isNotEmpty(msg.getImageFiles())){
                    for(String imgId : Arrays.asList(msg.getImageFiles().split(CommonAttribute.SPLIT_VERTICAL))){
                        images.add(baseUrl+imageMap.get(imgId));
                    }
                }
                msgMap.put("images",images);
                //comment
                Object commentCount = commentMap.get(msg.getId());
                if(commentCount!=null){
                    Integer count = MathUtil.toInteger(commentCount);
                    msgMap.put("has_comment",count>0);
                    msgMap.put("comment_count", count);
                }else {
                    msgMap.put("has_comment",false);
                    msgMap.put("comment_count", 0);
                }

                ret.add(msgMap);
            }
        }

        return ResponsePayload.success(ret);
    }

    @Override
    public ResponsePayload getMessage(String messageId) {
        //1.获取留言信息
        KzMessage message = messageRepository.findById(messageId).orElse(null);
        Map ret = new HashMap(16);
        if(message!=null){
            ret.put("id", message.getId());
            ret.put("content", message.getContent());
            ret.put("createTime", DateUtil.formatDate(message.getCreateTime(),DateUtil.format_yyyy_MM_dd_HHmmss));

            //2.获取留言者信息
            String queryWriterInfoSql = "select user_id id, avatar, nick_name from kz_user where user_id=?";
            Map writerInfo = NativeSql.findOneByNativeSQL(queryWriterInfoSql, Arrays.asList(message.getWriter()));
            ret.put("user", writerInfo);

            //3.获取图片地址
            String imageFiles = message.getImageFiles();
            List<String> images = new ArrayList<>();
            if(StringUtil.isNotEmpty(imageFiles)){
                List<String> imgIdList = Arrays.asList(imageFiles.split(CommonAttribute.SPLIT_VERTICAL));
                String queryImgSql = "select id, path from kz_file where id in ("+ StringUtil.joinForSqlIn(imgIdList,",") +")";
                List<Map> imgList = NativeSql.findByNativeSQL(queryImgSql, null);
                Map<String, String> imageMap = ListUtil.convertList2Map(imgList,"id", "path");

                for(String imgId : imgIdList){
                    images.add(baseUrl+imageMap.get(imgId));
                }
            }
            ret.put("images", images);

            //4.获取评论信息
            String queryCommentSql = "select message_id as id,count(*) as count from kz_comment where message_id =? GROUP BY message_id";
            Map commentMap= NativeSql.findOneByNativeSQL(queryCommentSql, Arrays.asList(messageId));
            if(commentMap != null){
                Object commentCount = commentMap.get("count");
                if(commentCount!=null){
                    Integer count = MathUtil.toInteger(commentCount);
                    ret.put("has_comment",count>0);
                    ret.put("comment_count", count);
                }else {
                    ret.put("has_comment",false);
                }
            }

        }else {
            return new ResponsePayload(false, 20001, "留言走丢了...", null);
        }

        return ResponsePayload.success(ret);
    }

    @Override
    public void deleteMessage(String messageId) {
        messageRepository.deleteById(messageId);
    }
}