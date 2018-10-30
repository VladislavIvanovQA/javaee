package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ENUMERATOR")
public class EnumeratorEntity implements Serializable {

    @Id
    @Column(name = "EID")
    private long eid;

    @ManyToOne
    @JoinColumn(name = "USERNO", referencedColumnName = "USERNO")
    private UserEntity userno;

    @Basic
    @Column(name = "EPUBLICATION")
    private String pnumber;

    @Basic
    @Column(name = "ESUBSCRIBE")
    private String enumber;

    @Basic
    @Column(name = "EFOLLOWERS")
    private String efollowers;
}
