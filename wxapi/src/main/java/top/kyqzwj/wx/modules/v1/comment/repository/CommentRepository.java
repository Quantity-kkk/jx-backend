package top.kyqzwj.wx.modules.v1.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import top.kyqzwj.wx.modules.v1.comment.domain.KzComment;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/9 15:45
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface CommentRepository extends JpaRepository<KzComment, String> {
}
