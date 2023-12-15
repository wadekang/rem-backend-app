package com.wadekang.rem.jpa.repository.event;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wadekang.rem.jpa.domain.Event;
import com.wadekang.rem.jpa.domain.QCalendarUser;
import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wadekang.rem.jpa.domain.QEvent.event;

@Repository
public class EventRepositoryImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public EventRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Event.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<EventVO> findAllByUserId(Long userId, EventRequestVO eventRequestVO) {
        QCalendarUser calendarUser = QCalendarUser.calendarUser;

        return queryFactory.select(Projections.constructor(EventVO.class,
                        event.eventId,
                        event.eventName,
                        event.eventStartDate,
                        event.eventEndDate,
                        calendarUser.calendar.calendarId,
                        calendarUser.color))
                .from(event)
                .join(calendarUser).on(event.calendar.calendarId.eq(calendarUser.calendar.calendarId))
                .fetchJoin()
                .where(
                        calendarUser.user.userId.eq(userId)
                        .and(
                            event.eventEndDate.goe(eventRequestVO.getStartDate())
                            .or(event.eventStartDate.loe(eventRequestVO.getEndDate()))
                        )
                )
                .orderBy(event.eventStartDate.asc(), event.eventDuration.desc())
                .fetch();
    }
}
