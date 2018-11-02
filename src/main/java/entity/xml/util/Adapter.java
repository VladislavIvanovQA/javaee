package entity.xml.util;

import entity.xml.UserJaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter extends XmlAdapter<String, UserJaxb> {
    public UserJaxb unmarshal(String v) throws Exception {
        UserJaxb userJaxb = new UserJaxb();
        userJaxb.setName(v);
        return userJaxb;
    }

    public String marshal(UserJaxb v) throws Exception {
        return v == null ? null : v.toString();
    }
}
