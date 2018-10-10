public class Main {
    public static void main(String[] args) {
        User first = new User("test","test","Тестов Тест Тестович","79000000001",
               "test@test.ru", 1000f);
        User second = new User("test1","test","Тестов Тест Тестович1", "79000000002",
                "test@test.ru", 2000f);
        User three = new User("test2","test","Тестов Тест Тестович2", "79000000003",
                "test@test.ru", 3000f);
        User four = new User("test3","test","Тестов Тест Тестович3", "79000000004",
                "test@test.ru", 4000f);
        User five = new User("test4","test","Тестов Тест Тестович4","79000000005",
                "test@test.ru", 5000f);
        User sex = new User("test5","test","Тестов Тест Тестович5","79000000006",
                "test@test.ru",6000f);
        User seven = new User("test6","test","Тестов Тест Тестович6","79000000007",
                "test@test.ru", 7000f);

        first.setPosition(Position.employee);
        second.setPosition(Position.hr);
        three.setPosition(Position.accountant);
        four.setPosition(Position.director);
        five.setPosition(Position.employee);
        sex.setPosition(Position.employee);
        seven.setPosition(Position.employee);

        Department programmers = new Department("Программисты");
        Department hrs = new Department("Менеджеры");
        Department accountant = new Department("Бухгалтера");

        programmers.setUsers(first);
        programmers.setUsers(five);
        programmers.setUsers(sex);
        programmers.setUsers(seven);
        hrs.setUsers(second);
        hrs.setUsers(four);
        accountant.setUsers(three);

        User users[] = new User[7];
        users[0]= first;
        users[1] = second;
        users[2] = three;
        users[3] = four;
        users[4] = five;
        users[5] = sex;
        users[6] = seven;

        new UserAccesses().sort(users);

        for (User user : users) {
            System.out.println(user.getFio() + " работает в должности " + user.getPosition() +
                    "с ролью доступа " + user.getAccessRights());
            System.out.println("Относится к отделу " + user.getDepartment().getName());
            System.out.println("В отделе " + user.getDepartment().getName() + " работает "
                    + user.getDepartment().getPersonCount() + " человек.");
            System.out.println();
        }

        ListUsers menu = new ListUsers();
        menu.showEmployees(users);



    }
}
