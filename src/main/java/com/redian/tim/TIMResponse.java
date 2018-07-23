package com.redian.tim;

import org.json.JSONObject;

import java.io.Serializable;

public class TIMResponse implements Serializable {
    private String status;
    private String message;
    private Integer code;
    private JSONObject data;

    public TIMResponse(JSONObject ret) {
        this.data = ret;
        this.status = this.data.getString("ActionStatus");
        this.message = this.data.getString("ErrorInfo");
        this.code = this.data.getInt("ErrorCode");

        this.data.remove("ActionStatus");
        this.data.remove("ErrorInfo");
        this.data.remove("ErrorCode");
    }

    public Object get(String key) {
        return this.data.get(key);
    }
}
