package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;//响应码，1 代表成功; 0 代表失败
    private String massage;  //响应信息 描述字符串
    private T data; //返回的数据

    //增删改 成功响应
    public static Result success() {
        return new Result(1, "success", null);
    }

    public static Result success(String message) {
        return new Result(1, message, null);
    }

    //查询 成功响应
    public static <E> Result<E> success(E data) {
        return new Result(1, "success", data);
    }

    public static <E> Result<E> success(String message, E data) {
        return new Result(1, message, data);
    }

    //失败响应
    public static Result error(String massage) {
        return new Result(0, massage, null);
    }
}
