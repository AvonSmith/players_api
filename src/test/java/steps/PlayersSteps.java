package steps;

import client.players.PlayersClient;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import module.players.AllUsersResp;
import module.players.CreatePlayerReq;
import module.players.Players;
import context.ScenarioContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static client.players.PlayersClient.createPlayer;
import static client.players.PlayersClient.getAllUsers;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayersSteps {

    private final ScenarioContext context;
    private Response response;
    private static final Faker faker = new Faker();

    public PlayersSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("Create {int} players")
    public void createPlayers(int amount) {
        List<Response> responses = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            String currencyCode = faker.number().digits(4);
            String email = faker.internet().emailAddress();
            String name = faker.name().firstName();
            String password = faker.number().digits(4);
            String surname = faker.name().lastName();
            String userName = String.format("%s_%s", faker.lorem().word(), faker.number().digits(4));
            CreatePlayerReq player = CreatePlayerReq.builder()
                    .currencyCode(currencyCode)
                    .email(email)
                    .name(name)
                    .passwordChange(password)
                    .passwordRepeat(password)
                    .surname(surname)
                    .username(userName)
                    .build();
            response = createPlayer(player, context.getAccessToken());
            responses.add(response);
        }

        context.setResponses(responses);
    }

    @Then("Status code is {int} and all created players match with schema")
    public void statusCodeIsAndResponseMatchesWithSchema(int statusCode) {
        assertThat(context.getResponses())
                .allSatisfy(resp -> {
                    ValidatableResponse validatableResponse = resp.then();
                    validatableResponse.statusCode(statusCode);
                    Players player = validatableResponse.extract().as(Players.class);

                    assertThat(player.getId()).isNotNull();
                    assertThat(player.getUsername()).isNotNull();
                    assertThat(player.getEmail()).isNotNull();
                    assertThat(player.getName()).isNotNull();
                    assertThat(player.getSurname()).isNotNull();
                });
    }

    @When("Get random created player by email")
    public void getUserByEmail() {
        int index = faker.random().nextInt(context.getResponses().size());
        response = context.getResponses().get(index);
        String email = response.jsonPath().getString("email");
        response = PlayersClient.getUserByEmail(email, context.getAccessToken());
        context.setResponse(response);

    }

    @Then("Status code is {int} and player match with schema")
    public void statusCodeIsAndPlayerMatchWithSchema(int statusCode) {
        ValidatableResponse validatableResponse = context.getResponse().then();
        validatableResponse.statusCode(statusCode);
        Players player = validatableResponse.extract().as(Players.class);

        assertThat(player.getId()).isNotNull();
        assertThat(player.getUsername()).isNotNull();
        assertThat(player.getEmail()).isNotNull();
        assertThat(player.getName()).isNotNull();
        assertThat(player.getSurname()).isNotNull();
    }

    @When("Get all players and sort by name")
    public void getAllUsersData() {
        response = getAllUsers(context.getAccessToken());
        List<AllUsersResp> users = new ArrayList<>(response.jsonPath().getList(".", AllUsersResp.class));
        users.sort(Comparator.comparing(AllUsersResp::getName));
        context.setUsers(users);
    }

    @When("Delete all players")
    public void deleteAllUsersData() {
        List<AllUsersResp> users = context.getUsers();
        users.forEach(u -> PlayersClient.deleteUser(u.getId(), context.getAccessToken()));
    }

    @Then("All players are deleted")
    public void allPlayersAreDeleted() {
        response = getAllUsers(context.getAccessToken());
        response.then().body("", org.hamcrest.Matchers.empty());
    }
}
