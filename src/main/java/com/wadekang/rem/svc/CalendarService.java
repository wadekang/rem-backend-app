package com.wadekang.rem.svc;

import com.wadekang.rem.vo.CalendarVO;

import java.util.List;

public interface CalendarService {

    List<CalendarVO> getCalendarsOfUser();

    void createCalendar(CalendarVO calendarVO);

    void createSharedCalendar(String code);

    void updateCalendar(CalendarVO calendarVO);

    void updateSharedCalendar(CalendarVO calendarVO);

    void deleteCalendar(Long calendarId);

    void deleteSharedCalendar(Long calendarId);

    String generateCalendarCode(Long calendarId);
}
