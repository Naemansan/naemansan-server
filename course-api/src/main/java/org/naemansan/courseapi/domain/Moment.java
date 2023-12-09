package org.naemansan.courseapi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.dto.type.EWeather;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "moments")
@SQLDelete(sql = "UPDATE moments SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "emotion", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EEmotion emotion;

    @Column(name = "weather", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EWeather weather;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, updatable = false)
    private Course course;

    @Builder
    public Moment(UUID userId, String content, EEmotion emotion, EWeather weather) {
        this.userId = userId;
        this.content = content;
        this.emotion = emotion;
        this.weather = weather;
    }
}
