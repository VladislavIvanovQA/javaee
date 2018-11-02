package entity.xml.util;

import entity.xml.UserJaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUser {
    private String getRandString(){
        StringBuilder randString = new StringBuilder();
        int count = ThreadLocalRandom.current().nextInt(5, 15 + 1);
        String symbols = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        for(int i = 0; i<count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        return String.valueOf(randString);
    }

    private long getRandLong(){
        long min = 1234L;
        long max = 12356L;
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public List<UserJaxb> getRandomUser(int count){
        List<UserJaxb> users = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            UserJaxb user = new UserJaxb();
            user.setUserno(getRandLong());
            user.setName(getRandString());
            user.setJob(getRandString());
            user.setSal(getRandLong());

            users.add(user);
        }
        return users;
    }

}
