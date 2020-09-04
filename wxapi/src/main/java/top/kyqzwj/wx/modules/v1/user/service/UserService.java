package top.kyqzwj.wx.modules.v1.user.service;

import top.kyqzwj.wx.facade.ResponsePayload;

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
}
