package com.dilay.youtubemusic.Controller;

import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.UserRole;
import com.dilay.youtubemusic.model.request.auth.LoginRequest;
import com.dilay.youtubemusic.model.request.user.CreateUserRequest;
import com.dilay.youtubemusic.model.request.user.UpdateUserRequest;
import com.dilay.youtubemusic.model.response.LoginResponse;
import com.dilay.youtubemusic.repository.UserRepository;
import com.dilay.youtubemusic.security.JwtService;
import com.dilay.youtubemusic.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final String NAME = "testuAAser";
    private static final String SURNAME =  "TesAAt Surname"  ;
    private static final String EMAIL = "testtt@gmail.com";
    private static final String PASSWORD = "password123";
    @Autowired
    private UserRepository repository ;
    @Autowired
    private AuthenticationService authenticationService ;
    @Autowired
    private JwtService jwtService;

    private static final int id = 1 ;
    @Autowired
    private TestRestTemplate template;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void whenAuthRequest_ThenStatus200() {
        loginUsing();
        String token = jwtService.createToken("user1");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = template.exchange(
                "/user/user1",
                HttpMethod.GET,
                request,
                String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    void testaddUser_ThenStatusOK() {
        loginUsing();
        String token = jwtService.createToken("user1");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName(NAME);
        createUserRequest.setSurname(SURNAME);
        createUserRequest.setEmail(EMAIL);
        createUserRequest.setPassword(PASSWORD);
        createUserRequest.setUserRole(UserRole.USER);

        HttpEntity<CreateUserRequest> requestEntity = new HttpEntity<>(createUserRequest, headers);

        ResponseEntity<User> response = template.postForEntity(
                "/user",
                requestEntity,
                User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NAME, response.getBody().getName());
    }
    @Test
    void updateUser() {
        loginUsing();
        HttpHeaders headers = new HttpHeaders();
        String token = jwtService.createToken("user1");
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);


        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setName("testerName");
        updateUserRequest.setSurname("testerSurname");
        updateUserRequest.setUserRole(UserRole.ADMIN);

         HttpEntity<UpdateUserRequest> request = new HttpEntity<>(updateUserRequest, headers);
        ResponseEntity<User> response = template
                .exchange("/user/update/user2" , HttpMethod.PUT , request, User.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals( "testerName", response.getBody().getName());
    }





    @Test
    void deleteUser() {
        loginUsing();
        HttpHeaders headers = new HttpHeaders();
        String token = jwtService.createToken("user1");
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> request = new HttpEntity<>(null, headers);


        ResponseEntity<Void> response = template.exchange("/user/delete/user2", HttpMethod.DELETE, request, Void.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    public void loginUsing(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("tes@gmail.com");
        loginRequest.setPassword("test1234");

        LoginResponse loginResponse = authenticationService.login(loginRequest);
    }
}
