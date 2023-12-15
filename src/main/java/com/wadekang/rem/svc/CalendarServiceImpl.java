package com.wadekang.rem.svc;

import com.wadekang.rem.jpa.domain.Calendar;
import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.jpa.repository.calendar.CalendarRepository;
import com.wadekang.rem.jpa.repository.calendar.CalendarUserRepository;
import com.wadekang.rem.jpa.repository.user.UserRepository;
import com.wadekang.rem.vo.CalendarVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarUserRepository calendarUserRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createCalendar(CalendarVO calendarVO) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Calendar newCalendar = Calendar.createCalendarBuilder()
                .user(user)
                .calendarName(calendarVO.getCalendarName())
                .color(calendarVO.getColor())
                .isOwner(true)
                .build();

        calendarRepository.save(newCalendar);
    }

    @Override
    public List<CalendarVO> getCalendarsOfUser() {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return calendarRepository.findCalendarsByUserId(Long.valueOf(userId));
    }

    @Override
    @Transactional
    public void deleteCalendar(Long calendarId) {

        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        calendarUserRepository.deleteByCalendar_CalendarIdAndUser_UserId(calendarId, userId);
        calendarRepository.deleteByCalendarIdAndUser_UserId(calendarId, userId);
    }
}
