package com.redian.chat.domain;

public class MessageQuery {
    private Long lastId;
    private Long userId1;
    private Long userId2;
    private Integer direction = 1;
    private Integer page = 1;
    private Integer size = 20;

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
