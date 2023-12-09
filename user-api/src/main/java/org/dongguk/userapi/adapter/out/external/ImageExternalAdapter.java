package org.dongguk.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.dongguk.userapi.application.port.out.ImageServicePort;
import org.dongguk.userapi.utility.ImageUtil;
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
