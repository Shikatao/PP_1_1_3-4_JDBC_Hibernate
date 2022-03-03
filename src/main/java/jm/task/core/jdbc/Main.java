package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        В методе main класса Main должны происходить следующие операции:
//
//        Создание таблицы User(ов)
//        Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль
//        ( User с именем – name добавлен в базу данных )
//        Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
//        Очистка таблицы User(ов)
//        Удаление таблицы
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Василий", "Морозов", (byte) 53);
        userService.saveUser("Петр", "Рачковский", (byte) 34);
        userService.saveUser("Марина", "Мирославская", (byte) 43);
        userService.saveUser("Юлия", "Сушина", (byte) 22);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.disconnection(Util.getConnection());
        // реализуйте алгоритм здесь
    }
}
