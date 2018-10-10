import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserAccesses{
    private Set<User> employeeList = new HashSet<>();
    private Set<User> accountantList = new HashSet<>();
    private Set<User> hrList = new HashSet<>();
    private User director[] = new User[1];

    public void sort(User[] users){
        for (User user: users){
            switch (user.getPosition()){
                case employee:
                    employeeList.add(user);
                    user.setAccessRights(AccessRights.VIEW_EMPLOYEES);
                    break;
                case accountant:
                    accountantList.add(user);
                    user.setAccessRights(AccessRights.EDIT_SALARY);
                    break;
                case hr:
                    hrList.add(user);
                    user.setAccessRights(AccessRights.EDIT_EMPLOYEES);
                    break;
                case director:
                    director[0] = user;
                    user.setAccessRights(AccessRights.ALL_ACCESS);
                    break;
            }
        }
    }
}
