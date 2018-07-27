package com.redian.chat.service;

import com.redian.errorcommon.DTO.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("user-service")
public interface UserInterface {

    @PostMapping(value = "/cusers/{openid}")
    Template getCUser(@PathVariable String openid);

    @PostMapping(value = "/busers/{openid}")
    Template getBUser(@PathVariable String openid);

    @PostMapping(value = "/user/{openid}")
    Template getShop(@PathVariable String openid);
}
