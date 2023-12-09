package org.naemansan.courseapi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.MultiPoint;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "courses")
@SQLDelete(sql = "UPDATE courses SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 12)
    private String title;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name= "start_location_name", nullable = false, length = 50, updatable = false)
    private String startLocationName;

    @Column(name= "locations", nullable = false, updatable = false)
    private MultiPoint locations;

    @Column(name = "distance", nullable = false, updatable = false)
    private Double distance;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "is_enrolled", nullable = false)
    private Boolean isEnrolled;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    /* Relation Child Column */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CourseTag> tags;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Spot> spots;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Moment> moments;

    @Builder
    public Course(
            String title, String content,
            String startLocationName, MultiPoint locations, Double distance,
            UUID userId) {
        this.title = title;
        this.content = content;
        this.startLocationName = startLocationName;
        this.locations = locations;
        this.distance = distance;
        this.userId = userId;
        this.createdAt = LocalDate.now();
        this.isEnrolled = false;
        this.isDeleted = false;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateStatus(Boolean isEnrolled) {
        this.isEnrolled = isEnrolled;
    }
}
