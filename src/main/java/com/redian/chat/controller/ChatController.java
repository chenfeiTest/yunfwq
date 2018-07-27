package com.redian.chat.controller;

import com.redian.chat.ApiResponse;
import com.redian.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping(name = "用户签名", value = "sign")
    @ApiResponse
    public Object sign(@RequestParam String identifier) {
        return chatService.sign(identifier);
    }

    @PostMapping(name = "用户注册", value = "register")
    @ApiResponse
    public Object register(@RequestParam String identifier, @RequestParam String username, @RequestParam String avatarUrl) {
        return chatService.register(identifier, username, avatarUrl);
    }
}
