package com.crazy.dashboard.model.enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 教师认证状态枚举类
 * Created by huangshuoying on 2017/4/1.
 */
public enum ExchangeEnum {
    CRYPTOPIA("cryptopia", "cryptopiaService"),
    OKEX("okex", "okexService"),
    HUOBI("火币", "huobiService"),
    UNKNOWN("", "未知");

    private String code;
    private String description;

    ExchangeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code 直接获取描述信息
     * @param code Integer 数字编号
     * @return String 对应枚举的描述，如果不是有效枚举返回“未知”
     */
    public static String getDescription(String code){
        return valueOf(code).getDescription();
    }

    public static List<ExchangeEnum> getEnumList() {
        List<ExchangeEnum> enumList = new ArrayList<>();
        Collections.addAll(enumList, values());
        enumList.remove(UNKNOWN);
        return enumList;
    }
}
