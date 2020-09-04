package top.kyqzwj.wx.modules.v1.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.jpa.repository.NativeSql;
import top.kyqzwj.wx.modules.v1.user.domain.KzUser;
import top.kyqzwj.wx.modules.v1.user.repository.UserRepository;
import top.kyqzwj.wx.modules.v1.user.service.UserService;
import top.kyqzwj.wx.util.BeanUtil;
import top.kyqzwj.wx.util.JSONUtil;
import top.kyqzwj.wx.util.Jcode2SessionUtil;
import top.kyqzwj.wx.util.JwtTokenUtil;

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

    @Autowired
    UserRepository repository;

    @Override
    public ResponsePayload wxLogin(Map<String, Object> data) throws Exception {
        //1.根据code去微信服务器换取appid等信息
        String code = (String) data.get("code");
        String rawData = (String) data.get("raw_data");
        String appId = (String) data.get("appid");
        Map<String, Object> sessionMap = Jcode2SessionUtil.jscode2session(appid, secret, code,"authorization_code");

        //2.根据appid信息判定数据库中是否有用户信息
        String openId = (String) sessionMap.get("openid");
        if(StringUtils.isEmpty(openId)){
            return new ResponsePayload(false, 40001, "用户登录凭证校验失败！", null);
        }
        //3.如果没有用户信息，就插入一条用户信息数据
        KzUser user = repository.findOneByOpenId(openId);
        //无用户，插入用户信息
        if(user==null){
            user = new KzUser();
            Map<String, Object> userInfo = JSONUtil.parseMap(rawData);

            user.setOpenId(openId);

            user.setNickName((String) userInfo.get("nickName"));
            user.setAvatar((String) userInfo.get("avatarUrl"));
            user.setMale((Integer) userInfo.get("gender"));
            //微信小程序用户
            user.setType(1);
            user.setSessionKey((String) sessionMap.get("session_key"));
            user = repository.save(user);
        }
        Map<String, Object> retMap = BeanUtil.toBean(user, Map.class);
        retMap.put("token", JwtTokenUtil.generateToken(user));
        //4.生成用户token，同时返回userDetail信息
        return ResponsePayload.success(retMap);
    }
}
