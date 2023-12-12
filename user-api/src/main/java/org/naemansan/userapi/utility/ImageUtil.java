package org.naemansan.userapi.utility;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.naemansan.common.constant.Constants;
import org.naemansan.common.dto.type.ErrorCode;
import org.naemansan.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUtil {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_PATH;

    @Value("${cloud.aws.s3.url}")
    private String CLOUD_URL;

    public Map<String, String> getPreSignedUrl(String dirName, String fileType) {
        String filePath = String.format("%s/%s.%s", dirName, UUID.randomUUID(), fileType);

        GeneratePresignedUrlRequest urlRequest = getGeneratePreSignedUrlRequest(BUCKET_PATH, filePath, fileType);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        return Map.of(
                "preSignedUrl", url.toString(),
                "fileUrl", String.format("%s/%s", CLOUD_URL, filePath)
        );
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String filePath, String fileType) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, filePath)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());

        // ACL을 public-read로 지정
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
