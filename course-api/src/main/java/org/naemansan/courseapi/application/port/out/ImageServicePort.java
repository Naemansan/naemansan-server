package org.naemansan.courseapi.application.port.out;

import java.util.Map;

public interface ImageServicePort {
    Map<String, String> getUploadImageUrl(String typeName);
}
