package com.example.shardingjdbctest.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.encrypt.spi.QueryAssistedEncryptAlgorithm;
import org.springframework.util.StringUtils;

@Slf4j
public class MyAesAssistQueryEncryptor extends MyAesEncryptor implements QueryAssistedEncryptAlgorithm {
    private static final String TYPE = "my-AssistQuery";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String queryAssistedEncrypt(String plaintext) {
        if (StringUtils.isEmpty(plaintext)) {
            return "";
        }
        return plaintext + "SHA-256";
    }
}