package kr.co.kwt.devportal.secret.model.secret;


import kr.co.kwt.devportal.secret.model.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@Document(collection = "secrets")
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class Secret {

    @Id
    private String id;
    private String service;
    private Environment environment;
    @DocumentReference
    private List<Resource> resources;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
