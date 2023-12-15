package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "tb_event", schema = "rem_schema")
public class Event extends BaseTimeEntity {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @JoinColumn(name = "calendar_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Calendar calendar;

    @Column(name = "event_name", nullable = false, length = 50)
    private String eventName;

    @Column(name = "event_description", columnDefinition = "text")
    private String eventDescription;

    @Column(name = "event_start_date", nullable = false)
    private LocalDate eventStartDate;

    @Column(name = "event_end_date", nullable = false)
    private LocalDate eventEndDate;

    @Column(name = "event_duration", nullable = false)
    private int eventDuration;

}
