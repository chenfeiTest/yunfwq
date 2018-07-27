package com.redian.chat.service;

import com.redian.errorcommon.DTO.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("user-service")
public interface UserInterface {

    @GetMapping(value = "/cusers/{openid}")
    Template getCUser(@PathVariable String openid);

    @GetMapping(value = "/busers/{openid}")
    Template getBUser(@PathVariable String openid);

    @GetMapping(value = "/user/{openid}")
    Template getShop(@PathVariable String openid);
}
