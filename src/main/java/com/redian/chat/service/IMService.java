package com.redian.chat.service;

import com.redian.chat.exception.BusinessException;
import com.redian.tim.TIMClient;
import com.redian.tim.TIMRequest;
import com.redian.tim.TIMResponse;
import com.tls.tls_sigature.tls_sigature;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
import java.util.zip.DataFormatException;

@Service
public class IMService {
    private static Logger logger = LoggerFactory.getLogger(IMService.class);
    private static final String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgaPViaHMC9Rc+UQiy\n" +
            "cv9caWBB0Y2IsPLmj+vFgei48wihRANCAARNqxWFjFmh+OwyhOXfyOSWBU8zaXbz\n" +
            "IxzwdJFPURQhJpccIFxC/6NxOsYTYvgfNi79UX7T4xP0lQ5fwZstdtSe\n" +
            "-----END PRIVATE KEY-----";
    private static final String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAETasVhYxZofjsMoTl38jklgVPM2l2\n" +
            "8yMc8HSRT1EUISaXHCBcQv+jcTrGE2L4HzYu/VF+0+MT9JUOX8GbLXbUng==\n" +
            "-----END PUBLIC KEY-----";
    private static final Long skdAppid = 1400114783L;
    private static final String identifier = "admin";
    private TIMClient client;


    public IMService() {
        this.client = new  TIMClient(skdAppid, identifier, privateKey, publicKey);
    }

    /**
     *用户签名
     * @return
     */
    public tls_sigature.GenTLSSignatureResult sign(String identifier) {
        return this.client.sign(identifier);
    }

    /**
     * 注册用户
     * @param identifier
     * @param nickname
     * @param avatarUrl
     * @return
     */
    public Map<String, Object> register(String identifier, String nickname, String avatarUrl) {
        TIMRequest request = new TIMRequest("/v4/im_open_login_svc/account_import");
        request.put("Identifier", identifier);
        request.put("Nick", nickname);
        request.put("FaceUrl", avatarUrl);

        TIMResponse response = this.client.request(request);
        return response.getData();
    }
}
