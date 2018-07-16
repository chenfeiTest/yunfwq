package com.redian.chat.controller;

import com.redian.chat.ApiResponse;
import com.redian.chat.domain.Message;
import com.redian.chat.domain.MessageDTO;
import com.redian.chat.domain.MessageQuery;
import com.redian.chat.domain.PageDTO;
import com.redian.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "chat")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(name = "消息列表", value = "list")
    @ApiResponse
    public Object list(@RequestBody MessageQuery query) {
        Page<Message> messagePage = messageService.findAllByQuery(query);
        PageDTO<Message> pageDTO = new PageDTO<>(messagePage);
        return pageDTO;
    }

    @RequestMapping(name = "发送消息", value = "send")
    @ApiResponse
    public Object send(@RequestBody MessageDTO dto) {
        messageService.sendMessage(dto);
        return null;
    }
}
