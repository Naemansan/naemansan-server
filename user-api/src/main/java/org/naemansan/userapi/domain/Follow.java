package org.naemansan.userapi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "follows")
public class Follow {
    /* Default Column */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "following_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User following;

    @JoinColumn(name = "follower_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User follower;

    @Builder
    public Follow(User following, User follower) {
        this.following = following;
        this.follower = follower;
    }
}
