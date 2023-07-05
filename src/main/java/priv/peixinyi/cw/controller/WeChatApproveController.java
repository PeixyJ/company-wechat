package priv.peixinyi.cw.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import priv.peixinyi.cw.config.ApproveConfig;
import priv.peixinyi.cw.utils.AesException;
import priv.peixinyi.cw.utils.WXBizJsonMsgCrypt;
import priv.peixinyi.cw.utils.WXBizMsgCrypt;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author peixinyi
 * 企业微信 Url 认证
 */
@Slf4j
@RestController
public class WeChatApproveController {

    private static String MESSAGE;

    private static String sReqMsgSig;
    private static String sReqTimeStamp;
    private static String sReqNonce;

    @GetMapping("/approve/message")
    public String getMessage() {
        return MESSAGE;
    }

    @GetMapping("/approve/message/decode")
    public String getMessageDecode() throws AesException, ParserConfigurationException, IOException, SAXException {
        String TOKEN = ApproveConfig.getApproveConfig().getToken();
        String CORP_ID = ApproveConfig.getApproveConfig().getCorpId();
        String ENCODING_AES_KEY = ApproveConfig.getApproveConfig().getEncodingAesKey();
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(TOKEN, ENCODING_AES_KEY, CORP_ID);
        return wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, MESSAGE);
    }


    @GetMapping("/approve")
    public String approve(@RequestParam String msg_signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) throws AesException {
        String TOKEN = ApproveConfig.getApproveConfig().getToken();
        String CORP_ID = ApproveConfig.getApproveConfig().getCorpId();
        String ENCODING_AES_KEY = ApproveConfig.getApproveConfig().getEncodingAesKey();
        log.info("TOKEN :{} CORP_ID :{} ENCODING_AES_KEY :{}", TOKEN, CORP_ID, ENCODING_AES_KEY);
        WXBizJsonMsgCrypt wxcpt = new WXBizJsonMsgCrypt(TOKEN, ENCODING_AES_KEY, CORP_ID);
        return wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    }

    @PostMapping("/approve")
    public String accredit(HttpServletRequest request, HttpServletResponse response) {
        sReqMsgSig = request.getParameter("msg_signature");
        sReqTimeStamp = request.getParameter("timestamp");
        sReqNonce = request.getParameter("nonce");
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
