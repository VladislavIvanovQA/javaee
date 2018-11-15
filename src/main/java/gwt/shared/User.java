package gwt.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(prefix = "")
public class User implements IsSerializable {

    @NotNull
    private long number;

    @NotNull
    @Size(min = 4, message = "Login must contain at least 4 characters.")
    private String login;

    @NotNull
    @Size(min = 4, message = "Password must contain at least 4 characters.")
    private String password;

    @NotNull
    @Size(min = 4, message = "Name and second name must contain at least 4 characters.")
    private String name;

    @NotNull
    @Size(max = 10, message = "Phone not must contain at least 10 characters.")
    private String phone;

    @NotNull
    @Size(min = 4)
    private String email;

    @NotNull
    @Size(min = 2)
    private String sex;
}
