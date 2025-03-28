package com.ccbb.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.ccbb.demo.chat.adapter.out.persistence.SpringDataChatFileRepository;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.chat.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class FileUtil {
    private final AmazonS3Client amazonS3Client;
    private final SpringDataChatFileRepository springDataChatFileRepository;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + changedImageName(uploadFile.getName());
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile); // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl; // 업로드된 파일의 S3 URL 주소 반환
    }

    // 실질적인 s3 업로드 부분
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead) // PublicRead 권한으로 업로드
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    // 랜덤 파일 이름 메서드 (파일 이름 중복 방지)
    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return random + originName;
    }
    
    // DB저장
    private UploadFile save(UploadFile uploadFile) {
        UserJpaEntity currentUser = SecurityUtil.getCurrentUser();

        if("002".equals(uploadFile.getUploadType())) {
            ChatFileJpaEntity chatMessageJpaEntity = ChatFileJpaEntity.builder()
                    .id(uploadFile.getId())
                    .seq(uploadFile.getSeq())
                    .fileName(uploadFile.getFileName())
                    .fileSize(uploadFile.getFileSize())
                    .s3Url(uploadFile.getS3Url())
                    .uploadedBy(currentUser.getUserId())
                    .build();
                    springDataChatFileRepository.save(chatMessageJpaEntity);
                    
                    return uploadFile;
        } else {
            return uploadFile;
        }
    }

}
