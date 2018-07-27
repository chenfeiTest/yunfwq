package com.redian.chat.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by york on 2018/6/28.
 */
public class PushDTO {

    private String templateId;

    public List<String> openids;

    public List<Map<String,String>> data;

    public String page;

    private String color;

    public String emphasisKeyword;

    private int type;

    private int platform;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<String> getOpenids() {
        return openids;
    }

    public void setOpenids(List<String> openids) {
        this.openids = openids;
    }

    public List<Map<String,String>> getData() {
        return data;
    }

    public void setData(List<Map<String,String>> data) {
        this.data = data;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }
}
