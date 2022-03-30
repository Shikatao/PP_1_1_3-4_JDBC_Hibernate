package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {


//    Hibernate открывает отличные возможности по управлению БД без скриптов:
//    Добавление пользователя в БД: session.save(user);
//    Получение пользователя по id, чтобы потом удалить: User user = (User) session.get(User.class, id);
//    Получение списка всех пользователей: List<User> users = session.createCriteria(User.class).list();
//    Всё это быстро гуглится, лучше ещё дополнительно почитайте доки по всем незнакомым методам в интернете.
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` " +
                "( `id` BIGINT(19) NOT NULL AUTO_INCREMENT, `name` VARCHAR(255) NOT NULL, " +
                "`lastName` VARCHAR(255) NOT NULL," +
                " `age` INT(3) NOT NULL, PRIMARY KEY (`id`)," +
                " UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sql = "DROP TABLE IF EXISTS `users`";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User")
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
