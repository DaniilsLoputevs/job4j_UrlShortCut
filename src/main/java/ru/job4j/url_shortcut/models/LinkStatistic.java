package ru.job4j.url_shortcut.models;

import lombok.*;

import java.sql.Timestamp;

/* Lombok */
@NoArgsConstructor
@AllArgsConstructor
@Getter
//@Setter
@ToString
@EqualsAndHashCode
@Builder

public class LinkStatistic {
    private int requestedTimes = 0;
    private Timestamp statisticTimestamp = new Timestamp(System.currentTimeMillis());
    
    public void increaseRequestedTimes() {
        this.requestedTimes++;
        this.statisticTimestamp = new Timestamp(System.currentTimeMillis());
    }
    
}
