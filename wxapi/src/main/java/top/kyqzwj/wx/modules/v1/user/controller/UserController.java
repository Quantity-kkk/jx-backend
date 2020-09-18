package top.kyqzwj.wx.modules.v1.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.helper.UserHelper;
import top.kyqzwj.wx.modules.v1.user.dto.KzUserDto;
import top.kyqzwj.wx.modules.v1.user.service.UserService;

import java.util.HashMap;
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

    @RequestMapping(value="/", method = RequestMethod.PUT)
    public ResponsePayload editUserInfo(@RequestBody KzUserDto userDto){
        //用户资料编辑
        service.updateUser(UserHelper.getUserId(), userDto);
        return ResponsePayload.success();
    }

    @RequestMapping("/visit/{userId}/")
    public ResponsePayload getVisitUserInfo(@PathVariable("userId") String userId){
        //根据用户ID获取用户更详细的基本信息
        return service.getVisitUser(userId);
    }

    /**
     * 头像更改接口
     * */
    @RequestMapping("/avatar/")
    public ResponsePayload avatar(@RequestParam("file") MultipartFile avatarFile){
        String avatarUrl = service.changeAvatar(avatarFile);
        Map ret = new HashMap(4);
        ret.put("avatar", avatarUrl);
        return ResponsePayload.success(ret);
    }

    /**
     * 头像更改接口
     * */
    @RequestMapping("/poster/")
    public ResponsePayload poster(@RequestParam("file") MultipartFile posterFile){
        String avatarUrl = service.changePoster(posterFile);
        Map ret = new HashMap(4);
        ret.put("poster", avatarUrl);
        return ResponsePayload.success(ret);
    }
}
