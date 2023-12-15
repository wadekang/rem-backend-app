package com.wadekang.rem.jpa.repository.event;

import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;

import java.util.List;

public interface EventRepositoryCustom {

    List<EventVO> findAllByUserId(Long userId, EventRequestVO eventRequestVO);
}
