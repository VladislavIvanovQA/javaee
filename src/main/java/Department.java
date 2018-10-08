import java.util.HashSet;
import java.util.Set;

public class Department implements Unit {
    private String name;

    private Set<User> users = new HashSet<>();

    Department(String name){
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Set<User> getUsers() {
        return users;
    }

    void setUsers(User newUser) {
        users.add(newUser);
        newUser.setDepartment(this);
    }

    public int getPersonCount() {
        return getUsers().size();
    }
}
