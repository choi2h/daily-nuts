package com.dailynuts.member.entity;

import com.dailynuts.member.dto.ExpertInfoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter
@Table(name = "expert_info")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExpertInfo {

    @Id
    @Column(name = "expert_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expertInfo", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ExpertCertificationImage> images;

    @Column(name = "is_approved", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isApproved;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    public ExpertInfo(Member member, String description) {
        this.description = description;
        this.member = member;
        this.images = new HashSet<>();
        this.isApproved = false;
    }

    public void addImage(ExpertCertificationImage image) {
        image.setExpertInfo(this);
        images.add(image);
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void clearImages() {
        this.images.clear();
    }
}
