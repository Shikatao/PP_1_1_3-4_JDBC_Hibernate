package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl extends Util implements UserDao {


//    Hibernate открывает отличные возможности по управлению БД без скриптов:
//    Добавление пользователя в БД: session.save(user);
//    Получение пользователя по id, чтобы потом удалить: User user = (User) session.get(User.class, id);
//    Получение списка всех пользователей: List<User> users = session.createCriteria(User.class).list();
//    Всё это быстро гуглится, лучше ещё дополнительно почитайте доки по всем незнакомым методам в интернете.


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` " +
                "( `id` BIGINT(19) NOT NULL AUTO_INCREMENT, `name` VARCHAR(255) NOT NULL, " +
                "`lastName` VARCHAR(255) NOT NULL," +
                " `age` INT(3) NOT NULL, PRIMARY KEY (`id`)," +
                " UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
        try {
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS `users`";
        try {
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = null;

        try {
            users = session.createQuery("from User")
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
