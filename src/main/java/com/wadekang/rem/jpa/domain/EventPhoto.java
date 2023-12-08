package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_event_photo", schema = "rem_schema")
public class EventPhoto extends BaseTimeEntity {

    @Id
    @Column(name = "event_photo_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_photo_seq")
    @SequenceGenerator(name = "event_photo_seq", sequenceName = "tb_event_photo_seq", allocationSize = 1)
    private Long eventPhotoId;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "photo_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Photo photo;

}
