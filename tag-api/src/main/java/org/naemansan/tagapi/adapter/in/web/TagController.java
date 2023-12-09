package org.naemansan.tagapi.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.dto.response.ResponseDto;
import org.naemansan.tagapi.application.port.in.usecase.TagQueryUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagQueryUseCase tagQueryUseCase;

    @GetMapping("")
    public ResponseDto<?> readTags(
            @RequestParam(value = "ids", required = false) List<Long> ids
    ) {
        return ResponseDto.ok(tagQueryUseCase.findAll(ids));
    }
}
