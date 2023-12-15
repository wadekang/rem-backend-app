package com.wadekang.rem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarVO {

    private Long calendarId;
    private String calendarName;
    private String color;
    private boolean isOwner;
    private boolean isDefault;

}
