package com.example.ChatApp.Infrastructure.Persistence.Image;

import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import com.example.ChatApp.Domain.AvatarStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SrpStorage implements AvatarStorage {

    private final RestTemplate restTemplate;
    @Value("${image_service.upload.url}")
    private String uploadUrl;

    @Override
    public String upload(MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", toResource(file));

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<UploadAvatarResponse> response =
                    restTemplate.postForEntity(
                            uploadUrl,
                            request,
                            UploadAvatarResponse.class
                    );

            if (!response.getStatusCode().is2xxSuccessful()
                    || response.getBody() == null
                    || response.getBody().fileName() == null) {
                throw new AuthDomainException("Upload avatar failed");
            }

            return response.getBody().fileName();
        } catch (IOException e) {
            throw new AuthDomainException("Cannot read avatar file", e);
        }
    }

    private Resource toResource(MultipartFile file) throws IOException {
        return new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
    }
}
