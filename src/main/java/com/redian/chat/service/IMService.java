package com.redian.chat.service;

import com.redian.chat.exception.BusinessException;
import com.tls.tls_sigature.tls_sigature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.zip.DataFormatException;

@Service
public class IMService {

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

    private static Logger logger = LoggerFactory.getLogger(IMService.class);

    /**
     *用户签名
     *
     * @return
     */
    public tls_sigature.GenTLSSignatureResult sign(String identifier) {
        try {
            tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(skdAppid, identifier, privateKey);
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
}
