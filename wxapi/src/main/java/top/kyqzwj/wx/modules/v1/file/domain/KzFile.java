package top.kyqzwj.wx.modules.v1.file.domain;

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
 * Description: 留言中的图片信息
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 15:27
 */
@Data
@ToString
@Entity
@Table(name="kz_file")
public class KzFile {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id", length = 32, unique = true, nullable = false)
    private String id;

    @Column(name="path", length = 255)
    private String path;

    @Column(name="file_size")
    private Long fileSize;

    @Column(name="file_type")
    private String fileType;

    /**创建时间*/
    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;

    /**原始文件名称*/
    @Column(name="original_name", length = 255)
    private String originalName;
}
