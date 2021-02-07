package ru.job4j.url_shortcut.models.jwt;

import lombok.*;

import java.io.Serializable;

/* Lombok */
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class JwtResponse implements Serializable {
    
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    
}