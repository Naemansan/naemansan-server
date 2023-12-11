package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.courseapi.application.port.out.EmotionServicePort;
import org.naemansan.courseapi.dto.type.EEmotion;
import org.naemansan.courseapi.utility.ExternalClientUtil;
import org.naemansan.courseapi.utility.InternalClientUtil;

@WebAdapter
@RequiredArgsConstructor
public class EmotionExternalAdapter implements EmotionServicePort {
    private final ExternalClientUtil externalClientUtil;

    @Override
    public EEmotion analyzeContent(String content) {
        return EEmotion.JOY;
    }
}
