package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement createTable = null;


        try {
            connection.setAutoCommit(false);
            createTable = connection.createStatement();
            createTable.executeUpdate("CREATE TABLE IF NOT EXISTS `mydbtest`.`users` " +
                    "( `id` BIGINT(19) NOT NULL AUTO_INCREMENT, `name` VARCHAR(255) NOT NULL, " +
                    "`lastName` VARCHAR(255) NOT NULL," +
                    " `age` INT(3) NOT NULL, PRIMARY KEY (`id`)," +
                    " UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(createTable);
        }


    }

    public void dropUsersTable() {
        Statement dropTable = null;
        try {
            connection.setAutoCommit(false);
            dropTable = connection.createStatement();
            dropTable.executeUpdate("DROP TABLE IF EXISTS `users`");
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(dropTable);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users  (name, lastName, age) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(preparedStatement);
        }

    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM USERS WHERE ID=?";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(preparedStatement);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(resultSet);
            closeQuietly(statement);
        }
        return userList;
    }

    public void cleanUsersTable() {
        Statement cleanTable = null;
        try {
            connection.setAutoCommit(false);
            cleanTable = connection.createStatement();
            cleanTable.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(cleanTable);
        }
    }
}
