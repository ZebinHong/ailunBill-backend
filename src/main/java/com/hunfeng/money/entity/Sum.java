package com.hunfeng.money.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sum {
    int day;
    int month;
    int year;
    double total;  //总金额
}
