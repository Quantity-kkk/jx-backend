package top.kyqzwj.wx.enumobj;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/25 13:46
 */

public enum WeChatUrl {

    /**
     * 微信小程序登录凭证校验接口
     * 返回值：
     * {
     *  openid	string	用户唯一标识
     *  session_key	string	会话密钥
     *  unionid	string	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     *  errcode	number	错误码
     *  errmsg	string	错误信息
     * }
     *
     * errorcode 合法值：
     *  -1	系统繁忙，此时请开发者稍候再试
     *  0	请求成功
     *  40029	code 无效
     *  45011	频率限制，每个用户每分钟100次
     * */
    JS_CODE_2_SESSION("https://api.weixin.qq.com/sns/jscode2session")

    ,GET_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token")
    ,SEND_TEMPLATE_MESSAGE("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send")
    ;

    private String url;

    WeChatUrl() {
    }

    WeChatUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public WeChatUrl setUrl(String url) {
        this.url = url;
        return this;
    }
}
