package top.kyqzwj.wx.modules.v1.message.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.kyqzwj.wx.modules.v1.message.service.AsyncMessageService;
import top.kyqzwj.wx.util.DateUtil;
import top.kyqzwj.wx.util.Jcode2SessionUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/24 10:04
 */
@Service
public class AsyncMessageServiceImpl  implements AsyncMessageService {

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Value("${weChat.miniProgramState}")
    private String miniProgramState;


    @Async
    @Override
    public void sendSubscribeMessage(String subject, String author, String touser, String content, String page, Date time, List<String> templateIds) {
        String accessToken = Jcode2SessionUtil.getAccessToken(appid, secret);
        for(String tmpId : templateIds){
            Map<String, Object> dataMap = new HashMap<>(6);
            dataMap.put("thing1",subject);
            dataMap.put("name2",getSubscribeData(author));
            dataMap.put("thing3",getSubscribeData(content));
            dataMap.put("time4",getSubscribeData(DateUtil.formatDate(time, DateUtil.format_yyyy_MM_dd_HHmmss)));
            Jcode2SessionUtil.sendSubscribeMessage(accessToken,
                    touser, tmpId,
                    page,  dataMap,
                    miniProgramState);
        }
    }

    private Map getSubscribeData(Object value){
        Map ret = new HashMap(4);
        ret.put("value", value);
        return ret;
    }
}
