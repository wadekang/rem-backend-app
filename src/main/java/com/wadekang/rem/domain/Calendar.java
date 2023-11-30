package com.wadekang.rem.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_calendar", schema = "rem_schema")
public class Calendar extends BaseTimeEntity {

    @Id
    @Column(name = "calendar_id")
    private Long calendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "calendar_name", nullable = false, length = 30)
    private String calendarName;

    @Column(name = "calendar_color", nullable = false, length = 20)
    private String calendarColor;

    @Column(name = "is_shared", nullable = false)
    private Boolean isShared;

}
