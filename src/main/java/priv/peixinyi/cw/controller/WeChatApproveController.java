package priv.peixinyi.cw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.peixinyi.cw.config.ApproveConfig;
import priv.peixinyi.cw.utils.AesException;
import priv.peixinyi.cw.utils.WXBizJsonMsgCrypt;

/**
 * @author peixinyi
 * 企业微信 Url 认证
 */
@RestController
public class WeChatApproveController {


    @GetMapping("/approve")
    public String approve(@RequestParam String msg_signature,
                          @RequestParam String timestamp,
                          @RequestParam String nonce,
                          @RequestParam String echostr) throws AesException {
        String TOKEN = ApproveConfig.getApproveConfig().getToken();
        String CORP_ID = ApproveConfig.getApproveConfig().getCorpId();
        String ENCODING_AES_KEY = ApproveConfig.getApproveConfig().getEncodingAesKey();
        WXBizJsonMsgCrypt wxcpt = new WXBizJsonMsgCrypt(TOKEN, ENCODING_AES_KEY, CORP_ID);
        return wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    }

}
