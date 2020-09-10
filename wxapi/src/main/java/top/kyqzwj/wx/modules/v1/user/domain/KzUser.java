package top.kyqzwj.wx.modules.v1.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
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
    @JSONField(name = "id")
    private String userId;

    @Column(name="password", length = 512)
    @JSONField(serialize = false)
    private String password;

    @Column(name="nick_name", length = 64)
    @JSONField(name = "nickName")
    private String nickName;

    /**
     * 用户头像
     * */
    @Column(name="avatar")
    private String avatar;

    @Column(name="signature")
    private String signature;

    @Column(name="male")
    private int male;

    @Column(name="birth")
    @JSONField(format = "yyyy年MM月dd日")
    private Date birth;

    /**
     * 用户封面
     * */
    @Column(name="poster")
    private String poster;

    /**
     * 用户类型：
     * 1-mini program 微信小程序
     * */
    @Column(name="type")
    @JSONField(serialize = false)
    private int type;

    /**微信账号体系相关字段：openId,sessionKey，unionId*/
    @Column(name="open_id")
    @JSONField(serialize = false)
    private String openId;

    @Column(name="session_key")
    @JSONField(serialize = false)
    private String sessionKey;

    @Column(name="union_id")
    @JSONField(serialize = false)
    private String unionId;

    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;
}
