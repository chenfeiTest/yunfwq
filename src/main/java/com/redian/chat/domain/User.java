package com.redian.chat.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "chat_user")
@EntityListeners({AuditingEntityListener.class})
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String openId;

    @Column(unique = true, nullable = false)
    private Integer userId;

    @Column(length = 30, nullable = false)
    private String userName;

    @Column(length = 200)
    private String userAvatar;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
