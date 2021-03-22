package ru.job4j.url_shortcut.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.url_shortcut.models.StatisticDTO;
import ru.job4j.url_shortcut.utils.devtools.DevLog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UrlsManager {
    
    @Autowired
    private RandomKeyGenerator randomKeyGenerator;
    @Autowired
    private SiteManager siteManager;
    
    /**
     * key - url key
     * val - url original
     */
    private final Map<String, String> urls = new HashMap<>();
    
    /**
     * key - url key
     * val - url statistic
     */
    private final Map<String, Integer> statistics = new ConcurrentHashMap<>();
    
    {
        urls.put("XXXZZZYYY", "www.job4j.ru/edu");
        statistics.put("XXXZZZYYY", 0);
        
    }
    
    /**
     * @param url - original url.
     * @return - unique key for url.
     */
    public String encode(String url) throws IllegalStateException {
        DevLog.print("encode", "after check");
        String rsl = "";
        boolean isUrlContains = true;
        for (var eachLink : urls.entrySet()) {
            if (eachLink.getValue().equals(url)) {
                rsl = eachLink.getKey();
                isUrlContains = false;
                break;
            }
        }
        if (isUrlContains) {
            rsl = randomKeyGenerator.generateUrlKey();
            statistics.put(rsl, 0);
            urls.put(rsl, url);
        }
        return rsl;
    }
    
    public String decode(String urlKey) throws IllegalStateException {
        String link = urls.get(urlKey);
        if (link == null) throw new IllegalStateException("Url doesn't found for urlKey: " + urlKey);
        increaseStatisticsFor(urlKey);
        return link;
    }
    
    public Collection<StatisticDTO> getAll() {
        return urls.entrySet().stream()
                .map(entry -> new StatisticDTO(entry.getValue(), statistics.get(entry.getKey())))
                .collect(Collectors.toList());
    }
    
    /** Just pretty API. */
    private void increaseStatisticsFor(String urlKey) {
        statistics.put(urlKey, statistics.get(urlKey) + 1);
    }
    
}
