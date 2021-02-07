package ru.job4j.url_shortcut.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.url_shortcut.models.Link;
import ru.job4j.url_shortcut.utils.devtools.DevLog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UrlsManager {
    
    @Autowired
    private RandomKeyGenerator randomKeyGenerator;
    @Autowired
    private SiteManager siteManager;
    
    private final Map<String, Link> linkMap = new HashMap<>();
    
    {
        linkMap.put("XXXZZZYYY", new Link("www.job4j.ru/edu"));
    }
    
    /**
     * @param url - original url.
     * @return - unique key for url.
     */
    public String encode(String url) throws IllegalStateException {
//        if (checkDomainsUrl(url)) throw new IllegalStateException("Url has unregistered domain. url: " + url);
        DevLog.print("encode", "after check");
        String rsl = "";
        boolean isUrlContains = true;
        for (Map.Entry<String, Link> eachLink : linkMap.entrySet()) {
            if (eachLink.getValue().equalsUrl(url)) {
                rsl = eachLink.getKey();
                isUrlContains = false;
                break;
            }
        }
        if (isUrlContains) {
            rsl = randomKeyGenerator.generateUrlKey();
            linkMap.put(rsl, new Link(url));
        }
        return rsl;
    }
    
    public String decode(String urlKey) throws IllegalStateException {
        Link link = linkMap.get(urlKey);
        if (link == null) throw new IllegalStateException("Url doesn't found for urlKey: " + urlKey);
        link.getStatistic().increaseRequestedTimes();
        return link.getUrl();
    }
    
    public Collection<Link> getAll() {
        return linkMap.values();
    }
    
//    /**
//     * extract domainUrl from url to encode and check it in {@code SiteManager}.
//     *
//     * @param url link for checking.
//     * @return true -> if url contains registered domain.
//     */
//    private boolean checkDomainsUrl(String url)  {
//        try {
//            return siteManager.contains(new URL(url).getHost());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    
}
