package com.wadekang.rem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_tag", schema = "rem_schema")
public class Tag extends BaseTimeEntity {

    @Id
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name", unique = true, nullable = false, length = 50)
    private String tagName;

}
