package com.dailynuts.member.entity;

import com.dailynuts.member.entity.type.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "member")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", length = 16, nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "phone", length = 11, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "role", columnDefinition = "VARCHAR(20)", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private ExpertInfo expertInfo;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Member(String loginId, String name, String password, String phoneNumber, String email, LocalDate birth, Role role) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birth = birth;
        this.role = role;
    }

}