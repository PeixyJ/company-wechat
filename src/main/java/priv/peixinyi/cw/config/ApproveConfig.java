package priv.peixinyi.cw.config;

import lombok.Data;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author peixinyi
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.approve")
public class ApproveConfig implements SmartInitializingSingleton {

    /**
     * 企业微信 Token
     */
    private String token;

    /**
     * 企业微信 CorpId
     */
    private String corpId;

    /**
     * 企业微信 EncodingAESKey
     */
    private String encodingAesKey;

    /**
     * 企业微信 ApproveConfig
     */
    private static ApproveConfig approveConfig;

    public static ApproveConfig getApproveConfig() {
        return approveConfig;
    }

    @Override
    public void afterSingletonsInstantiated() {
        ApproveConfig.approveConfig = this;
    }

}
