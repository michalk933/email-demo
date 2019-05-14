package com.example.emaildemo.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@Value
@ConfigurationProperties("email.model")
public class ModelEmailProperties {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

}
