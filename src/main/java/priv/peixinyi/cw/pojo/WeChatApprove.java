package priv.peixinyi.cw.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peixinyi
 */

@Data
@NoArgsConstructor
public class WeChatApprove {

    private String encrypt;

    private String tousername;

    private String agentid;

}
