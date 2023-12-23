package com.wadekang.rem.svc;

import com.wadekang.rem.vo.CreateEventVO;
import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;

import java.util.List;

public interface EventService {

    Long createEvent(CreateEventVO createEventVO);

    List<EventVO> getEventsOfUser(EventRequestVO eventRequestVO);
}
