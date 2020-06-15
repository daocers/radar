package cc.wdcloud.common;

/**
 * 接口返回信息包装类
 *
 * @author daocers
 * @create 2017/10/22 0:04
 */
public class Resp<T> {
    private static final String SUCCESS_MESSAGE = "success";
    private static final Integer SUCCESS_CODE = 0;

    private Boolean result;
    private Integer code;
    private String message;


    private T data;

    private Resp() {

    }

    private Resp(Boolean result, Integer code, String message, T data) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getResult() {
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }


    public static <T> Resp<T> success(T data) {
        return new Resp<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static Resp success() {
        return new Resp(true, SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static Resp fail(Integer code, String message) {
        return new Resp(false, code, message, null);
    }

    public static Resp fail() {
        return new Resp(false, null, null, null);
    }

    public static Resp fail(String message){
        return new Resp(false, null, message, null);
    }

}
