package com.example.ChatApp.Infrastructure.Persistence.Logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest =
                new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper wrappedResponse =
                new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse, start);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String body = new String(
                request.getContentAsByteArray(),
                StandardCharsets.UTF_8
        );

//        log.warn("""
//                HTTP REQUEST
//                Method : {}
//                Path   : {}
//                Headers: {}
//                Body   : {}
//                """,
//                request.getMethod(),
//                request.getRequestURI(),
//                extractHeaders(request),
//                body
//        );
    }

    private void logResponse(
            ContentCachingResponseWrapper response,
            long start
    ) {
        String body = new String(
                response.getContentAsByteArray(),
                StandardCharsets.UTF_8
        );
//
//        log.debug("""
//                HTTP RESPONSE
//                Status : {}
//                Time   : {} ms
//                Body   : {}
//                """,
//                response.getStatus(),
//                System.currentTimeMillis() - start,
//                body
//        );
    }

    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers;
    }
}
