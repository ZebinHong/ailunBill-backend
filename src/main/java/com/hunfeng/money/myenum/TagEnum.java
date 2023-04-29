package com.hunfeng.money.myenum;


public enum TagEnum {
    CANYIN(1, "餐饮"),
    JIAOTONG(2, "交通"),
    FUSHI(3, "服饰"),
    GOUWU(4, "购物"),
    FUWU(5, "服务"),
    JIAOYU(6, "教育"),
    YULE(7, "娱乐"),
    QUANBU(8,"全部"),
    WEIXIN(9,"微信"),
    YUYIN(10, "语音"),
    QITA(11, "其他"),
    ALIPAY(12, "支付宝");
    public int id;
    public String name;
    TagEnum(int id, String name){
        this.id = id;
        this.name = name;
    }
}
