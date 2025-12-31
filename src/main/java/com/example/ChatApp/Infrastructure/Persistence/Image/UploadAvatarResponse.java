package com.example.ChatApp.Infrastructure.Persistence.Image;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UploadAvatarResponse(
        @JsonProperty("file_name")
        String fileName
) {}