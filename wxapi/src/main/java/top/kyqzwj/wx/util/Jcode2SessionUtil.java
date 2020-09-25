package top.kyqzwj.wx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.dongliu.requests.Requests;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.kyqzwj.wx.enumobj.WeChatUrl;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.*;

public class Jcode2SessionUtil {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Logger log = LoggerFactory.getLogger(Jcode2SessionUtil.class);
    /**
     * 请求微信后台获取用户数据
     * @param code wx.login获取到的临时code
     * @return 请求结果
     * @throws Exception
     */
    public static Map<String, Object> jscode2session(String appid, String secret, String code, String grantType)throws Exception{
        //定义返回的json对象
        //创建请求通过code换取session等数据
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret",secret);
        params.put("js_code",code);
        params.put("grant_type",grantType);

        String ret = Requests
                .post(WeChatUrl.JS_CODE_2_SESSION.getUrl())
                .params(params)
                .send()
                .readToText();
        Map<String, Object> requestRet = (Map<String, Object>) JSON.parse(ret);
        return requestRet;
    }
    /**
     * 解密用户敏感数据获取用户信息
     * @param sessionKey 数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return
     */
    public static String getUserInfo(String encryptedData,String sessionKey,String iv)throws Exception{
        // 被加密的数据
        byte[] dataByte = decoder.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = decoder.decode(sessionKey);
        // 偏移量
        byte[] ivByte = decoder.decode(iv);
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, "UTF-8");
            log.info(result);
            return result;
        }
        return null;
    }

    /**
     * 获取微信接口调用凭证
     * @param appid
     * @param secret
     * @return 返回String 可转JSON
     */
    public static String getAccessToken(String appid,String secret){
        Map<String, Object> params = new HashMap<>();
        //获取接口调用凭证
        params.put("grant_type","client_credential");
        params.put("appid",appid);
        params.put("secret",secret);
        String ret = Requests.get(WeChatUrl.GET_ACCESS_TOKEN.getUrl()).params(params).send().readToText();

        Map<String, Object> requestRet = (Map<String, Object>) JSON.parse(ret);
        return (String) requestRet.get("access_token");
    }

    /**
     * 发送模板消息
     * @param access_token      接口调用凭证
     * @param touser            接收者（用户）的 openid
     * @param template_id       所需下发的模板消息id
     * @param page              点击模版卡片后跳转的页面，仅限本小程序内的页面。支持带参数，（eg：index?foo=bar）。该字段不填则模版无法跳转
     * @param form_id           表单提交场景下，为submit事件带上的formId；支付场景下，为本次支付的 prepay_id
     * @param data              模版内容，不填则下发空模版。具体格式请参照官网示例
     * @param emphasis_keyword  模版需要放大的关键词，不填则默认无放大
     * @return                  返回String可转JSON
     */
    public static String sendTemplateMessage(String access_token,String touser,String template_id,String page,String form_id,Object data,String emphasis_keyword){
        JSONObject params = new JSONObject();
        params.put("touser",touser);
        params.put("template_id",template_id);
        if (null != page && !"".equals(page)){
            params.put("page",page);
        }
        params.put("form_id",form_id);
        params.put("data",data);
        if (null != emphasis_keyword && !"".equals(emphasis_keyword)){
            params.put("emphasis_keyword",emphasis_keyword);
        }
        //发送请求
        return Requests.post(WeChatUrl.SEND_TEMPLATE_MESSAGE.getUrl()+"?access_token=" + access_token).params(params).send().readToText();
    }

    /**
     * 发送订阅消息
     * @param access_token      接口调用凭证
     * @param touser            接收者（用户）的 openid
     * @param template_id       所需下发的订阅模板id
     * @param page              非必填，点击模版卡片后跳转的页面，仅限本小程序内的页面。支持带参数，（eg：index?foo=bar）。该字段不填则模版无法跳转
     * @param data              必填，模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     * @param miniprogram_state  非必填，跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     * @return                  返回String可转JSON
     */
    public static String sendSubscribeMessage(String access_token,String touser,String template_id,String page, Object data,String miniprogram_state){
        JSONObject params = new JSONObject();
        params.put("touser",touser);
        params.put("template_id",template_id);
        if (null != page && !"".equals(page)){
            params.put("page",page);
        }
        params.put("data",data);
        if (null != miniprogram_state && !"".equals(miniprogram_state)){
            params.put("miniprogram_state",miniprogram_state);
        }

        //发送请求
        return Requests.post(WeChatUrl.SEND_SUBSCRIBE_MESSAGE.getUrl()+"?access_token=" + access_token).params(params).send().readToText();
    }
}
