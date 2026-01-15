package client.auth;

import io.restassured.response.Response;
import module.auth.AuthReq;
import utils.FrameworkProperties;

import static specification.Spec.requestSpec;
import static io.restassured.RestAssured.given;

public class AuthClient {

    private static final String LOGIN_URI = "/api/tester/login";

    public static Response login(String email, String password) {
        return given(requestSpec(new FrameworkProperties().getProperty("base_url")))
                .body(AuthReq.builder()
                        .email(email)
                        .password(password)
                        .build())
                .post(LOGIN_URI);
    }
}
