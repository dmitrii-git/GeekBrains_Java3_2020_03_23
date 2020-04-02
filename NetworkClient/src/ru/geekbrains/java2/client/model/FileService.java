package ru.geekbrains.java2.client.model;

import java.io.*;

public class FileService {
    public void writeToFile(String user, String message) throws IOException {
        FileOutputStream out = new FileOutputStream(user + ".txt", true);
        out.write(message.getBytes());
        out.close();

    }
    public String readFromFile(String user) throws IOException {
        String message = null;
        StringBuffer strBuffer = new StringBuffer("");
        FileInputStream in = new FileInputStream(user + ".txt");
        int i;
        while((i=in.read())!= -1){
            strBuffer.append((char)i);
        }
        in.close();
        message = strBuffer.toString();
        System.out.println(message);
        return message;
    }
}
