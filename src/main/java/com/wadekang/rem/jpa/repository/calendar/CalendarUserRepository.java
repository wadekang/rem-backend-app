package com.wadekang.rem.jpa.repository.calendar;

import com.wadekang.rem.jpa.domain.CalendarUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarUserRepository extends JpaRepository<CalendarUser, Long>, CalendarUserRepositoryCustom {

    /**
     * Delete calendar user by calendar id and user id
     * @param calendarId
     * @param userId
     */
    void deleteByCalendar_CalendarIdAndUser_UserId(Long calendarId, Long userId);

    /**
     * Find calendar user by calendar id and user id
     * @param calendarId
     * @param userId
     * @return
     */
    Optional<CalendarUser> findByCalendar_CalendarIdAndUser_UserId(Long calendarId, Long userId);
}
