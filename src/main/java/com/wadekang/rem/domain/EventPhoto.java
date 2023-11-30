package com.wadekang.rem.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_event_photo", schema = "rem_schema")
public class EventPhoto extends BaseTimeEntity {

    @Id
    @Column(name = "event_photo_id")
    private Long eventPhotoId;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "photo_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Photo photo;

}
