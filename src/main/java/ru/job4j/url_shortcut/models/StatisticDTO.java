package ru.job4j.url_shortcut.models;

import lombok.*;

/* Lombok */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@Builder
public class StatisticDTO {
    private String url;
    private int total;
    
}
