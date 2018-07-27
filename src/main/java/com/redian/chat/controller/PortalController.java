package com.redian.chat.controller;

import com.redian.chat.ApiResponse;
import com.redian.chat.domain.IMCallbackParam;
import com.redian.chat.domain.PushDTO;
import com.redian.chat.service.PushInterface;
import com.redian.chat.service.UserInterface;
import com.redian.errorcommon.DTO.Template;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "portal")
public class PortalController {

    private static Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    UserInterface userInterface;

    @Autowired
    PushInterface pushInterface;

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
            logger.debug("用户在线状态:" + status);
            if (status == null || status.equals("logout")) {
                Template templateB = userInterface.getShop(sender);
                logger.debug("获取店铺信息:" + templateB.getCode() + " " + templateB.getMsg() + " " + templateB.getData());
                if (templateB.getCode() != 200 || templateB.getData() == null) {
                    return null;
                }

                Map dataMap = (Map<?, ?>) templateB.getData();
                if (dataMap.size() == 0) {
                    return null;
                }

                JSONObject tempInfo = new JSONObject(dataMap);
                JSONObject userInfo = tempInfo.getJSONObject("user");
                JSONObject shopInfo = tempInfo.getJSONArray("shops").getJSONObject(0);
                String title = userInfo.getString("nickName") + "(" + shopInfo.getString("name") + ")";

                List<String> openIds = new ArrayList<>();
                openIds.add(receiver);

                List<Map<String, String>> data = new ArrayList<>();
                Map<String, String> info1 = new HashMap<>();
                info1.put("name", "keyword1");
                info1.put("value", title);
                data.add(info1);

                Map<String, String> info2 = new HashMap<>();
                info2.put("name", "keyword2");
                info2.put("value", "会话消息");
                data.add(info2);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = format.format(new Date());

                Map<String, String> info3 = new HashMap<>();
                info3.put("name", "keyword3");
                info3.put("value", dateString);
                data.add(info3);

                Map<String, String> info4 = new HashMap<>();
                info4.put("name", "keyword4");
                info4.put("value", "点出查看详情");
                data.add(info4);

                PushDTO dto = new PushDTO();
                dto.setTemplateId("TpozlnOzzxAhiuS-5T-eaRNOLXReZTeqIjLTa3LiHqE");
                dto.setOpenids(openIds);
                dto.setPage("pages/user/userIndex?redirect=conversation-list");
                dto.setType(5);
                dto.setPlatform(2);
                dto.setData(data);

                Template template = pushInterface.push(dto);
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
                redisTemplate.opsForValue().set(key, "login", 10, TimeUnit.MINUTES);
            }
        }
        return null;
    }
}
