package top.kyqzwj.wx.modules.v1.user.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.jpa.repository.NativeSQL;
import top.kyqzwj.wx.modules.v1.user.service.UserService;
import top.kyqzwj.wx.util.Jcode2SessionUtil;
import top.kyqzwj.wx.util.JwtTokenUtil;

import java.util.List;
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
@Service
public class UserServiceImpl implements UserService {

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Override
    public ResponsePayload wxLogin(String code) throws Exception {
        //1.根据code去微信服务器换取appid等信息
        Map<String, Object> sessionMap = Jcode2SessionUtil.jscode2session(appid, secret, code,"authorization_code");

        //2.根据appid信息判定数据库中是否有用户信息
        String openId = (String) sessionMap.get("openid");
        if(StringUtils.isEmpty(openId)){
            return new ResponsePayload(false, 40001, "获取用户微信session失败！", null);
        }
        //3.如果没有用户信息，就插入一条用户信息数据
        int count = NativeSQL.findCountByNativeSQL("select count(*) from kz_user", null);
        //4.生成用户token返回
        return ResponsePayload.success(JwtTokenUtil.generateToken("sessionInfo"));
    }
}
