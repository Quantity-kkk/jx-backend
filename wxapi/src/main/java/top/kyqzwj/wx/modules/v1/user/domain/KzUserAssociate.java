package top.kyqzwj.wx.modules.v1.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: 用户好友
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/9 13:47
 */
@Data
@ToString
@Entity
@Table(name="kz_user_associate", indexes = {@Index(name="idx_kzua_userid", columnList = "user_id"),@Index(name="idx_kzua_friendid", columnList = "friend_id")})
public class KzUserAssociate {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id", length = 32, unique = true, nullable = false)
    private String id;

    @Column(name="user_id")
    private String userId;

    @Column(name="friend_id")
    private String friendId;

    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;
}
