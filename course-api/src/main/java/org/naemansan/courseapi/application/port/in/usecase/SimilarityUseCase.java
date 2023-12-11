package org.naemansan.courseapi.application.port.in.usecase;

import org.naemansan.courseapi.application.port.in.query.ReadSimilarEnrolledCoursesCommand;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarPersonalCoursesCommand;
import org.naemansan.courseapi.dto.response.CourseSimilarityDto;

import java.util.List;

public interface SimilarityUseCase {
    List<CourseSimilarityDto> findSimilarPersonalCourses(ReadSimilarPersonalCoursesCommand command);

    List<CourseSimilarityDto> findSimilarEnrolledCourses(ReadSimilarEnrolledCoursesCommand command);
}
