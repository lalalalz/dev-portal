package kr.co.kwt.devportal.secret.model.register;

import kr.co.kwt.devportal.secret.model.ResourceType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MySqlProvisioningAccountRegister implements ProvisioningAccountRegister {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProvisioningAccount register(String service) {
        try {
            // 기본값 설정
            if (service == null || service.isEmpty()) {
                service = "user";
            }

            // 랜덤 사용자 이름과 비밀번호 생성
            String username = generateUsername(service);
            String password = generateStrongPassword();

            // 사용자 생성
            jdbcTemplate.execute("CREATE USER '" + username + "'@'%' IDENTIFIED BY '" + password + "'");

            // 데이터베이스에 권한 부여
            jdbcTemplate.execute("GRANT ALL PRIVILEGES ON `" + service + "`.* TO '" + username + "'@'%'");
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
