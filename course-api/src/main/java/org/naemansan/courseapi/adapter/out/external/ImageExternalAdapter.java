package org.naemansan.courseapi.adapter.out.external;

import lombok.RequiredArgsConstructor;
import org.naemansan.common.annotaion.WebAdapter;
import org.naemansan.common.constant.Constants;
import org.naemansan.courseapi.application.port.out.ImageServicePort;
import org.naemansan.courseapi.utility.ExternalClientUtil;

import java.util.Map;

@WebAdapter
@RequiredArgsConstructor
public class ImageExternalAdapter implements ImageServicePort {
    private final ExternalClientUtil externalClientUtil;

    @Override
    public Map<String, String> getUploadImageUrl(String typeName) {
        return externalClientUtil.getPreSignedUrl(Constants.SPOT_IMAGE_PREFIX, typeName);
    }
}
