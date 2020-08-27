package top.kyqzwj.wx.modules.v1.user.service;

import top.kyqzwj.wx.facade.ResponsePayload;

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
     * @param code 前台传来的wx code
     * @return top.kyqzwj.wx.facade.ResponsePayload 用户token信息
     * @exception Exception 微信调用过程中的未知错误
     *
     * */
    ResponsePayload wxLogin(String code) throws Exception;
}
