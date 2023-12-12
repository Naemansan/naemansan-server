package org.naemansan.userapi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.naemansan.userapi.dto.type.EProvider;
import org.naemansan.userapi.dto.type.ERole;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    /* Default Column */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /* User Info */
    @Column(name = "nickname", length = 12)
    private String nickname;

    @Column(name = "introduction", length = 100)
    private String introduction;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    /* Relation Child Mapping */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followers = new ArrayList<>();

    @Builder
    private User(String serialId, String password, EProvider provider, ERole role) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdAt = LocalDate.now();
    }

    public void register(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public void update(String nickname, String introduction, String profileImageUrl) {
        if (nickname != null && !nickname.equals(this.nickname)) {
            this.nickname = nickname;
        }

        if (introduction != null && !introduction.equals(this.introduction)) {
            this.introduction = introduction;
        }

        if (profileImageUrl != null && !profileImageUrl.equals(this.profileImageUrl)) {
            this.profileImageUrl = profileImageUrl;
        }
    }
}
