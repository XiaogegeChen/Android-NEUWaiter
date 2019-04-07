package com.neuwljs.wallsmalltwo.model;

/**
 * 自定义一个Exception,方便对用到的Exception做统一处理
 */
public class MyException extends Exception {

    /**
     * 异常码，可打印的部分，可以直接展示在ui上
     */
    private String exceptionCode;

    /**
     * 异常详细信息,调试用
     */
    private String exceptionMessage;

    public MyException(String exceptionCode, String exceptionMessage){
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}
