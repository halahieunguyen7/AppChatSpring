package com.example.ChatApp.Domain;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarStorage {
    String upload(
        MultipartFile file
    );
}
