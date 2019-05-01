package cn.neuwljs.ocr;

/**
 * ocr识别过程中的异常
 */
public class OCRException extends Exception {
    // 错误码
    private int errorCode;

    // 错误信息
    private String errorMessage;

    // 其它异常
    private Throwable cause;

    private static String genMessage(int errorCode, String errorMessage){
        return "[" + errorCode + "] " + errorMessage;
    }

    OCRException(int errorCode, String errorMessage){
        super(genMessage (errorCode, errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    OCRException(int errorCode, String errorMessage, Throwable cause){
        super(genMessage (errorCode, errorMessage), cause);
        this.cause = cause;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
