package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import utils.FrameworkProperties;
import context.ScenarioContext;

import static client.auth.AuthClient.login;
import static org.hamcrest.Matchers.notNullValue;

public class AuthSteps {

    private final FrameworkProperties properties = new FrameworkProperties();
    private final ScenarioContext context;
    private Response response;

    public AuthSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("User login with email {string} and {string}")
    public void post_api_tester_login_user_login(String userEmail, String userPass) {
        response = login(properties.getProperty(userEmail), properties.getProperty(userPass));
        context.setAccessToken(response.jsonPath().getString("accessToken"));
    }

    @Then("Status code is {int} and response contains access token")
    public void loginIsSuccessful(int statusCode) {
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(statusCode);
        validatableResponse.body("accessToken", notNullValue());
    }
}
