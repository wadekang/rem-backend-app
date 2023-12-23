package com.wadekang.rem.jpa.repository.calendar;

import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarUserRepositoryImpl extends QuerydslRepositorySupport implements CalendarUserRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    public CalendarUserRepositoryImpl(JPQLQueryFactory queryFactory) {
        super(CalendarUserRepositoryImpl.class);
        this.queryFactory = queryFactory;
    }
}
