package priv.peixinyi.cw.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import priv.peixinyi.cw.config.ApproveConfig;
import priv.peixinyi.cw.utils.AesException;
import priv.peixinyi.cw.utils.WXBizJsonMsgCrypt;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author peixinyi
 * 企业微信 Url 认证
 */
@Slf4j
@RestController
public class WeChatApproveController {

    private static String MESSAGE;

    @GetMapping("/suite/receive/message")
    public String getMessage() {
        return MESSAGE;
    }


    @GetMapping("/approve")
    public String approve(@RequestParam String msg_signature,
                          @RequestParam String timestamp,
                          @RequestParam String nonce,
                          @RequestParam String echostr) throws AesException {
        String TOKEN = ApproveConfig.getApproveConfig().getToken();
        String CORP_ID = ApproveConfig.getApproveConfig().getCorpId();
        String ENCODING_AES_KEY = ApproveConfig.getApproveConfig().getEncodingAesKey();
        log.info("TOKEN :{} CORP_ID :{} ENCODING_AES_KEY :{}", TOKEN, CORP_ID, ENCODING_AES_KEY);
        WXBizJsonMsgCrypt wxcpt = new WXBizJsonMsgCrypt(TOKEN, ENCODING_AES_KEY, CORP_ID);
        return wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    }

    @PostMapping("/suite/receive")
    public String accredit(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            // 处理异常
        }
        String requestBodyString = requestBody.toString();
        log.info("requestBodyString :{}", requestBodyString);
        MESSAGE = requestBodyString;
        return "success";
    }

}
