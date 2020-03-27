package ru.geekbrains.java2.server.auth;

import java.sql.SQLException;

public interface AuthService {

    //String getUsernameByLoginAndPassword(String login, String password) throws ClassNotFoundException, SQLException;
    String getUsernameByLoginAndPasswordSql(String login, String password) throws ClassNotFoundException, SQLException;

    void start();
    void stop();

}
