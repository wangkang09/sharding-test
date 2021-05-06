package com.example.shardingjdbctest.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.encrypt.spi.EncryptAlgorithm;

import java.util.Properties;

@Slf4j
public class MyAesEncryptor implements EncryptAlgorithm {

    private static final String TYPE = "my-AES";
    private Properties properties = new Properties();

    @Override
    public void init() {
    }

    @Override
    public String encrypt(Object plaintext) {
        return plaintext.toString() + "encrypt";
    }

    @Override
    public Object decrypt(String ciphertext) {
        return ciphertext.substring(0, ciphertext.length() - "encrypt".length());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Get properties.
     *
     * @return properties
     */
    @Override
    public Properties getProps() {
        return properties;
    }

    /**
     * Set properties.
     *
     * @param props properties
     */
    @Override
    public void setProps(Properties props) {
        this.properties = props;
    }
}

