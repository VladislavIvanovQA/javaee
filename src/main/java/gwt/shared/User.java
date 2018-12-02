package gwt.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(prefix = "")
public class User implements IsSerializable {
    @NotNull
    @Size(min = 4, message = "Login must contain at least 4 characters.")
    private String login;

    @NotNull
    @Size(min = 4, message = "Password must contain at least 4 characters.")
    private String password;

    private String session;

    private Date expires;
}
