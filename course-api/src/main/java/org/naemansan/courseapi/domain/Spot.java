package org.naemansan.courseapi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Point;
import org.naemansan.courseapi.dto.type.ECategory;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "spots")
@SQLDelete(sql = "UPDATE spots SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Spot {
    /* Default Column */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, updatable = false, length = 12)
    private String name;

    @Column(name = "description", nullable = false, updatable = false, length = 100)
    private String description;

    @Column(name="location", columnDefinition = "POINT")
    private Point location;

    @Column(name = "thumbnail_url", updatable = false)
    private String thumbnailUrl;

    @Column(name = "category", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ECategory category;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    /* Relation Parent Column */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, updatable = false)
    private Course course;

    @Builder
    public Spot(String name, String description, Point location, String thumbnail,
                Course course, ECategory category) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.thumbnailUrl = thumbnail;
        this.course = course;
        this.category = category;
        this.isDeleted = false;
    }
}
