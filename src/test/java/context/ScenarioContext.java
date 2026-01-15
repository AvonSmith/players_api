package context;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import module.players.AllUsersResp;

import java.util.List;

@Setter
@Getter
public class ScenarioContext {

    private String accessToken;
    private Response response;
    private List<Response> responses;
    private List<AllUsersResp> users;

}
