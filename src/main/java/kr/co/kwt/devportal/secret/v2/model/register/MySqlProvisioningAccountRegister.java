package kr.co.kwt.devportal.secret.v2.model.register;

import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MySqlProvisioningAccountRegister implements ProvisioningAccountRegister {

    private final JdbcTemplate devJdbcTemplate;
    private final JdbcTemplate prodJdbcTemplate;

    @Override
    public ProvisioningAccount register(String service, Environment environment) {
        try {
            // 기본값 설정
            if (service == null || service.isEmpty()) {
                throw new MySqlProvisioningAccountRegisterException("service is not specified");
            }

            // 환경에 따라 적절한 JdbcTemplate 선택
            JdbcTemplate jdbcTemplate = (environment == Environment.DEV)
                    ? devJdbcTemplate
                    : prodJdbcTemplate;

            // 랜덤 사용자 이름과 비밀번호 생성
            String username = generateUsername(service);
            String password = generateStrongPassword();

            // 사용자 생성 - 172.16.* IP 대역만 허용
//            jdbcTemplate.execute("CREATE USER '" + username + "'@'172.16.%' IDENTIFIED BY '" + password + "'");
            jdbcTemplate.execute("CREATE USER '" + username + "'@'192.168.%.%' IDENTIFIED BY '" + password + "'");


            // 데이터베이스에 권한 부여 - 172.16.* IP 대역만 허용
            jdbcTemplate.execute("GRANT ALL PRIVILEGES ON `" + "sys" + "`.* TO '" + username + "'@'192.168.%.%'");
//            jdbcTemplate.execute("GRANT ALL PRIVILEGES ON `" + service + "`.* TO '" + username + "'@'172.16.%'");

            jdbcTemplate.execute("FLUSH PRIVILEGES");

            // 생성된 계정 정보 반환
            return new ProvisioningAccount(username, password);
        }
        catch (Exception e) {
            throw new MySqlProvisioningAccountRegisterException(e);
        }
    }

    private String generateUsername(String prefix) {
        // UUID의 일부를 사용하여 고유한 이름 생성
        String uniquePart = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return prefix + "_" + uniquePart;
    }

    /**
     * 강력한 비밀번호 생성 메소드
     */
    @SuppressWarnings("deprecation")
    private String generateStrongPassword() {
        // 16자리 랜덤 문자열 (문자, 숫자, 특수 문자 포함)
        String letters = RandomStringUtils.randomAlphabetic(8);
        String numbers = RandomStringUtils.randomNumeric(4);
        String specialChars = RandomStringUtils.random(4, "!@#$%^&*()_-+=<>?");

        // 문자들을 섞어서 반환
        String combined = letters + numbers + specialChars;
        char[] chars = combined.toCharArray();

        // 간단한 셔플 알고리즘
        for (int i = 0; i < chars.length; i++) {
            int randomIndex = (int) (Math.random() * chars.length);
            char temp = chars[i];
            chars[i] = chars[randomIndex];
            chars[randomIndex] = temp;
        }

        return new String(chars);
    }

    @Override
    public boolean supports(ResourceType resourceType) {
        return ResourceType.MYSQL.equals(resourceType);
    }
}