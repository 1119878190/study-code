package com.study.common.dto.responseprotocal;

/**
 * @author zYh
 */
public class ErrorResponse extends Response {

    private long timestamp;

    private String path;

    public ErrorResponse() {
        setCode(BusinessStatus.SERVER_ERROR.getValue());
        setMessage(BusinessStatus.SERVER_ERROR.getReasonPhrase());
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
