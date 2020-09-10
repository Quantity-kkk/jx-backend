package top.kyqzwj.wx.modules.v1.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.kyqzwj.wx.jpa.repository.NativeSql;
import top.kyqzwj.wx.modules.v1.comment.domain.KzComment;
import top.kyqzwj.wx.modules.v1.comment.dto.CommentDto;
import top.kyqzwj.wx.modules.v1.comment.repository.CommentRepository;
import top.kyqzwj.wx.modules.v1.comment.service.CommentService;
import top.kyqzwj.wx.util.BeanUtil;
import top.kyqzwj.wx.util.DateUtil;
import top.kyqzwj.wx.util.ListUtil;
import top.kyqzwj.wx.util.StringUtil;

import java.util.*;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/9 15:43
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Map> getMessageComments(String messageId, Integer page, Integer size){
        String queryCommentSql = "select id,content,create_time,reply,writer from kz_comment where message_id = ? order by create_time";
        List<Map> commentList = NativeSql.findByNativeSQL(queryCommentSql, Arrays.asList(messageId), page-1, size);

        if(ListUtil.isNotEmpty(commentList)){
            //处理用户头像等信息
            Set<String> userIdSet = new HashSet<>();
            commentList.forEach(x->{
                String writer = (String) x.get("writer");
                if(StringUtil.isNotEmpty(writer)){
                    userIdSet.add(writer);
                }
                String reply = (String) x.get("reply");
                if(StringUtil.isNotEmpty(reply)){
                    userIdSet.add(reply);
                }
            });

            String queryWriterInfoSql = "select user_id id, avatar, nick_name from kz_user where user_id in ("+StringUtil.joinForSqlIn(userIdSet, ",")+")";
            List<Map> userList = NativeSql.findByNativeSQL(queryWriterInfoSql, null);
            Map<String, Object> writerMap = ListUtil.convertList2Map(userList, "id");

            commentList.forEach(x->{
                String writer = (String) x.get("writer");
                if(StringUtil.isNotEmpty(writer)){
                    x.put("user", writerMap.get(writer));
                }
                String reply = (String) x.get("reply");
                if(StringUtil.isNotEmpty(reply)){
                    x.put("reply", writerMap.get(reply));
                }

                //处理时间
                x.put("createTime", DateUtil.formatDate((Date) x.get("createTime"),DateUtil.format_yyyy_MM_dd_HHmmss));
            });
        }

        return commentList;
    }

    @Override
    public void addComment(CommentDto commentDto) {
        KzComment comment = new KzComment();
        BeanUtil.beanCopy(commentDto, comment, false);
        //jwt授权环境下的当前用户获取
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setWriter(userId);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }

}
