package ru.job4j.url_shortcut.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url_shortcut.models.StatisticDTO;
import ru.job4j.url_shortcut.services.UrlsManager;

import java.util.Collection;
import java.util.Map;

@RestController
@CrossOrigin(value = {"*"})
public class UrlsController {
    public static final String CONTROLLER_URLS = "/shortcut/urls/";
    public static final String CONTROLLER_URLS_REDIRECT = "/shortcut/urls/redirect";
    public static final String CONTROLLER_URLS_STATISTIC = "/shortcut/urls/statistic";
    @Autowired
    private UrlsManager urlsManager;
    
    /**
     * Register Url then return unique cod for Decode this url.
     * Request body:
     * {
     * "url": "YOUR_URL"
     * }
     * Response body:
     * {
     * "urlKey": "UNIQUE_CODE_FOR_YOUR_URL"
     * }
     */
    @PostMapping(CONTROLLER_URLS)
    public ResponseEntity<Map<String, String>> encodeUrl(@RequestBody Map<String, String> reqObj) {
        return ResponseEntity.ok(Map.of("urlKey", urlsManager.encode(reqObj.get("url"))));
        
    }
    
    /**
     * Decode ulrKey into Url, then return decoded url.
     * Request body:
     * {
     * "urlKey": "UNIQUE_CODE_FOR_YOUR_URL"
     * }
     * Response body:
     * {
     * "url": "YOUR_URL"
     * }
     */
    @GetMapping(CONTROLLER_URLS)
    private ResponseEntity<Map<String, String>> decodeUrl(@RequestBody Map<String, String> reqObj) {
        return ResponseEntity.ok(Map.of("urlKey", urlsManager.decode(reqObj.get("urlKey"))));
    }
    
    /**
     * Decode ulrKey into Url, then return decoded url.
     * * This mapping doesn't need Authorization.
     * Request body:
     * {
     * "urlKey": "UNIQUE_CODE_FOR_YOUR_URL"
     * }
     * Response body:
     * {
     * "url": "YOUR_URL"
     * }
     */
    @GetMapping(CONTROLLER_URLS_REDIRECT)
    private ResponseEntity<Map<String, String>> decodeAndRedirectUrl(@RequestBody Map<String, String> reqObj) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(Map.of("urlKey", urlsManager.decode(reqObj.get("urlKey"))));
    }
    
    @GetMapping(CONTROLLER_URLS_STATISTIC)
    private ResponseEntity<Collection<StatisticDTO>> getStatistic() {
        return ResponseEntity.ok(urlsManager.getAll());
    }
    
}
