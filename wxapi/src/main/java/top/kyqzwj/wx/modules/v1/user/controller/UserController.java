package top.kyqzwj.wx.modules.v1.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.user.service.UserService;

import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/25 14:49
 */
@RestController()
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService service;

    @ResponseBody
    @RequestMapping("")
    public String index(){
        return "idsdx";
    }
    /**
     * 微信小程序登录接口
     */
    @PostMapping("/wxLogin")
    public ResponsePayload wxLogin(@RequestBody Map<String, Object> param) throws Exception {
        return service.wxLogin(param);
    }

    @RequestMapping("/{userId}/")
    public ResponsePayload getUserInfo(@PathVariable("userId") String userId){
        //根据用户ID获取用户更详细的基本信息
        return service.getUser(userId);
    }

    @RequestMapping("/visit/{userId}/")
    public ResponsePayload getVisitUserInfo(@PathVariable("userId") String userId){
        //根据用户ID获取用户更详细的基本信息
        return service.getVisitUser(userId);
    }
}
