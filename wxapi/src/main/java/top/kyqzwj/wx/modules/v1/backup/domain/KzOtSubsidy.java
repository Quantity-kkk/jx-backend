package top.kyqzwj.wx.modules.v1.backup.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/14 14:20
 */
@Data
@ToString
@Entity
@Table(name="kz_ot_subsidy", indexes = {@Index(name="idx_os_overdate", columnList = "over_date")})
public class KzOtSubsidy {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id", length = 32, unique = true, nullable = false)
    private String id;

    @Column(name="over_date")
    private Date overDate;

    @Column(name="week", length = 32)
    private String week;

    @Column(name="people_name", length = 512)
    private String peopleName;

    @Column(name="cost")
    private BigDecimal cost;

    @Column(name="create_by", length = 32)
    private String createBy;


    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Date createTime;
}
