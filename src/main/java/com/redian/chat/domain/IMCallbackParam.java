package com.redian.chat.domain;

public class IMCallbackParam {
    private String SdkAppid;
    private String CallbackCommand;
    private String contenttype;
    private String ClientIP;
    private String OptPlatform;

    public String getSdkAppid() {
        return SdkAppid;
    }

    public void setSdkAppid(String sdkAppid) {
        SdkAppid = sdkAppid;
    }

    public String getCallbackCommand() {
        return CallbackCommand;
    }

    public void setCallbackCommand(String callbackCommand) {
        CallbackCommand = callbackCommand;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getClientIP() {
        return ClientIP;
    }

    public void setClientIP(String clientIP) {
        ClientIP = clientIP;
    }

    public String getOptPlatform() {
        return OptPlatform;
    }

    public void setOptPlatform(String optPlatform) {
        OptPlatform = optPlatform;
    }
}
