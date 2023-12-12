package org.naemansan.userapi.application.port.out;


import java.util.Map;

public interface ImageServicePort {
    Map<String, String> getUploadImageUrl(String typeName);
}
