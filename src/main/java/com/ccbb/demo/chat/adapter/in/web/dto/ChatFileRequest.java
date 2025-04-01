package com.ccbb.demo.chat.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ChatFileRequest (List<MultipartFile> files, MultipartFile file, String type){
}
