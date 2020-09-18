package top.kyqzwj.wx.modules.v1.user.service;

import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.user.dto.KzUserDto;

import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/26 17:34
 */
public interface UserService {

    /**
     * 微信小程序用户登录方法
     *
     * @param data 前台传来的基础信息，包含登录的code，用户开放信息
     * @return top.kyqzwj.wx.facade.ResponsePayload 用户token信息
     * @exception Exception 微信调用过程中的未知错误
     *
     * */
    ResponsePayload wxLogin(Map<String, Object> data) throws Exception;


    /**
     * 获取用户基本信息，刷新token等
     *
     * @param userId
     * @return top.kyqzwj.wx.facade.ResponsePayload
     * */
    ResponsePayload getUser(String userId);

    /**
     * 获取浏览用户基本信息
     *
     * @param userId
     * @return top.kyqzwj.wx.facade.ResponsePayload
     * */
    ResponsePayload getVisitUser(String userId);

    /**
     * 更新用户信息
     * @param userId
     * @param userDto
     * */
    void updateUser(String userId, KzUserDto userDto);

    /**
     * 修改用户头像
     * @param avatarFile
     * @return
     * */
    String changeAvatar(MultipartFile avatarFile);

    /**
     * 修改用户封面
     * @param posterFile
     * @return
     * */
    String changePoster(MultipartFile posterFile);
}
