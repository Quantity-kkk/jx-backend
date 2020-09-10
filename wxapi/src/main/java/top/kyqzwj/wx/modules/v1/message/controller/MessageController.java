package top.kyqzwj.wx.modules.v1.message.controller;

import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.message.service.MessageService;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 16:26
 */
@RestController()
@RequestMapping("/v1/message")
public class MessageController {

    @Autowired
    MessageService messageService;
    /**
     * 消息留言接口
     * */
    @RequestMapping("/leaveMessage")
    public ResponsePayload leaveMessage(@RequestBody Map<String, Object> map){
        return messageService.leaveMessage(map);
    }

    /**
     * 图片上传接口
     * */
    @RequestMapping("/images")
    public ResponsePayload uploadImage(@RequestParam("file") MultipartFile uploadImage){
        return messageService.uploadImage(uploadImage);
    }


    @RequestMapping("/user/{userId}/")
    public ResponsePayload getMessages(@PathVariable("userId") String userId, @PathParam("page") Integer page, @PathParam("size") Integer size){
        return messageService.getMessages(userId, page, size);
    }
    @RequestMapping("/{messageId}/")
    public ResponsePayload getMessage(@PathVariable("messageId") String messageId){
        return messageService.getMessage(messageId);
    }

    /**
     * 消息删除接口
     * */
    @RequestMapping(value = "/{messageId}/", method = {RequestMethod.DELETE})
    public ResponsePayload deleteComment(@PathVariable("messageId") String messageId){
        messageService.deleteMessage(messageId);
        return ResponsePayload.success();
    }
}
