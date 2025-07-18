package com.dailynuts.member.entity;

import com.dailynuts.member.entity.type.ImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "image")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "url", length = 300, nullable = false)
    private String url;

    @Column(name = "type", columnDefinition = "VARCHAR(20)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    public Image(String name, String url, ImageType type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }
}
