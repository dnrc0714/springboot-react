package com.ccbb.demo.file.application.port.in.command;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FileCreateCommand(List<MultipartFile> files) {
}
