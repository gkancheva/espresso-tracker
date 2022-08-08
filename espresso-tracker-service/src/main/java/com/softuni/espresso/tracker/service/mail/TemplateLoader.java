package com.softuni.espresso.tracker.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.enterprise.context.ContextException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class TemplateLoader {

    public String loadMailTemplate() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:mail-template/new-coffees-notifications.html");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            String errorMessage = "String errorMessage = Exception while loading email template: " + e.getMessage();
            log.error(errorMessage, e);
            throw new ContextException(errorMessage);
        }
    }

}
