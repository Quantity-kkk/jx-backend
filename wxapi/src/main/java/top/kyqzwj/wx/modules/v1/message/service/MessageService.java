package top.kyqzwj.wx.modules.v1.message.service;

import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.facade.ResponsePayload;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 16:35
 */
public interface MessageService {

    /**
     * 用户留言接口
     *
     * @param paramMap 消息参数
     * @return
     * */
    ResponsePayload leaveMessage(Map<String, Object> paramMap);

    /**
     * 图片上传接口
     * 1.将用户上传的图片保存在配置的路径下，按照上传的日期分目录，然后重命名为唯一的uuid。
     * 2将图片信息保存到数据库
     *
     * @param uploadImage 上传文件
     * */
    ResponsePayload uploadImage(MultipartFile uploadImage);

    /**
     *  获取留言分页数据
     * @param userId
     * @param page
     * @param size
     * @return
     * */
    ResponsePayload getMessages(String userId, Integer page, Integer size);

    /**
     * 获取留言详细信息，及其评论等
     * @param messageId
     * @return
     * */
    ResponsePayload getMessage(String messageId);

    /**
     * 留言删除
     * @param messageId
     * */
    void deleteMessage(String messageId);
}
