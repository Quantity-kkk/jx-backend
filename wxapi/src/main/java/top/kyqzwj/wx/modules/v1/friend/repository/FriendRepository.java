package top.kyqzwj.wx.modules.v1.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kyqzwj.wx.modules.v1.friend.domain.KzUserAssociate;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 14:35
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface FriendRepository extends JpaRepository<KzUserAssociate, String> {
    KzUserAssociate findFirstByUserIdAndFriendId(String userId, String friendId);
}
