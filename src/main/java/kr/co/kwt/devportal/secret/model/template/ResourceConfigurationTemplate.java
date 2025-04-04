package kr.co.kwt.devportal.secret.model.template;

import kr.co.kwt.devportal.secret.model.ResourceType;

public interface ResourceConfigurationTemplate<T> {

    String getId();
    
    T getProperties();

    ResourceType getResourceType();

    boolean isSupportsAccountProvisioning();
}
