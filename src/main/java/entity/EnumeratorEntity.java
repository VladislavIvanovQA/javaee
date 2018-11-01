package entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnumeratorEntity implements Serializable {
    private long eid;
    private UserEntity userno;
    private String pnumber;
    private String enumber;
    private String efollowers;
}
