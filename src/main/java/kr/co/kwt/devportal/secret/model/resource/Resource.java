package kr.co.kwt.devportal.secret.model.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

public interface Resource {

    String getId();

    ResourceType getResourceType();

    boolean isPreset();

    @Getter
    @RequiredArgsConstructor
    final class ResourceProvider {
        private final Map<ResourceType, Resource> resourceTypeResourceMap;
    }
}
