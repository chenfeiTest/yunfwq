package com.redian.chat.service;

import com.redian.chat.domain.PushDTO;
import com.redian.errorcommon.DTO.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("base-service")
public interface WxPushInterface {

    @PostMapping(value = "/wechat/push")
    Template push(@RequestBody PushDTO pushDTO);
}
