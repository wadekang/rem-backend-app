package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_tag", schema = "rem_schema")
@NoArgsConstructor
public class Tag extends BaseTimeEntity {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(name = "tag_name", unique = true, nullable = false, length = 50)
    private String tagName;

}
