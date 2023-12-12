package org.naemansan.userapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.userapi.application.port.out.ImageServicePort;
import org.naemansan.userapi.utility.ImageUtil;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.constant.Constants;

import java.util.Map;

@WebAdapter
@RequiredArgsConstructor
public class ImageExternalAdapter implements ImageServicePort {
    private final ImageUtil imageUtil;

    @Override
    public Map<String, String> getUploadImageUrl(String typeName) {
        return imageUtil.getPreSignedUrl(Constants.PROFILE_PREFIX, typeName);
    }
}
