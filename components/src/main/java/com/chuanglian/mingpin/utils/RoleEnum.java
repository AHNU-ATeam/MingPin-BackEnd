package com.chuanglian.mingpin.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    PRINCIPAL(1, "principal"),
    TEACHER(2, "teacher"),
    STUDENT(3, "student");

    private final int code;
    private final String type;

    // 根据code获取对应的枚举实例
    public static RoleEnum fromCode(int code) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    // 根据type获取对应的枚举实例
    public static RoleEnum fromType(String type) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.type.equalsIgnoreCase(type)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid type: " + type);
    }

}
