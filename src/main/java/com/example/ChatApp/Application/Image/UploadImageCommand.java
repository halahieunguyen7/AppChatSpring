package com.example.ChatApp.Application.Image;

import org.springframework.web.multipart.MultipartFile;

public record UploadImageCommand(
        MultipartFile file
) {}