package com.wadekang.rem.svc;

import com.wadekang.rem.auth.svc.AuthService;
import com.wadekang.rem.jpa.domain.CalendarUser;
import com.wadekang.rem.jpa.domain.Event;
import com.wadekang.rem.jpa.repository.calendar.CalendarUserRepository;
import com.wadekang.rem.jpa.repository.event.EventRepository;
import com.wadekang.rem.vo.CreateEventVO;
import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CalendarUserRepository calendarUserRepository;

    @Override
    public Long createEvent(CreateEventVO createEventVO) {

        Long userId = AuthService.getUserIdFromContext();

        CalendarUser calendarUser = calendarUserRepository.findByCalendar_CalendarIdAndUser_UserId(createEventVO.getCalendarId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Calendar user not found"));

        Event event = Event.createBuilder()
                .calendar(calendarUser.getCalendar())
                .eventName(createEventVO.getEventName())
                .eventDescription(createEventVO.getEventDescription())
                .eventStartDate(createEventVO.getEventStartDate())
                .eventEndDate(createEventVO.getEventEndDate())
                .build();

        Event savedEvent = eventRepository.save(event);

        return savedEvent.getEventId();
    }

    @Override
    public List<EventVO> getEventsOfUser(EventRequestVO eventRequestVO) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return eventRepository.findAllByUserIdAndDate(Long.valueOf(userId), eventRequestVO);
    }
}
