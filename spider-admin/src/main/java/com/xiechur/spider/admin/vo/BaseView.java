package com.xiechur.spider.admin.vo;

public class BaseView<E> {

    private int code = 200;

    private String message;

    private E data;

    public BaseView() {
        super();
    }

    public BaseView(E data) {
        super();
        this.data = data;
    }

    public BaseView(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public BaseView(int code, E data) {
        super();
        this.code = code;
        this.data = data;
    }

    public BaseView(int code, String message, E data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseView [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
