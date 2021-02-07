package ru.job4j.url_shortcut.repository;

import org.springframework.stereotype.Component;
import ru.job4j.url_shortcut.models.Site;

import java.util.HashMap;
import java.util.Map;

@Deprecated
@Component
public class SiteRepository {
    private final Map<String, Site> sites = new HashMap<>();
    
    public void put(Site site) {
        sites.put(site.getDomainUrl(), site);
    }
    
    public Site get(String domainUrl) {
        return sites.get(domainUrl);
    }
    
    public void delete(Site site) {
        sites.remove(site.getDomainUrl());
    }
    
}
