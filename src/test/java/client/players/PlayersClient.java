package client.players;

import io.restassured.response.Response;
import module.players.CreatePlayerReq;
import utils.FrameworkProperties;

import static specification.Spec.requestSpec;
import static io.restassured.RestAssured.given;

public class PlayersClient {

    private static final String CREATE_URI = "/api/automationTask/create";
    private static final String GET_USER_URI = "/api/automationTask/getOne";
    private static final String GET_ALL_USERS_URI = "/api/automationTask/getAll";
    private static final String DELETE_USER_URI = "/api/automationTask/deleteOne/{userId}";

    public static Response createPlayer(CreatePlayerReq playerReq, String accessToken) {
        return given(requestSpec(new FrameworkProperties().getProperty("base_url")))
                .auth().oauth2(accessToken)
                .body(playerReq)
                .post(CREATE_URI);
    }

    public static Response getUserByEmail(String email, String accessToken) {
        String body = String.format("{\"email\": \"%s\"}", email);
        return given(requestSpec(new FrameworkProperties().getProperty("base_url")))
                .auth().oauth2(accessToken)
                .body(body)
                .post(GET_USER_URI);
    }

    public static Response getAllUsers(String accessToken) {
        return given(requestSpec(new FrameworkProperties().getProperty("base_url")))
                .auth().oauth2(accessToken)
                .get(GET_ALL_USERS_URI);
    }

    public static Response deleteUser(String userId, String accessToken) {
        return given(requestSpec(new FrameworkProperties().getProperty("base_url")))
                .auth().oauth2(accessToken)
                .pathParam("userId", userId)
                .delete(DELETE_USER_URI);
    }
}
