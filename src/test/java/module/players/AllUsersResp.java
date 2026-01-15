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
public class AllUsersResp {

    private String id;
    @JsonProperty("currency_code")
    private String currencyCode;
    private String email;
    private String name;
    private String surname;
    private String username;
}
