package com.yapp.web1.config;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(
        properties = "spring.config.location=/app/config/yappian/google.yml,"+"/app/config/yappian/s3.yml")
public class OAuthConfigTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8085;
    }

    // TODO 테스트 깨짐 수정
    @Test
    public void google로그인시도_oauth인증창 () throws Exception {
        given()
                .when()
                .redirects().follow(false)
                .get("/api/login")
                .then()
                .statusCode(302)
                .header("Location", containsString("https://accounts.google.com/o/oauth2/v2/auth"));
    }
}