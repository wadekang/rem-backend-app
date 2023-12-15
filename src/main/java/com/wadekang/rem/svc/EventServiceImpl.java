package com.wadekang.rem.svc;

import com.wadekang.rem.jpa.repository.event.EventRepository;
import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;
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

    @Override
    public List<EventVO> getEventsOfUser(EventRequestVO eventRequestVO) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return eventRepository.findAllByUserId(Long.valueOf(userId), eventRequestVO);
    }
}
