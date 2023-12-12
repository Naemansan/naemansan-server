package org.naemansan.courseapi.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarEnrolledCoursesCommand;
import org.naemansan.courseapi.application.port.in.query.ReadSimilarPersonalCoursesCommand;
import org.naemansan.courseapi.application.port.in.usecase.SimilarityUseCase;
import org.naemansan.courseapi.dto.request.SimilarityDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class SimilarityExternalAdapter {
    private final SimilarityUseCase similarityUseCase;

    @PostMapping("/similarity")
    public ResponseDto<?> similarity(
            @RequestParam(value = "isPersonal") Boolean isPersonal,
            @RequestBody @Valid SimilarityDto requestBody
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";

        System.out.println(isPersonal);

        if (isPersonal) {
            return ResponseDto.ok(similarityUseCase.findSimilarPersonalCourses(
                    ReadSimilarPersonalCoursesCommand.builder()
                            .userId(uuid)
                            .locations(requestBody.locations())
                            .build()));
        } else {
            return ResponseDto.ok(similarityUseCase.findSimilarEnrolledCourses(
                    ReadSimilarEnrolledCoursesCommand.builder()
                            .locations(requestBody.locations())
                            .build()));
        }
    }
}
