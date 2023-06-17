# Company-Wechat 快速认证

快速完成企业微信回调认证
## Quick Start

### Docker
```bash
java -jar jar/app.jar
```

## Docker
```bash
docker run -idt \
    --name wechat-company \
    -p 8080:8080 \
    -v ./resources:/resources \
    travel-time
```

验证地址为: http://localhost:8080/verify