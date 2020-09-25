package top.kyqzwj.wx.modules.v1.message.service;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/24 10:05
 */
public interface AsyncMessageService{

    /**
     * 发送小程序订阅消息给用户
     * @param subject 消息主题
     * @param author 消息作者
     * @param touser 消息推送目标
     * @param content 消息内容
     * @param page 跳转页面
     * @param time 消息产生时间
     * @param templateIds 模板id
     * */
    void sendSubscribeMessage(String subject, String author, String touser, String content, String page, Date time, List<String> templateIds);
}
