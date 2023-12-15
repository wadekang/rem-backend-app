package com.wadekang.rem.jpa.repository.calendar;

import com.wadekang.rem.jpa.domain.CalendarUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarUserRepository extends JpaRepository<CalendarUser, Long> {

    void deleteByCalendar_CalendarIdAndUser_UserId(Long calendarId, Long userId);
}
