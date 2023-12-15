package com.wadekang.rem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventVO {

    private Long eventId;
    private String eventName;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;

    private Long calendarId;
    private String calendarColor;

}
