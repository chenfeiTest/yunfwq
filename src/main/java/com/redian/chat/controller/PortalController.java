package com.redian.chat.controller;

import com.redian.chat.ApiResponse;
import com.redian.chat.domain.IMCallbackParam;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "portal")
public class PortalController {

    @PostMapping(name = "IM回调")
    @ApiResponse
    public Object portal(@RequestBody Map body, IMCallbackParam param) {
        return null;
    }
}
