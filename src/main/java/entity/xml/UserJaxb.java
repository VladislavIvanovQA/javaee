package entity.xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@Data
@ToString
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserJaxb {

    @XmlAttribute(required = true)
    private long userno;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String job;

    @XmlElement
    private long sal;

    @XmlAttribute(required = true)
    private String maxAverage = "none";

}
