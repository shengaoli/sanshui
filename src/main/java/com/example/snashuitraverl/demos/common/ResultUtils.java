package com.example.snashuitraverl.demos.common;


/**
 * 返回工具类
 *
 * @author shen_.li
 * shen_.li
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param <T>
     * @param data
     * @param 成功
     * @return
     */
    public static <T> BaseResponse<T> success(T data, String 成功) {
        return new BaseResponse<T>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static<T> BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<T>(errorCode);
    }

    // 作者 [程序员shen_.li](https://shen_.li.icu/)

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
    }



}