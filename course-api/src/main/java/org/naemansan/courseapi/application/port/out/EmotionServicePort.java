package org.naemansan.courseapi.application.port.out;

import org.naemansan.courseapi.dto.type.EEmotion;

public interface EmotionServicePort {
    EEmotion analyzeContent(String content);
}
