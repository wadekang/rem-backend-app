package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_calendar_shared", schema = "rem_schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"calendar_id", "shared_with_user_id"})
})
public class CalendarShared extends BaseTimeEntity {

    @Id
    @Column(name = "shared_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calendar_shared_seq")
    @SequenceGenerator(name = "calendar_shared_seq", sequenceName = "tb_calendar_shared_seq", allocationSize = 1)
    private Long sharedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with_user_id", referencedColumnName = "user_id", nullable = false)
    private User sharedWithUser;

    @Column(name = "color", nullable = false, length = 20)
    private String color;


}
