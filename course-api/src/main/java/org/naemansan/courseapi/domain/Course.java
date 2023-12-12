package org.naemansan.courseapi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.MultiPoint;
import org.naemansan.courseapi.dto.type.EState;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "courses")
@SQLDelete(sql = "UPDATE courses SET state = 'DELETED' WHERE id = ?")
@Where(clause = "state != 'DELETED' AND state != 'IN_PROGRESS'")
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EState state;

    /* Relation Parent Column */
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

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
        this.state = EState.PERSONAL;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateStatus(EState state) {
        this.state = state;
    }
}
