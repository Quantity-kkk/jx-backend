package top.kyqzwj.wx.modules.v1.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.comment.dto.CommentDto;
import top.kyqzwj.wx.modules.v1.comment.service.CommentService;

import java.util.Map;


/**
 * Description: 评论相关的接口
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/9 15:44
 */
@RestController()
@RequestMapping("/v1/comment")
public class CommentController {

    @Autowired
    CommentService service;

    @RequestMapping("/message/{messageId}/")
    public ResponsePayload getMessageComments(@PathVariable("messageId") String messageId, Integer page, Integer size ){
        return ResponsePayload.success(service.getMessageComments(messageId, page, size));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponsePayload addComment(@RequestBody CommentDto commentDto){
        service.addComment(commentDto);
        return ResponsePayload.success();
    }

    @RequestMapping(value = "/{commentId}/", method = {RequestMethod.DELETE})
    public ResponsePayload deleteComment(@PathVariable("commentId") String commentId){
        service.deleteComment(commentId);
        return ResponsePayload.success();
    }
}
