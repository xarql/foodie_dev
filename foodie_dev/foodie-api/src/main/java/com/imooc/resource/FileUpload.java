package com.imooc.resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="file")
@PropertySource("classpath:file-upload-prod.properties")
@Getter
@Setter
public class FileUpload {
    private String imeagUserFaceLocation;
    private String imageServerUrl;
}
