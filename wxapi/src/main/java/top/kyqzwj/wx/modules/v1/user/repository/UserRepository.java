package top.kyqzwj.wx.modules.v1.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kyqzwj.wx.modules.v1.user.domain.KzUser;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/1 13:42
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UserRepository extends JpaRepository<KzUser, String> {

    KzUser findOneByOpenId(String openId);
}
