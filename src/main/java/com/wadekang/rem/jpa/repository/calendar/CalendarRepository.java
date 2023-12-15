package com.wadekang.rem.jpa.repository.calendar;

import com.wadekang.rem.jpa.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, CalendarRepositoryCustom {

    void deleteByCalendarIdAndUser_UserId(Long calendarId, Long userId);
}
