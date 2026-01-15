package module.players;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerReq {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String email;
    private String name;
    @JsonProperty("password_change")
    private String passwordChange;
    @JsonProperty("password_repeat")
    private String passwordRepeat;
    private String surname;
    private String username;
}