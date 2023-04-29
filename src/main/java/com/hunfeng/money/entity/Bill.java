package com.hunfeng.money.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author hunfeng
 * @since 2022-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dc_bill")
@ApiModel(value="Bill对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class Bill implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账单金额")
    private Double money;

    private Integer userId;

    @ApiModelProperty(value = "账单详情")
    private String details;

    @ApiModelProperty(value = "账单类型，1（收入）或0（支出）")
    private Integer type;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    @TableField(exist = false)//改字段不是数据库表字段
    @ApiModelProperty(value = "标签中文")
    private String tagDetail;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //出参格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //入参格式化
    @ApiModelProperty(value = "账单记录时间")
    private Date recordTime;

    @TableLogic
    private Integer deleted;
}
