package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        userDao.saveUser("Катя", "Светлова", (byte) 12);
        userDao.saveUser("Cвета", "Светлова", (byte) 23);
        userDao.saveUser("Нюра", "Светлова", (byte) 34);
        System.out.println(userDao.getAllUsers());
        userDao.removeUserById(2);
        System.out.println(userDao.getAllUsers());
        userDao.dropUsersTable();
        System.out.println(userDao.getAllUsers());
    }
}
