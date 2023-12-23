package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_event_tag", schema = "rem_schema")
@NoArgsConstructor
public class EventTag extends BaseTimeEntity {

    @Id
    @Column(name = "event_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventTagId;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "tag_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

}
