package top.kyqzwj.wx.modules.v1.user.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 20:56
 */
@Data
@ToString
@Entity
@Table(name="kz_user", indexes = {@Index(name="idx_kzuser_openid", columnList = "open_id")})
public class KzUser {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="user_id", length = 32, unique = true, nullable = false)
    private String userId;

    @Column(name="password", length = 512)
    private String password;

    @Column(name="nick_name", length = 64)
    private String nickName;

    @Column(name="avatar", length = 63)
    private String avatar;

    @Column(name="signature", length = 256)
    private String signature;

    @Column(name="male")
    private int male;

    @Column(name="birth")
    private Date birth;

    /**
     * 用户类型：
     * 1-mini program 微信小程序
     * */
    @Column(name="type")
    private int type;

    /**微信账号体系相关字段：openId,sessionKey，unionId*/
    @Column(name="open_id")
    private String openId;

    @Column(name="session_key")
    private String sessionKey;

    @Column(name="union_id")
    private String unionId;
}
