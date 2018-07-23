package com.redian.tim;

import com.redian.chat.exception.BusinessException;
import com.tls.tls_sigature.tls_sigature;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.zip.DataFormatException;

public class TIMClient {
    private Long skdAppid;
    private String identifier;
    private String privateKey;
    private String publicKey;
    private String usersig;

    private static final String baseUrl = "https://console.tim.qq.com";
    private static final RestTemplate template = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(TIMClient.class);


    public TIMClient(Long skdAppid, String identifier, String privateKey, String publicKey) {
        this.skdAppid = skdAppid;
        this.identifier = identifier;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * 签名
     *
     * @param identifier
     * @return
     */
    public tls_sigature.GenTLSSignatureResult sign(String identifier) {
        return sign(identifier, 30 * 24 * 3600);
    }

    /**
     * 签名
     *
     * @param identifier
     * @return
     */
    public tls_sigature.GenTLSSignatureResult sign(String identifier, long expire) {
        try {
            tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(skdAppid, identifier, privateKey, expire);
            if (result.urlSig.length() == 0) {
                throw new BusinessException("GenTLSSignatureEx failed: " + result.errMessage);
            }

            tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, skdAppid, identifier, publicKey);
            if (!checkResult.verifyResult) {
                throw new BusinessException("CheckTLSSignatureEx failed: " + checkResult.errMessage);
            }

            return result;
        } catch (DataFormatException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 请求
     *
     * @param request
     * @return
     */
    public TIMResponse request(TIMRequest request) {
        if (this.usersig == null) {
            tls_sigature.GenTLSSignatureResult result = this.sign(identifier, 360 * 24 * 3600);
            if (result.urlSig.length() > 0) {
                this.usersig = result.urlSig;
            } else {
                throw new BusinessException("请求失败: " + result.errMessage);
            }
        }

        Random rand = new Random();
        Map<String, String> query = new HashMap<>();
        query.put("sdkappid", String.valueOf(this.skdAppid));
        query.put("identifier", this.identifier);
        query.put("usersig", this.usersig);
        query.put("random", String.valueOf(rand.nextInt()));
        query.put("contenttype", "json");

        List<String> param = new ArrayList<>();
        for (String key : query.keySet()) {
            param.add(key + "=" + query.get(key));
        }

        String url = baseUrl + request.getAction();
        url += "?";
        url += String.join("&", param);

        HttpEntity<Object> entity = new HttpEntity<>(request.getData());
        ResponseEntity<String> responseEntity = template.postForEntity(url, entity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException("请求失败: " + responseEntity.getStatusCode());
        }

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        TIMResponse response = new TIMResponse(jsonObject);
        return  response;
    }
}
