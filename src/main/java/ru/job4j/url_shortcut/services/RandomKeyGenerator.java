package ru.job4j.url_shortcut.services;

public interface RandomKeyGenerator {
    
    String generateLogin();
    
    String generatePassword();
    
    String generateUrlKey();
    
}
