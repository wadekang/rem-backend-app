package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tb_event", schema = "rem_schema")
public class Event extends BaseTimeEntity {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "tb_event_seq", allocationSize = 1)
    private Long eventId;

    @JoinColumn(name = "calendar_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Calendar calendar;

    @Column(name = "event_name", nullable = false, length = 50)
    private String eventName;

    @Column(name = "event_description", columnDefinition = "text")
    private String eventDescription;

    @Column(name = "event_start_date", nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime eventStartDate;

    @Column(name = "event_end_date", nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime eventEndDate;

    @Column(name = "is_all_day", nullable = false)
    private Boolean isAllDay;

}
