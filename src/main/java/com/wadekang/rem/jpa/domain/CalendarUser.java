package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_calendar_user", schema = "rem_schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"calendar_id", "user_id"})
})
@NoArgsConstructor
public class CalendarUser extends BaseTimeEntity {

    @Id
    @Column(name = "calendar_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @Column(name = "is_owner", nullable = false, columnDefinition = "boolean default false")
    private Boolean isOwner;

    @Builder(builderClassName = "CreateDefaultCalenderUserBuilder", builderMethodName = "createDefaultCalenderUserBuilder")
    public CalendarUser(Calendar calendar, User user) {
        this.calendar = calendar;
        this.user = user;
        this.color = "#FFC0CB";
        this.isOwner = true;
    }

    @Builder(builderClassName = "CreateCalenderUserBuilder", builderMethodName = "createCalenderUserBuilder")
    public CalendarUser(Calendar calendar, User user, String color, Boolean isOwner) {
        this.calendar = calendar;
        this.user = user;
        this.color = color;
        this.isOwner = isOwner;
    }
}
