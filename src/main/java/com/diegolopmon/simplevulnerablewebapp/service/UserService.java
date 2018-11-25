package com.diegolopmon.simplevulnerablewebapp.service;

import com.diegolopmon.simplevulnerablewebapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private EntityManager entityManager;

    @Autowired
    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> search(String firstName, String lastName) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM USER";

        if (!firstName.isEmpty() || !lastName.isEmpty()) {
            sql += " WHERE ";
        }
        if (!firstName.isEmpty()) {
            sql += " FIRST_NAME='" + firstName + "'";
        }
        if (!lastName.isEmpty()) {
            if (firstName.isEmpty()) {
                sql += " LAST_NAME='" + lastName + "'";
            } else {
                sql += " AND LAST_NAME='" + lastName + "'";
            }
        }

        LOGGER.info(String.format("SQL query: %s", sql));

        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(Long.valueOf(resultSet.getString("ID")), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getString("PASSWORD"));
            userList.add(user);
        }
        return userList;
    }

}
