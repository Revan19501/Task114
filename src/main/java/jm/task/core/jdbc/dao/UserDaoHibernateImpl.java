package jm.task.core.jdbc.dao;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT,  " +
            "name VARCHAR(80), lastName VARCHAR(100), " +
            "age TINYINT (100), " +
            "PRIMARY KEY (id));";
    Util util = new Util();
    SessionFactory sf = util.getFactory();

    public UserDaoHibernateImpl() {

    }

    public void createUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            Transaction transaction = sf.openSession().getTransaction();
            transaction.rollback();
        }
    }


    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            Transaction transaction = sf.openSession().getTransaction();
            transaction.rollback();
        }

    }


    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sf.openSession()){
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        }catch (Exception e) {
            Transaction transaction = sf.openSession().getTransaction();
            transaction.rollback();
        }
    }

    public void removeUserById(long id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            System.out.println("Удаляем прользователя: " + user.getName() + " " + user.getLastName());
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e) {
            Transaction transaction = sf.openSession().getTransaction();
            transaction.rollback();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Session session = sf.openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            Transaction transaction = sf.openSession().getTransaction();
            transaction.rollback();
            System.out.println("Такой таблицы нет");
        }
        return users;
    }

    public void cleanUsersTable() {

        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            Transaction transaction = sf.openSession().getTransaction();
            transaction.rollback();
        }
    }
}
