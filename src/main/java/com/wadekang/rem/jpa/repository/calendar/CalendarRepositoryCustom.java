package com.wadekang.rem.jpa.repository.calendar;

import com.wadekang.rem.vo.CalendarVO;

import java.util.List;

public interface CalendarRepositoryCustom {

    List<CalendarVO> findCalendarsByUserId(Long userId);
}
