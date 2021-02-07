package ru.job4j.url_shortcut.models;

import lombok.*;

/* Lombok */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Link {
    private String url;
    private LinkStatistic statistic;
    
    public Link(String url) {
        this.url = url;
        this.statistic = new LinkStatistic();
    }
    
    
    public boolean equalsUrl(Link link) {
        return this.url.equals(link.url);
    }
    public boolean equalsUrl(String link) {
        return this.url.equals(link);
    }
   
}
