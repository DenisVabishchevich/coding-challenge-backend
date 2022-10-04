package com.am.challenge.service;

import com.am.challenge.domain.Application;
import com.am.challenge.mapper.ApplicationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ObjectMapper mapper;
    private final ApplicationMapper applicationMapper;
    private final GitHubService gitHubService;

    @Async
    public CompletableFuture<Resource> generateApplicationPdf(Application application) {
        log.debug("Generating PDF for application: {}", application.getName());
        try {
            Document document = new Document();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            InputStream userAvatar = gitHubService.getUserAvatar(application.getGithubUser());
            document.add(Image.getInstance((userAvatar.readAllBytes())));
            document.add(new Paragraph(mapper.writeValueAsString(applicationMapper.toDto(application))));
            document.close();
            writer.close();

            ByteArrayResource value = new ByteArrayResource(os.toByteArray()) {
                @Override
                public String getFilename() {
                    return application.getName() + ".pdf";
                }
            };
            return CompletableFuture.completedFuture(value);
        } catch (Exception e) {
            throw new RuntimeException("Generating pdf error for application: " + application, e);
        }
    }
}
