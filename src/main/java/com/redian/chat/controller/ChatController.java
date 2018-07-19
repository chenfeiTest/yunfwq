package com.redian.chat.controller;

import com.redian.chat.ApiResponse;
import com.redian.chat.domain.Message;
import com.redian.chat.domain.MessageDTO;
import com.redian.chat.domain.MessageQuery;
import com.redian.chat.domain.PageDTO;
import com.redian.chat.service.IMService;
import com.redian.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.tls.tls_sigature.tls_sigature;

import java.io.IOException;

@RestController
@RequestMapping(value = "chat")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private IMService imService;

    @GetMapping(name = "消息列表")
    @ApiResponse
    public Object list(MessageQuery query) {
        Page<Message> messagePage = messageService.findAllByQuery(query);
        PageDTO<Message> pageDTO = new PageDTO<>(messagePage);
        return pageDTO;
    }

    @PostMapping(name = "发送消息")
    @ApiResponse
    public Object send(@RequestBody MessageDTO dto) {
        messageService.sendMessage(dto);
        return null;
    }

    @PostMapping(name = "用户签名", value = "sign")
    @ApiResponse
    public Object sign(@RequestParam String identifier) {
        return imService.sign(identifier);
    }
}
