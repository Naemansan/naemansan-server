package org.naemansan.courseapi.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.application.port.in.command.CreateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.DeleteCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseCommand;
import org.naemansan.courseapi.application.port.in.command.UpdateCourseStatusCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCourseDependenceCommand;
import org.naemansan.courseapi.application.port.in.query.ReadCoursesCommand;
import org.naemansan.courseapi.application.port.in.query.ReadMomentsCommand;
import org.naemansan.courseapi.application.port.in.usecase.CourseUseCase;
import org.naemansan.courseapi.application.port.in.usecase.MomentUseCase;
import org.naemansan.courseapi.dto.common.LocationDto;
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
    private final MomentUseCase momentUseCase;

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
    public ResponseDto<?> readCourses(
            @RequestParam(value = "tagIds", required = false) List<Long> tagIds,
            @RequestParam(value = "lati", required = false) Double lati,
            @RequestParam(value = "longi", required = false) Double longi,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        if (page < 0 || size < 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }

        if (lati != null && longi != null) {
            return ResponseDto.ok(courseUseCase.findCoursesByLocation(
                    ReadCoursesCommand.of(tagIds, new LocationDto(lati, longi), page, size)));
        } else {
            return ResponseDto.ok(courseUseCase.findCourses(
                    ReadCoursesCommand.of(tagIds, null, page, size)));
        }
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
    public ResponseDto<?> readMomentsByCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        if (page < 0 || size < 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }

        return ResponseDto.ok(momentUseCase.findMomentsByCourseId(ReadCourseDependenceCommand.builder()
                .courseId(courseId)
                .page(page)
                .size(size).build()));
    }
}
