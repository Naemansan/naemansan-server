package org.dongguk.userapi.utility;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.constant.Constants;
import org.naemansan.common.exception.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUtil {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_PATH;

    @Value("${cloud.aws.s3.url}")
    private String CLOUD_URL;

    public String uploadImage(MultipartFile file, String dirName) {
        final String contentType = file.getContentType();

        assert contentType != null;
        if (!contentType.startsWith(Constants.IMAGE_CONTENT_PREFIX)) {
            throw new CommonException(ErrorCode.INVALID_CONTEXT_TYPE);
        }

        final String uuidImageName = UUID.randomUUID() + "." + contentType.split("/")[1];
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (final InputStream inputStream = file.getInputStream()) {
            String keyName = dirName + uuidImageName;

            s3Client.putObject(
                    new PutObjectRequest(BUCKET_PATH, keyName, inputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new CommonException(ErrorCode.AWS_S3_ERROR);
        }


        return CLOUD_URL + dirName + uuidImageName;
    }
}
