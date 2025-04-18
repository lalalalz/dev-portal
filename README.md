# DEV PORTAL

개발자를 위해 운영상 필요한 기능들을 제공하는 서비스입니다. 쉽게 개발자를 위한 어드민이라고 이해하면 좋을 것 같습니다.

# 시크릿(Secret) 관리 시스템

## 개요

현재 배포 잡을 생성할 때, hidden parameter로 쿠버네티스 시크릿을 생성할 데이터(JSON 구조)를 전달합니다. 이때, 해당 데이터의 Key 값을 어플리케이션에선 `${Key}` 형태로 사용이
가능합니다. **보통 리소스 또는 인프라의 연결 정보나 계정 정보가 이에 해당됩니다.**
서비스별로 잡을 생성할 때마다 해당 데이터(JSON)를 직접 작성하기가 번거롭고, 심지어 계정을 생성해줘야 하는 경우도 있습니다. (예를 들어, 서비스별로 MySQL 계정을 분리하는 경우)
이를 위해 서비스 이름을 작성하고, 사용할 자원 또는 인프라를 선택하면 그에 맞는 JSON 데이터를 생성해주는 기능을 제공합니다. 필요한 경우에는 계정을 직접 생성해서 JSON 데이터에 함께 내려줍니다.

## 핵심 개념

### 리소스(Resource)

외부 시스템(MySQL, Redis, Kafka 등)의 연결/계정 정보를 나타냅니다. 각 리소스 타입은 열거형에 정의되며, 특정 속성 클래스와 매핑됩니다. `ResourceType`

### 리소스 속성(Resource Properties)

리소스 타입별로 필요한 속성 정보를 담는 클래스입니다. 예를 들어:

- : 데이터베이스 연결정보 (username, password, url, driverClassName) `DataSourceProperties`
- : Redis 연결정보 `RedisProperties`
- : MongoDB 연결정보 `MongoProperties`

### 리소스 구성 템플릿(ResourceConfigurationTemplate)

공통으로 사용되는 자원에 대한 속성 정보를 담는 템플릿입니다. 이는 DB에 저장되며 시스템 시작 시 로드됩니다. 템플릿은 다음과 같은 정보를 포함합니다:

- 환경(Environment): 개발, 스테이징, 운영 등
- 리소스 타입(ResourceType): MySQL, Redis, Kafka 등
- 속성(Properties): 연결 정보, 설정값 등
- 계정 프로비저닝 지원 여부(supportsAccountProvisioning): 서비스별 계정 생성 가능 여부

### 리소스 구성(ResourceConfiguration)

특정 서비스에서 사용할 리소스 구성 정보로, 을 참조합니다. 프로비저닝이 필요한 경우 정보를 추가로 가집니다. `ResourceConfigurationTemplate``ProvisioningAccount`

### 프로비전(Provision)

필요시 서비스별 계정을 자동으로 생성하는 기능으로, 템플릿의 속성으로 지원 여부를 판단합니다. `supportsAccountProvisioning`

## 리소스 타입

현재 시스템에서 지원하는 리소스 타입:

| 리소스 타입 | 속성 클래스               | 설명                 |
|--------|----------------------|--------------------|
| MYSQL  | DataSourceProperties | MySQL 데이터베이스 연결 정보 |
| REDIS  | RedisProperties      | Redis 연결 정보        |
| MONGO  | DataSourceProperties | MongoDB 연결 정보      |
| KAFKA  | DataSourceProperties | Kafka 연결 정보        |

## 작동 방식

1. **템플릿 로드**: 시스템 시작 시 DB에서 리소스 구성 템플릿() 정보를 로드합니다. `ResourceConfigurationTemplate`
    - 공용 계정 템플릿: 계정 정보를 포함하며 모든 서비스가 공유
    - 프로비저닝 템플릿: 계정 생성이 필요한 리소스 타입을 나타냄

2. **리소스 구성 요청**: 사용자가 서비스 이름, 환경, 리소스 타입을 지정하여 리소스 구성을 요청합니다.
3. **템플릿 매칭**: 요청된 환경과 리소스 타입에 맞는 템플릿을 찾습니다.
4. **계정 프로비저닝(필요시)**: 템플릿이 계정 프로비저닝을 지원하는 경우(`supportsAccountProvisioning=true`), 해당 서비스를 위한 계정을 생성합니다.
5. **구성 생성**: 최종적으로 서비스에 필요한 리소스 구성()을 생성하고 저장합니다. `ResourceConfiguration`

## 신규 리소스 속성(Properties) 클래스 추가 방법

새로운 리소스 타입에 대한 속성 클래스를 추가하려면 다음 단계를 따릅니다:

1. **Properties 클래스 작성**:

``` java
   package kr.co.kwt.devportal.secret.v2.model.properties;
   
   import com.fasterxml.jackson.annotation.JsonInclude;
   import lombok.AccessLevel;
   import lombok.Builder;
   import lombok.Getter;
   import lombok.NoArgsConstructor;
   
   @Getter
   @JsonInclude(JsonInclude.Include.NON_NULL)
   @NoArgsConstructor(access = AccessLevel.PROTECTED)
   public class NewServiceProperties {
   
       private String property1;
       private String property2;
       private String property3;
       
       @Builder
       public NewServiceProperties(String property1, String property2, String property3) {
           this.property1 = property1;
           this.property2 = property2;
           this.property3 = property3;
       }
   }
```

