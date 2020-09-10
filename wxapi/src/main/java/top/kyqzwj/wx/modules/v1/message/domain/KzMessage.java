package top.kyqzwj.wx.modules.v1.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
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
 * @Date 2020/9/8 15:24
 */
@Data
@ToString
@Entity
@Table(name="kz_message", indexes = {@Index(name="idx_kzmsg_targetusser", columnList = "target_user")})
public class KzMessage {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id", length = 32, unique = true, nullable = false)
    @JSONField(name = "id")
    private String id;

    @Column(name="target_user", length = 64)
    private String targetUser;

    @Column(name="writer", length = 64)
    private String writer;

    @Column(name="content", length = 512)
    private String content;

    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;

    /**留言包含的图片，按照顺序使用竖线分隔即可，不再单独建表存储*/
    @Column(name="image_files", length = 512)
    private String imageFiles;
}
