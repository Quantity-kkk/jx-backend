package top.kyqzwj.wx.modules.v1.comment.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: 留言中的评论信息
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 15:29
 */
@Data
@ToString
@Entity
@Table(name="kz_comment", indexes = {@Index(name="idx_kzcomment_messageid", columnList = "message_id")})
public class KzComment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id", length = 32, unique = true, nullable = false)
    private String id;

    @Column(name="message_id", length = 32)
    private String messageId;

    /**
     * 评论和评论之间还可以互相回复，所以添加了这一属性备用
     * */
    @Column(name="comment_id", length = 32)
    private String commentId;

    @Column(name="writer", length = 32, nullable = false)
    private String writer;

    @Column(name="reply", length = 32)
    private String reply;

    @Column(name="content", length = 512, nullable = false)
    private String content;

    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;
}