1. **ResourceType 열거형 업데이트**:

``` java
   public enum ResourceType {
       MYSQL(DataSourceProperties.class),
       REDIS(RedisProperties.class),
       MONGO(DataSourceProperties.class),
       KAFKA(DataSourceProperties.class),
       NEW_SERVICE(NewServiceProperties.class),  // 새로운 리소스 타입 추가
       ;
   
       private final Class<?> propertiesClass;
       
       ResourceType(Class<?> propertiesClass) {
           this.propertiesClass = propertiesClass;
       }
   }
```

1. **필요시 프로비저닝 등록기(Register) 구현**:

``` java
   public class NewServiceProvisioningAccountRegister implements ProvisioningAccountRegister {
       @Override
       public ProvisioningAccount register(String service, Environment environment) {
           // 계정 생성 로직 구현
           return new ProvisioningAccount(/* 계정 정보 */);
       }
   
       @Override
       public boolean supports(ResourceType resourceType) {
           return resourceType == ResourceType.NEW_SERVICE;
       }
   }
```

1. **템플릿 등록**: API를 통해 새 리소스 타입에 대한 템플릿을 등록합니다.

``` 
   POST /resource-configuration/templates
   {
     "environment": "DEVELOPMENT",
     "resourceType": "NEW_SERVICE",
     "properties": {
       "property1": "value1",
       "property2": "value2",
       "property3": "value3"
     },
     "supportsAccountProvisioning": true
   }
```

## 템플릿 관리

템플릿은 REST API를 통해 관리됩니다:

### 템플릿 추가

``` 
POST /resource-configuration/templates
```

### 템플릿 조회

``` 
GET /resource-configuration/templates
```

## 리소스 구성 관리

### 리소스 구성 추가

``` 
POST /resource-configurations
{
  "service": "my-service",
  "environment": "DEVELOPMENT",
  "resourceTypes": ["MYSQL", "REDIS", "KAFKA"]
}
```

### 리소스 구성 조회

``` 
GET /resource-configurations?service=my-service&environment=DEVELOPMENT
```

## 프로비저닝 메커니즘

프로비저닝은 다음 과정을 통해 이루어집니다:

1. 리소스 구성 요청 시, 시스템은 요청된 리소스 타입에 해당하는 템플릿을 찾습니다.
2. 템플릿의 값이 인 경우, 계정 프로비저닝을 진행합니다. `supportsAccountProvisioning``true`
3. 해당 리소스 타입을 지원하는 구현체를 찾아 메소드를 호출합니다. `ProvisioningAccountRegister``register`
4. 생성된 계정 정보는 객체에 저장되어 에 포함됩니다. `ProvisioningAccount``ResourceConfiguration`

## 사용 예시

### MySQL 계정 프로비저닝 예시

1. MySQL 템플릿 등록:

``` 
   POST /resource-configuration/templates
   {
     "environment": "DEVELOPMENT",
     "resourceType": "MYSQL",
     "properties": {
       "url": "jdbc:mysql://db-hostname:3306/",
       "driverClassName": "com.mysql.cj.jdbc.Driver"
     },
     "supportsAccountProvisioning": true
   }
```

1. 서비스를 위한 리소스 구성 요청:

``` 
   POST /resource-configurations
   {
     "service": "user-service",
     "environment": "DEVELOPMENT",
     "resourceTypes": ["MYSQL"]
   }
```

1. 결과 (프로비저닝 후):

``` json
   {
     "id": "...",
     "service": "user-service",
     "environment": "DEVELOPMENT",
     "resourceConfigurationTemplate": {
       "id": "...",
       "environment": "DEVELOPMENT",
       "resourceType": "MYSQL",
       "properties": {
         "url": "jdbc:mysql://db-hostname:3306/",
         "driverClassName": "com.mysql.cj.jdbc.Driver"
       },
       "supportsAccountProvisioning": true
     },
     "provisioningAccount": {
       "username": "user_service_user",
       "password": "generated_password",
       "database": "user_service_db"
     }
   }
```

## 아키텍처

시크릿 관리 시스템은 다음 컴포넌트로 구성됩니다:

- **컨트롤러**: API 엔드포인트 제공
- **서비스**: 비즈니스 로직 처리
- **레포지토리**: MongoDB에 데이터 저장 및 조회
- **변환기(Converter)**: MongoDB 문서와 자바 객체 간 변환
- **프로비저닝 등록기**: 리소스 타입별 계정 생성 로직

## 주의사항

- 프로비저닝이 필요한 리소스 타입은 반드시 해당 구현체가 등록되어 있어야 합니다. `ProvisioningAccountRegister`
- 리소스 타입과 속성 클래스의 매핑은 열거형에서 관리됩니다. `ResourceType`
- 모든 속성 클래스는 Jackson 직렬화/역직렬화를 위한 적절한 어노테이션을 포함해야 합니다.
