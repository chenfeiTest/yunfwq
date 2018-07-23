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
    private IMService imService;

    @PostMapping(name = "用户签名", value = "sign")
    @ApiResponse
    public Object sign(@RequestParam String identifier) {
        return imService.sign(identifier);
    }

    @PostMapping(name = "用户注册", value = "register")
    @ApiResponse
    public Object register(@RequestParam String identifier, @RequestParam String username, @RequestParam String avatarUrl) {
        return imService.register(identifier, username, avatarUrl);
    }
}
