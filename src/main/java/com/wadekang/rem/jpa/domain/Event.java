package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_event", schema = "rem_schema")
@NoArgsConstructor
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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventPhoto> eventPhotos = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventTag> eventTags = new ArrayList<>();

    @Builder(builderMethodName = "createBuilder")
    public Event(Calendar calendar, String eventName, String eventDescription, LocalDate eventStartDate, LocalDate eventEndDate) {
        this.calendar = calendar;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventDuration = (int) (eventEndDate.toEpochDay() - eventStartDate.toEpochDay()) + 1;
    }
}
