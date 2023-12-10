package org.naemansan.courseapi.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.naemansan.courseapi.application.port.in.command.CreateMomentCommand;
import org.naemansan.courseapi.application.port.in.query.ReadMomentsCommand;
import org.naemansan.courseapi.application.port.in.usecase.MomentUseCase;
import org.naemansan.courseapi.dto.request.MomentDto;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/moments")
public class MomentExternalAdapter {
    private final MomentUseCase momentUseCase;

    @PostMapping("")
    public ResponseDto<?> createMoment(
            @RequestBody @Valid MomentDto momentDto
    ) {
        String uuid = "625ad265-cc31-44fd-b783-e8cd047b6903";
        return ResponseDto.ok(momentUseCase.createMoment(CreateMomentCommand.of(
                uuid,
                momentDto.courseId(),
                momentDto.content()
        )));
    }

    @GetMapping("")
    public ResponseDto<?> readMoment(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        if (page < 0 || size < 0) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }

        return ResponseDto.ok(momentUseCase.findMoments(ReadMomentsCommand.builder()
                .page(page)
                .size(size).build()));
    }
}
