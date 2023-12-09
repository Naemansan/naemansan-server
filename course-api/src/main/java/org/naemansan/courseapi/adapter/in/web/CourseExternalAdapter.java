package org.naemansan.courseapi.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.ResponseDto;
import org.naemansan.courseapi.application.port.in.command.CreateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseStatusCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseCommand;
import org.naemansan.courseapi.application.port.in.usecase.CourseUseCase;
import org.naemansan.courseapi.dto.request.CourseDto;
import org.naemansan.courseapi.dto.request.CourseUpdateDto;
import org.naemansan.courseapi.dto.request.CourseUpdateStatusDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseExternalAdapter {
    private final CourseUseCase courseUseCase;

    @PostMapping("")
    public ResponseDto<?> createCourse(
            @RequestBody @Valid CourseDto requestDto
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";

        return ResponseDto.ok(courseUseCase.createCourse(CreateCourseCommand.of(
                uuid,
                requestDto.title(),
                requestDto.content(),
                requestDto.tagIds(),
                requestDto.locations(),
                requestDto.spots())));
    }

    @GetMapping("")
    public String readCourses(
            @RequestParam(value = "tagIds") List<Long> tagIds,
            @RequestParam(value = "lati") Double lati,
            @RequestParam(value = "longi") Double longi,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return "readCourseInfo";
    }

    @GetMapping("/{courseId}")
    public ResponseDto<?> readCourse(
            @PathVariable("courseId") Long courseId
    ) {
        return ResponseDto.ok(courseUseCase.findCourseById(ReadCourseCommand.of(courseId)));
    }

    @PutMapping("/{courseId}")
    public ResponseDto<?> updateCourse(
            @PathVariable("courseId") Long courseId,
            @RequestBody @Valid CourseUpdateDto requestDto
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        courseUseCase.updateCourse(UpdateCourseCommand.of(
                uuid,
                courseId,
                requestDto.title(),
                requestDto.content(),
                requestDto.tagIds(),
                requestDto.spots()));

        return ResponseDto.ok(null);
    }

    @PatchMapping("/{courseId}")
    public ResponseDto<?> updateCourseStatus(
            @PathVariable("courseId") Long courseId,
            @RequestBody @Valid CourseUpdateStatusDto requestDto
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        courseUseCase.updateCourseStatus(UpdateCourseStatusCommand.of(
                uuid,
                courseId,
                requestDto.isEnrolled()));

        return ResponseDto.ok(null);
    }

    @DeleteMapping("/{courseId}")
    public ResponseDto<?> deleteCourse(
            @PathVariable("courseId") Long courseId
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        courseUseCase.deleteCourse(DeleteCourseCommand.of(uuid, courseId));

        return ResponseDto.ok(null);
    }

    /* Course Dependency */
    @GetMapping("/{courseId}/spots")
    public String readSpotsByCourse(
            @PathVariable("courseId") Long courseId
    ) {
        return "readCourseInfo";
    }

    @GetMapping("/{courseId}/moments")
    public String readMomentsByCourse(
            @PathVariable("courseId") Long courseId
    ) {
        return "readCourseInfo";
    }
}
