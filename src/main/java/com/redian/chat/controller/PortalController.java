package com.redian.chat.controller;

import com.redian.chat.ApiResponse;
import com.redian.chat.domain.IMCallbackParam;
import com.redian.chat.domain.PushDTO;
import com.redian.chat.service.WxPushInterface;
import com.redian.errorcommon.DTO.Template;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "portal")
public class PortalController {

    private static Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    WxPushInterface wxPushInterface;

    @PostMapping(name = "IM回调")
    @ApiResponse
    public Object portal(@RequestBody String body, IMCallbackParam param) {
        JSONObject json = new JSONObject(body);
        logger.debug("IM回调:" + json);

        if (param.getCallbackCommand().equals("C2C.CallbackAfterSendMsg")) {//发单聊消息之后回调
            String sender = json.getString("From_Account");
            String receiver = json.getString("To_Account");

            String key = "im_callback_status_" + receiver;
            String status = redisTemplate.opsForValue().get(key);
            if (status == null || status.equals("logout")) {
                List<String> openIds = new ArrayList<>();
                openIds.add(receiver);

                List<Map<String, String>> data = new ArrayList<>();
                Map<String, String> info = new HashMap<>();
                info.put("回复人", sender);
                info.put("回复类型", "");
                info.put("回复时间", "");
                info.put("内容", "你收到一条聊天消息");
                data.add(info);

                PushDTO dto = new PushDTO();
                dto.setTemplateId("TpozlnOzzxAhiuS-5T-eaRNOLXReZTeqIjLTa3LiHqE");
                dto.setOpenids(openIds);
                dto.setPage("pages/user/userIndex?redirect=conversation-list");
                dto.setData(data);

                logger.debug("微信推送请求:" + new JSONObject(dto));
                Template template = wxPushInterface.push(dto);
                logger.debug("微信推送响应:" + template.getCode() + "  " + template.getMsg() + " " + template.getData());
            }
        } else if (param.getCallbackCommand().equals("State.StateChange")) {//状态变更回调
            JSONObject info = json.getJSONObject("Info");
            String action = info.getString("Action");
            String user = info.getString("To_Account");

            String key = "im_callback_status_" + user;
            if (action.equals("Logout")) {
                redisTemplate.opsForValue().set(key, "logout", 1, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, "login");
            }
        }
        return null;
    }
}
