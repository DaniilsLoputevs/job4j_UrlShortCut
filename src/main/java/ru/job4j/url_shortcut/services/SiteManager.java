package ru.job4j.url_shortcut.services;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.url_shortcut.models.Site;

import java.util.HashMap;
import java.util.Map;

/**
 * Domains holder.
 */
/* Lombok */
@ToString
/* Spring Core */
@Service
public class SiteManager {
    
    private final Map<String, Site> sites = new HashMap<>();
    
    @Autowired
    private RandomKeyGenerator randomKeyGenerator;
    
    {
        sites.put("test.com", new Site("test.com", "asdf", "1234"));
    }
    
    public Site registerSite(String domainUrl) {
        Site site = Site.builder().domainUrl(domainUrl)
                .login(randomKeyGenerator.generateLogin())
                .password(randomKeyGenerator.generatePassword())
                .build();
        sites.put(domainUrl, site);
        return site;
    }
    
    public Site getByDomainUrl(String domainUrl) {
        return sites.get(domainUrl);
    }
    
    public Site getByLogin(String login) {
        return sites.values().stream()
                .filter(site -> site.getLogin().equals(login))
                .findFirst().orElse(Site.EMPTY);
    }
    
    public boolean contains(String domainUrl) {
        return sites.containsKey(domainUrl);
    }
    
}
