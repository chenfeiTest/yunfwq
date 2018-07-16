package com.redian.chat.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "chat_message")
@EntityListeners({AuditingEntityListener.class})
public class Message extends BaseEntity {

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.REMOVE)
    private User sender;      //发送者

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.REMOVE)
    private User receiver;   //接收者

    @Column(length = 500, nullable = false)
    private String content;     //内容

    @Column(length = 2, nullable = false)
    private Integer status;     //状态（0未读，1已读）


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
