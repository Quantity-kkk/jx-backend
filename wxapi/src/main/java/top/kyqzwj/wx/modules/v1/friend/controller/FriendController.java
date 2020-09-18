package top.kyqzwj.wx.modules.v1.friend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.friend.service.FriendService;

import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 14:27
 */
@RestController()
@RequestMapping("/v1/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponsePayload addFriend(@RequestBody Map params){
        return friendService.addFriend((String) params.get("friendUserId"));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponsePayload getFriend(){
        return friendService.getFriends();
    }


}
