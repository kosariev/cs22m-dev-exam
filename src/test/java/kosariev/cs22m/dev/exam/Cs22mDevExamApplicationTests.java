package kosariev.cs22m.dev.exam;

import kosariev.cs22m.dev.exam.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Cs22mDevExamApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void checkAuthValidUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("demo");
        userDTO.setPassword("demo");
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO);
        ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:8080/login", request, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("true");
    }

    @Test
    public void checkAuthNotExistUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("notexist");
        userDTO.setPassword("demo");
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO);
        ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:8080/login", request, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(result.getBody().contains("Login not found")).isTrue();
    }

    @Test
    public void checkAuthIncorrectPassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("demo");
        userDTO.setPassword("incorrect");
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO);
        ResponseEntity<String> result = this.restTemplate.postForEntity("http://localhost:8080/login", request, String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(result.getBody().contains("Incorrect password")).isTrue();
    }
}
