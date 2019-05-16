package com.example.emaildemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@Data
@ConfigurationProperties("email.model")
public class ModelEmailProperties {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

}
