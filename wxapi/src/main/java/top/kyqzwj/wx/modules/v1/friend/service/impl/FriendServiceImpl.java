package top.kyqzwj.wx.modules.v1.friend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.helper.FileHelper;
import top.kyqzwj.wx.helper.UserHelper;
import top.kyqzwj.wx.jpa.repository.NativeSql;
import top.kyqzwj.wx.modules.v1.file.service.FileService;
import top.kyqzwj.wx.modules.v1.friend.domain.KzUserAssociate;
import top.kyqzwj.wx.modules.v1.friend.repository.FriendRepository;
import top.kyqzwj.wx.modules.v1.friend.service.FriendService;
import top.kyqzwj.wx.util.ListUtil;
import top.kyqzwj.wx.util.StringUtil;

import java.util.*;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 14:26
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    FileService fileService;

    @Override
    public ResponsePayload addFriend(String friendUserId) {
        String currentUserId = UserHelper.getUserId();

        //如果是好友则删除，如果不是则添加
        KzUserAssociate associate = friendRepository.findFirstByUserIdAndFriendId(currentUserId, friendUserId);
        KzUserAssociate associate2 = friendRepository.findFirstByUserIdAndFriendId(friendUserId, currentUserId);
        if(associate!=null){
            friendRepository.deleteAll(Arrays.asList(associate, associate2));
        }else {
            //不能添加自己为好友
            if(StringUtil.isNotEmpty(friendUserId) && friendUserId.equals(currentUserId)){
                return new ResponsePayload(false,20002,"Can Not Add Yourself",null);
            }

            //互为好友,需要加两条记录
            associate = new KzUserAssociate();
            associate.setUserId(currentUserId);
            associate.setFriendId(friendUserId);

            associate2 = new KzUserAssociate();
            associate2.setUserId(friendUserId);
            associate2.setFriendId(currentUserId);
            friendRepository.saveAll(Arrays.asList(associate,associate2));
        }

        return ResponsePayload.success();
    }

    /**
     * 用于好友列表的数据展示，前台需要对数据进行一次处理，获得其昵称首字母所属的类型
     * A-Z,#
     * */
    @Override
    public ResponsePayload getFriends() {
        String userId = UserHelper.getUserId();

        //1.获取用户的好友
        String queryFriendSql = "select user_id as id,nick_name,avatar from kz_user where user_id in (select friend_id from kz_user_associate where user_id=?)";
        List<Map> friendList = NativeSql.findByNativeSQL(queryFriendSql, Arrays.asList(userId));
        //2.对好友信息进行一些简单的转换
        if(ListUtil.isNotEmpty(friendList)){
            //对好友的昵称进行一次首字母发音转换
            friendList.forEach(x->{
                String nickName = (String) x.get("nickName");
                x.put("anchor", StringUtil.getAnchor(nickName));

            });

            //对好友的头像进行一次处理
            Set<String> fileIds = new HashSet<>();
            friendList.forEach(x->{
                String avatar = (String) x.get("avatar");
                if(FileHelper.isFileId(avatar)){
                    fileIds.add(avatar);
                }
            });
            Map<String, String> filePathMap = fileService.getFilePath(fileIds);
            friendList.forEach(x->{
                String avatar = (String) x.get("avatar");
                if(FileHelper.isFileId(avatar)){
                    x.put("avatar",filePathMap.get(avatar));
                }
            });
        }
        //3.根据昵称排序
        friendList.sort(Comparator.comparing(x -> ("" + x.get("nickName"))));

        return ResponsePayload.success(friendList);
    }

}
