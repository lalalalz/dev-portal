package kr.co.kwt.devportal.secret.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractResource implements Resource {

    private ResourceType resourceType;
    private boolean isPreset;
}
