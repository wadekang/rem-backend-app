package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_tag", schema = "rem_schema")
public class Tag extends BaseTimeEntity {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "tb_tag_seq", allocationSize = 1)
    private Long tagId;

    @Column(name = "tag_name", unique = true, nullable = false, length = 50)
    private String tagName;

}
