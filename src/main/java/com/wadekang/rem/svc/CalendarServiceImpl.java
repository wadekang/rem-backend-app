package com.wadekang.rem.svc;

import com.wadekang.rem.auth.svc.AuthService;
import com.wadekang.rem.jpa.domain.Calendar;
import com.wadekang.rem.jpa.domain.CalendarUser;
import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.jpa.repository.calendar.CalendarRepository;
import com.wadekang.rem.jpa.repository.calendar.CalendarUserRepository;
import com.wadekang.rem.jpa.repository.user.UserRepository;
import com.wadekang.rem.vo.CalendarVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
    private final CalendarShareService calendarShareService;

    @Override
    public List<CalendarVO> getCalendarsOfUser() {

        Long userId = AuthService.getUserIdFromContext();

        return calendarRepository.findCalendarsByUserId(userId);
    }

    @Override
    @Transactional
    public void createCalendar(CalendarVO calendarVO) {

        Long userId = AuthService.getUserIdFromContext();

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Calendar newCalendar = Calendar.createCalendarBuilder()
                .user(user)
                .calendarName(calendarVO.getCalendarName())
                .color(calendarVO.getColor())
                .isOwner(true)
                .build();

        calendarRepository.save(newCalendar);
    }

    @Override
    @Transactional
    public void createSharedCalendar(String code) {

        Long userId = AuthService.getUserIdFromContext();

        try {
            Long calendarId = calendarShareService.validateCode(code);

            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new EntityNotFoundException("Calendar not found"));

            CalendarUser calendarUser = CalendarUser.createCalenderUserBuilder()
                    .user(user)
                    .calendar(calendar)
                    .color("#FFC0CB")
                    .isOwner(false)
                    .build();

            calendarUserRepository.save(calendarUser);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void updateCalendar(CalendarVO calendarVO) {

        Long userId = AuthService.getUserIdFromContext();

        Calendar calendar = calendarRepository.findByCalendarIdAndUser_UserId(calendarVO.getCalendarId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar not found"));

        calendar.updateCalendar(userId, calendarVO.getCalendarName(), calendarVO.getColor());

        calendarRepository.save(calendar);
    }

    @Override
    @Transactional
    public void updateSharedCalendar(CalendarVO calendarVO) {

        Long userId = AuthService.getUserIdFromContext();

        CalendarUser calendarUser = calendarUserRepository.findByCalendar_CalendarIdAndUser_UserId(calendarVO.getCalendarId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar user not found"));

        calendarUser.updateColor(calendarVO.getColor());

        calendarUserRepository.save(calendarUser);
    }

    @Override
    @Transactional
    public void deleteCalendar(Long calendarId) {

        Long userId = AuthService.getUserIdFromContext();

        CalendarVO calendar = calendarRepository.findCalendarByCalendarIdAndUserId(calendarId, userId);

        // 캘린더와 캘린더 유저, 이벤트, 이벤트 사진/태그 등 모두 삭제
        if (calendar != null) {
            calendarRepository.deleteByCalendarIdAndUser_UserId(calendarId, userId);
        } else {
            throw new EntityNotFoundException("Calendar not found");
        }
    }

    @Override
    @Transactional
    public void deleteSharedCalendar(Long calendarId) {

        Long userId = AuthService.getUserIdFromContext();

        CalendarVO calendar = calendarRepository.findCalendarByCalendarIdAndUserId(calendarId, userId);

        // 캘린더 유저만 삭제
        if (calendar != null) {
            calendarUserRepository.deleteByCalendar_CalendarIdAndUser_UserId(calendarId, userId);
        } else {
            throw new EntityNotFoundException("Calendar not found");
        }
    }

    @Override
    public String generateCalendarCode(Long calendarId) {

        Long userId = AuthService.getUserIdFromContext();

        Calendar calendar = calendarRepository.findByCalendarIdAndUser_UserId(calendarId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar not found"));

        try {
            return calendarShareService.generateCodeForShare(calendar.getCalendarId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
