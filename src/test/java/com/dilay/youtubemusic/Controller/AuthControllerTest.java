package com.dilay.youtubemusic.Controller;

import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.model.request.auth.LoginRequest;
import com.dilay.youtubemusic.model.request.auth.register.RegisterRequest;
import com.dilay.youtubemusic.model.response.LoginResponse;
import com.dilay.youtubemusic.repository.UserRepository;
import com.dilay.youtubemusic.security.JwtService;
import com.dilay.youtubemusic.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ExtendWith(SpringExtension.class)


public class AuthControllerTest {
    @Autowired
    private AuthenticationService authenticationService ;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository ;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final String REGISTER_ENDPOINT = "/auth/register";
    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String NAME = "testuseeer";
    private static final String SURNAME =  "Test Surname"  ;
    private static final String EMAIL = "testest@gmail.com";
    private static final String PASSWORD = "password123";
    final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


//    @Test
//    void GetsUserAfterAddingToRepo(){
//        User user = new User();
//        user.setName(NAME+"1");
//        user.setSurname(SURNAME+"1");
//        user.setEmail(EMAIL+"1");
//        user.setUserRole(UserRole.USER);
//        user.setPasswordHash(PASSWORD+"1");
//
//                userRepository.save(user);
//
//        User response = userRepository.findUserByEmail(EMAIL);
//        assertNotNull(response);
//    }

@Test
    void registerTest() throws Exception {

        RegisterRequest registerRequest = new RegisterRequest()  ;
        registerRequest.setName(NAME);
        registerRequest.setSurname(SURNAME);
        registerRequest.setEmail(EMAIL);
        registerRequest.setPassword(PASSWORD);

       User response =  authenticationService.register(registerRequest);

        assertNotNull(response);
        User user = userRepository.findUserByEmail(EMAIL);
        assertNotNull(user);
        ResponseEntity<User> resp = restTemplate.postForEntity(REGISTER_ENDPOINT, registerRequest , User.class );
        assertEquals(HttpStatus.OK, resp.getStatusCode());

    }


    @Test
    void loginTest() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("tes@gmail.com");
        loginRequest.setPassword("test1234");

        LoginResponse loginResponse = authenticationService.login(loginRequest);
        assertNotNull(loginResponse);


        ResponseEntity<LoginResponse> templateLoginResponse = restTemplate.postForEntity(LOGIN_ENDPOINT,loginRequest ,LoginResponse.class);

         Assertions.assertEquals(HttpStatus.OK, templateLoginResponse.getStatusCode());
        Assertions.assertTrue(templateLoginResponse.hasBody());




    }

}
