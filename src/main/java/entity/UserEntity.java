package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class UserEntity{

    @Id
    @Column(name = "USERNO")
    private long number;

    @Basic
    @Column(name = "ULOGIN")
    private String login;

    @Basic
    @Column(name = "UPASSWORD")
    private String password;

    @Basic
    @Column(name = "UIO")
    private String name;

    @Basic
    @Column(name = "UPHONE")
    private String phone;

    @Basic
    @Column(name = "UEMAIL")
    private String email;

    @Basic
    @Column(name = "USEX")
    private String sex;
}
