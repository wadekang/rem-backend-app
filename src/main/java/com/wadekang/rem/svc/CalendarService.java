package com.wadekang.rem.svc;

import com.wadekang.rem.vo.CalendarVO;

import java.util.List;

public interface CalendarService {

    void createCalendar(CalendarVO calendarVO);

    List<CalendarVO> getCalendarsOfUser();

    void deleteCalendar(Long calendarId);
}
