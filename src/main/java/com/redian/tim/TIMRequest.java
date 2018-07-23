package com.redian.tim;

import org.json.JSONObject;

public class TIMRequest {
    private String action;
    private JSONObject data;

    public TIMRequest(String action) {
        this.action = action;
        this.data = new JSONObject();
    }

    public TIMRequest(String action, JSONObject data) {
        this.action = action;
        this.data = data;
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public String getAction() {
        return action;
    }

    public Object getData() {
        return data.toMap();
    }
}
