package com.ccbb.demo.file.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

public record ChatFileUploadRequest(Long messageId, MultipartFile file) {
}
