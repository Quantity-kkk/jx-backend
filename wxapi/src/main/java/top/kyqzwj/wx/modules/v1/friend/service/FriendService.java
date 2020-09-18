package top.kyqzwj.wx.modules.v1.friend.service;

import top.kyqzwj.wx.facade.ResponsePayload;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 14:26
 */
public interface FriendService {

    /**
     * 添加好友接口
     * @param friendUserId
     * @return
     * */
    ResponsePayload addFriend(String friendUserId);

    /**
     * 获得好友列表接口
     * @return
     * */
    ResponsePayload getFriends();
}
