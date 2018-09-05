package com.xiechur.spider.base.data.lock;

public class DistributedLockException extends RuntimeException {

    String message;

    private static final long serialVersionUID = 8142730293296838042L;

    public DistributedLockException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

}
