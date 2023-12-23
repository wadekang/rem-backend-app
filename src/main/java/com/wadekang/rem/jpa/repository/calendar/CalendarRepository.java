package com.wadekang.rem.jpa.repository.calendar;

import com.wadekang.rem.jpa.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, CalendarRepositoryCustom {

    Optional<Calendar> findByCalendarIdAndUser_UserId(Long calendarId, Long userId);

    void deleteByCalendarIdAndUser_UserId(Long calendarId, Long userId);
}
