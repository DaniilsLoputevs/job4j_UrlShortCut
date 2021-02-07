package ru.job4j.url_shortcut.models;

import lombok.*;

/**
 * Domain view in code.
 */
/* Lombok */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Site {
    private String domainUrl;
    /**
     * login for JWT.
     */
    private String login;
    /**
     * password for JWT.
     */
    private String password;
    
    public static final Site EMPTY = new Site("", "", "");
    
}
