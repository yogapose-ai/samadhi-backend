package com.capstone.samadhi.security.service.response;

import java.util.Map;

public class GoogleResponse{
    private final Map<String, Object> attributes;

    public GoogleResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    public String getProviderEmail() {
        return String.valueOf(attributes.get("email"));
    }

    public String getProviderName() {
        return String.valueOf(attributes.get("name"));
    }

    public String getProfilePath() {
        return String.valueOf(attributes.get("picture"));
    }
}
