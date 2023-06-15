package com.hunfeng.money.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRespDto {
    private Integer id;
    private Double money;
    private Integer userId;
    private String details;
    private Integer type;
    private String tagDetail;
    private String iconClass;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //出参格式化
    private Date recordTime;
}
