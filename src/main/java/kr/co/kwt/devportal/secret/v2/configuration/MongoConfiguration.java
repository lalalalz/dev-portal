package kr.co.kwt.devportal.secret.v2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext
    ) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);

        // _class 필드 제거
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        // 커스텀 컨버터 등록
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new MongoReadingConverter());

        // 추가: MongoDB에서 읽어오지 않고 저장할 때만 쓰는 경우 WritingConverter도 필요할 수 있음
        // converterList.add(new MongoWritingConverter());

        MongoCustomConversions customConversions = new MongoCustomConversions(converterList);
        converter.setCustomConversions(customConversions);

        // 필요한 경우 커스텀 컨버전 등록 후 afterPropertiesSet 호출
        converter.afterPropertiesSet();

        return converter;
    }

}
