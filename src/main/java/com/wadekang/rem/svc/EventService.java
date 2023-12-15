package com.wadekang.rem.svc;

import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;

import java.util.List;

public interface EventService {

    List<EventVO> getEventsOfUser(EventRequestVO eventRequestVO);
}
