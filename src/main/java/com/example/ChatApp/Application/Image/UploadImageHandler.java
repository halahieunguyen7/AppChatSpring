package com.example.ChatApp.Application.Image;

import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import com.example.ChatApp.Domain.AvatarStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadImageHandler {
    private final AvatarStorage avatarStorage;

    public String handle(UploadImageCommand cmd) {

        validate(cmd.file());

        String avatarUrl = avatarStorage.upload(
                cmd.file()
        );

        return avatarUrl;
    }

    private void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new AuthDomainException("File is empty");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new AuthDomainException("Invalid image type");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new AuthDomainException("Image too large");
        }
    }
}
