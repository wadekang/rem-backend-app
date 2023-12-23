package com.wadekang.rem.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateEventVO {

    private Long calendarId;
    private String eventName;
    private String eventDescription;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;

    private List<String> eventPhotoList;
    private List<String> eventTagList;

}
