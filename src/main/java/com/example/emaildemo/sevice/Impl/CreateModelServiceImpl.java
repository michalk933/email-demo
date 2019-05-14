package com.example.emaildemo.sevice.Impl;

import com.example.emaildemo.properties.ModelEmailProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CreateModelServiceImpl {

    private final ModelEmailProperties modelEmailProperties;

    public Map<String, Object> getModelEmail() {
        Map<String, Object> model = new HashMap<>();
        model.put("Name", modelEmailProperties.getName());
        model.put("location", modelEmailProperties.getLocation());
        return model;
    }

}
