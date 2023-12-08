package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_event_tag", schema = "rem_schema")
public class EventTag extends BaseTimeEntity {

    @Id
    @Column(name = "event_tag_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_tag_seq")
    @SequenceGenerator(name = "event_tag_seq", sequenceName = "tb_event_tag_seq", allocationSize = 1)
    private Long eventTagId;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "tag_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

}
