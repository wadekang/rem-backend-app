package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_calendar", schema = "rem_schema")
@NoArgsConstructor
public class Calendar extends BaseTimeEntity {

    @Id
    @Column(name = "calendar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "calendar_name", nullable = false, length = 30)
    private String calendarName;

    @Column(name = "is_default", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDefault;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarUser> calendarUser = new ArrayList<>();

    @Builder(builderClassName = "CreateDefaultCalendarBuilder", builderMethodName = "createDefaultCalendarBuilder")
    public Calendar(User user) {
        this.user = user;
        this.calendarName = "내 캘린더";
        this.isDefault = true;
    }

    @Builder(builderClassName = "CreateCalendarBuilder", builderMethodName = "createCalendarBuilder")
    public Calendar(User user, String calendarName, String color, boolean isOwner) {
        this.user = user;
        this.calendarName = calendarName;
        this.isDefault = false;

        addCalendarUser(color, isOwner);
    }

    /**
     * 캘린더 추가 시 CalendarUser 추가
     * @param color
     * @param isOwner
     */
    private void addCalendarUser(String color, boolean isOwner) {
        CalendarUser cu = CalendarUser.createCalenderUserBuilder()
                .calendar(this)
                .user(this.user)
                .color(color)
                .isOwner(isOwner)
                .build();

        this.calendarUser.add(cu);
    }

    public void updateCalendar(Long userId, String calendarName, String color) {

        this.calendarName = calendarName;

        CalendarUser cu = this.calendarUser.stream()
                .filter(calendarUser -> calendarUser.getUser().getUserId().equals(userId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("CalendarUser not found"));

        cu.updateColor(color);
    }
}
