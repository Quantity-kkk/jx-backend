package top.kyqzwj.wx.modules.v1.comment.service;

import top.kyqzwj.wx.modules.v1.comment.dto.CommentDto;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/9 15:43
 */
public interface CommentService {

    /**
     *  获取留言评论分页数据
     * @param messageId
     * @param page
     * @param size
     * @return
     * */
    List<Map> getMessageComments(String messageId, Integer page, Integer size);

    /**
     *  对留言进行回复评论
     * @param commentDto
     * */
    void addComment(CommentDto commentDto);

    /**
     * 评论删除
     * @param commentId
     * */
    void deleteComment(String commentId);
}
