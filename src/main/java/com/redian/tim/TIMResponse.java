package com.redian.tim;

import java.io.Serializable;
import java.util.Map;

public class TIMResponse implements Serializable {
    private String status;
    private String message;
    private Integer code;
    private Map<String, Object> data;

    public TIMResponse(Map<String, Object> data) {
        this.data = data;
        this.status = (String) this.data.get("ActionStatus");
        this.message = (String) this.data.get("ErrorInfo");
        this.code = (Integer) this.data.get("ErrorCode");

        this.data.remove("ActionStatus");
        this.data.remove("ErrorInfo");
        this.data.remove("ErrorCode");
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
