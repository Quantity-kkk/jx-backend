package top.kyqzwj.wx.modules.v1.backup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kyqzwj.wx.modules.v1.backup.domain.KzActivityCost;
import top.kyqzwj.wx.modules.v1.backup.domain.KzOtSubsidy;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/14 16:18
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface KzActivityCostRepository extends JpaRepository<KzActivityCost, String> {
}
