package org.naemansan.userapi.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServicePort {
    String uploadImage(MultipartFile file);
}
