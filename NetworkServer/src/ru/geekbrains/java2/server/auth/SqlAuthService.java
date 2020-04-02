package ru.geekbrains.java2.server.auth;


import java.sql.SQLException;
import java.sql.*;



public class SqlAuthService implements AuthService {

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    private static int BaseCreate = 0;


    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:users.db");


    }



    public String getUsernameByLoginAndPasswordSql(String login, String password) throws ClassNotFoundException, SQLException {

        this.Conn();
        this.CreateDB();
        this.WriteDB();

        resSet = statmt.executeQuery("SELECT * FROM users");
        while (resSet.next()) {
            String loginSql = resSet.getString("login");
            String passwordSql = resSet.getString("password");
            String usernameSql = resSet.getString("username");
            System.out.println(loginSql + passwordSql + usernameSql);
            if (login.equals(loginSql) && password.equals(passwordSql)) {
                return usernameSql;
            }

        }
        this.CloseDB();
        return null;
    }

    public void start() {

    }

    public void stop() {

    }
    public static void CreateDB () throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text, 'username' text);");
        return;

    }
    public static void WriteDB () throws SQLException
    {
        if (BaseCreate == 0) {
            statmt.execute("INSERT INTO 'users' ('login', 'password', 'username') VALUES ('login1', 'pass1', 'username1'); ");
            statmt.execute("INSERT INTO 'users' ('login', 'password', 'username') VALUES ('login2', 'pass2', 'username2'); ");
            statmt.execute("INSERT INTO 'users' ('login', 'password', 'username') VALUES ('login3', 'pass3', 'username3'); ");
            BaseCreate = 1;
        }
    }
    public static void CloseDB () throws ClassNotFoundException, SQLException
    {
        resSet.close();
        statmt.close();
        conn.close();
    }
}
