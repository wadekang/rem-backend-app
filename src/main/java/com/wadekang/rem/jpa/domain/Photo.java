package com.wadekang.rem.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_photo", schema = "rem_schema")
public class Photo extends BaseTimeEntity {

    @Id
    @Column(name = "photo_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_seq")
    @SequenceGenerator(name = "photo_seq", sequenceName = "tb_photo_seq", allocationSize = 1)
    private Long photoId;

    @JoinColumn(name = "upload_user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User uploadUser;

    @Column(name = "saved_file_name", nullable = false, length = 100)
    private String savedFileName;

    @Column(name = "saved_file_path", nullable = false, length = 200)
    private String savedFilePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

}
