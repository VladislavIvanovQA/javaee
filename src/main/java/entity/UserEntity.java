package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity{
    private long number;
    private String login;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String sex;
}
