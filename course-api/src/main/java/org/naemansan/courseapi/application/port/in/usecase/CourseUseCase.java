package org.naemansan.courseapi.application.port.in.usecase;

import org.naemansan.courseapi.application.port.in.command.CreateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseStatusCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCoursesCommand;
import org.naemansan.courseapi.dto.response.CourseDetailDto;
import org.naemansan.courseapi.dto.response.CourseListDto;

import java.util.List;

public interface CourseUseCase {
    CourseDetailDto createCourse(CreateCourseCommand command);

    List<CourseListDto> findCourses(ReadCoursesCommand command);

    CourseDetailDto findCourseById(ReadCourseCommand command);

    void updateCourse(UpdateCourseCommand command);

    void updateCourseStatus(UpdateCourseStatusCommand command);

    void deleteCourse(DeleteCourseCommand command);
}
