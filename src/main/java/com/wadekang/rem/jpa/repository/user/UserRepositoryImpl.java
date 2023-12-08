package com.wadekang.rem.jpa.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.jpa.vo.UserResponseVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.wadekang.rem.jpa.domain.QUser.*;

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<UserResponseVO> findByUserId(Long userId) {
        return Optional.ofNullable(
                queryFactory
                        .select(Projections.constructor(UserResponseVO.class,
                                user.userId,
                                user.name,
                                user.email,
                                user.profileImageUrl))
                        .from(user)
                        .where(user.userId.eq(userId))
                        .fetchOne()
        );
    }

    public Optional<UserResponseVO> findByoAuthUserId(String oAuthUserId) {
        return Optional.ofNullable(
                queryFactory
                        .select(Projections.constructor(UserResponseVO.class,
                                user.userId,
                                user.name,
                                user.email,
                                user.profileImageUrl))
                        .from(user)
                        .where(user.oAuthUserId.eq(oAuthUserId))
                        .fetchOne()
        );
    }
}
