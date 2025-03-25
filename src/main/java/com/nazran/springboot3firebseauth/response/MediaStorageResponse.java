package com.nazran.springboot3firebseauth.response;

import com.nazran.springboot3firebseauth.enums.ReferenceType;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for media storage response.
 */
@Data
@Builder
public class MediaStorageResponse {
    private Integer ownerId;

    private ReferenceType referenceType;

    private Integer referenceId;

    private String externalId;

    private String url;

    private String mimeType;
}