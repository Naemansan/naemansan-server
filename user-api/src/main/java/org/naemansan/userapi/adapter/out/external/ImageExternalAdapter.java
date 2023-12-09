package org.naemansan.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.application.port.out.ImageServicePort;
import org.naemansan.userapi.utility.ImageUtil;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.constant.Constants;
import org.springframework.web.multipart.MultipartFile;

@WebAdapter
@RequiredArgsConstructor
public class ImageExternalAdapter implements ImageServicePort {
    private final ImageUtil imageUtil;

    @Override
    public String uploadImage(MultipartFile file) {
        return imageUtil.uploadImage(file, Constants.PROFILE_PREFIX);
    }
}
