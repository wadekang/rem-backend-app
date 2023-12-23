package com.wadekang.rem.jpa.repository.calendar;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wadekang.rem.jpa.domain.Calendar;
import com.wadekang.rem.jpa.domain.QCalendarUser;
import com.wadekang.rem.vo.CalendarVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wadekang.rem.jpa.domain.QCalendar.calendar;

@Repository
public class CalendarRepositoryImpl extends QuerydslRepositorySupport implements CalendarRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CalendarRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Calendar.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CalendarVO> findCalendarsByUserId(Long userId) {

        QCalendarUser calendarUser = QCalendarUser.calendarUser;

        return queryFactory.select(Projections.constructor(CalendarVO.class,
                        calendar.calendarId,
                        calendar.calendarName,
                        calendarUser.color,
                        calendarUser.isOwner,
                        calendar.isDefault
                ))
                .from(calendar)
                .join(calendarUser).on(calendar.calendarId.eq(calendarUser.calendar.calendarId))
                .fetchJoin()
                .where(calendarUser.user.userId.eq(userId))
                .orderBy(calendar.createdDate.asc())
                .fetch();
    }

    @Override
    public CalendarVO findCalendarByCalendarIdAndUserId(Long calendarId, Long userId) {

        QCalendarUser calendarUser = QCalendarUser.calendarUser;

        return queryFactory.select(Projections.constructor(CalendarVO.class,
                        calendar.calendarId,
                        calendar.calendarName,
                        calendarUser.color,
                        calendarUser.isOwner,
                        calendar.isDefault
                ))
                .from(calendar)
                .join(calendarUser).on(calendar.calendarId.eq(calendarUser.calendar.calendarId))
                .where(calendar.calendarId.eq(calendarId)
                        .and(calendarUser.user.userId.eq(userId))
                )
                .fetchOne();
    }
}
