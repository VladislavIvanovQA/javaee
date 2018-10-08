class ListUsers {
    void showEmployees(User[] users){
        System.out.println("Список сотрудников:");
        for (int i =0; i < users.length; i++){
            if(users[i] != null){
                System.out.println(users[i].getFio() +" - " + users[i].getPosition());
            }
        }
    }

}
