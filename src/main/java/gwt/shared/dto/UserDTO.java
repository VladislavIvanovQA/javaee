package gwt.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(prefix = "")
public class UserDTO implements IsSerializable, Comparable<UserDTO> {
    @NotNull
    private long number;
    @NotNull(message = "Login must contain at least 4 characters.")
    private String login = "";

    @NotNull(message = "Password must contain at least 4 characters.")
    private String password = "";

    @NotNull(message = "Name and second name must contain at least 4 characters.")
    private String name = "";

    @NotNull(message = "Phone not must contain at least 10 characters.")
    private String phone = "";

    private String email = "";

    private String sex = "";


    @Override
    public int compareTo(UserDTO o) {
        return (o == null || o.name == null) ? -1 : -o.name.compareTo(name);
    }

    public boolean equals(UserDTO o) {
        if (o instanceof UserDTO) {
            return number == ((UserDTO) o).number;
        }
        return false;
    }
}
