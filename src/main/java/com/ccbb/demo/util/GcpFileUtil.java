package com.ccbb.demo.util;

import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class GcpFileUtil {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        String uuid = UUID.randomUUID().toString();
        String ext = multipartFile.getContentType();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        if (multipartFile.isEmpty()) {
            imgUrl = null;
        } else {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext).build();

            Blob blob = storage.create(blobInfo, multipartFile.getInputStream());
        }

        return imgUrl;
    }
}
