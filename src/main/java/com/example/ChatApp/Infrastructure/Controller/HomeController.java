package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Image.UploadImageCommand;
import com.example.ChatApp.Application.Image.UploadImageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class HomeController
{
    private final UploadImageHandler uploadAvatarHandler;
    @GetMapping("/")
    public String home() {
        return "Hello 2!";
    }

    @PostMapping("/upload-image")
    public ObjectNode uploadImage(
            @RequestPart("file") MultipartFile file,
            ObjectMapper mapper
    ) {
        String url = uploadAvatarHandler.handle(new UploadImageCommand(file));

        ObjectNode json = mapper.createObjectNode();
        json.put("url", url);
        return json;
    }
}
