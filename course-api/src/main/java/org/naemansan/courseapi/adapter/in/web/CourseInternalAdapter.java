package org.naemansan.courseapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.courseapi.application.port.in.usecase.CourseUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-courses")
public class CourseInternalAdapter {
    private final CourseUseCase courseUseCase;

    @GetMapping("/{userId}")
    public ResponseDto<?> readCourseForUser(
            @PathVariable("userId") String userId
    ) {

        return ResponseDto.ok(null);
    }
}
