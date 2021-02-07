package ru.job4j.url_shortcut.services;

import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/* Lombok */
@ToString
/* Spring Core */
@Component
public class RandomKeyGeneratorDefault implements RandomKeyGenerator {
    
    private final Set<String> logins = new HashSet<>();
    private final Set<String> passwords = new HashSet<>();
    private final Set<String> urls = new HashSet<>();
    
    @Value("${login-generator.loginLength}")
    private int loginLength;
    @Value("${login-generator.passwordLength}")
    private int passwordLength;
    @Value("${login-generator.urlLength}")
    private int urlLength;
    
    @Override
    public String generateLogin() {
        return generateTemplate(logins, loginLength);
    }
    
    @Override
    public String generatePassword() {
        return generateTemplate(passwords, passwordLength);
    }
    
    @Override
    public String generateUrlKey() {
        return generateTemplate(urls, urlLength);
    }
    
    
    private String generateTemplate(Set<String> store, int keyLength) {
        String generatedPassword = RandomStringUtils.random(keyLength, true, true);
        while (store.contains(generatedPassword)) {
            generatedPassword = RandomStringUtils.random(keyLength, true, true);
        }
        store.add(generatedPassword);
        return generatedPassword;
    }
    
}
