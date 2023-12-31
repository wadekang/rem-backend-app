package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_event_photo", schema = "rem_schema")
@NoArgsConstructor
public class EventPhoto extends BaseTimeEntity {

    @Id
    @Column(name = "event_photo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventPhotoId;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "photo_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Photo photo;

}
