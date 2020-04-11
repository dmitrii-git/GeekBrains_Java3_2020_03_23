package ru.geekbrains.java2.server.client;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.CommandType;
import ru.geekbrains.java2.client.command.AuthCommand;
import ru.geekbrains.java2.client.command.BroadcastMessageCommand;
import ru.geekbrains.java2.client.command.PrivateMessageCommand;
import ru.geekbrains.java2.server.NetworkServer;


import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientHandler {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private final NetworkServer networkServer;
    private final Socket clientSocket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String nickname;

    public ClientHandler(NetworkServer networkServer, Socket socket) {
        this.networkServer = networkServer;
        this.clientSocket = socket;
    }

    public void run() {
        doHandle(clientSocket);
    }

    private void doHandle(Socket socket) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            //new Thread(() -> {
            executorService.execute(() -> {
                        try {
                            ClientHandler.this.authentication();
                            ClientHandler.this.readMessages();
                        } catch (IOException | SQLException | ClassNotFoundException e) {
                            LOGGER.info("Соединение с клиентом " + nickname + " было закрыто!");
                            System.out.println("Соединение с клиентом " + nickname + " было закрыто!");
                        } finally {
                            ClientHandler.this.closeConnection();
                        }
            });
            executorService.shutdown();
            //}).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            networkServer.unsubscribe(this);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }
            switch (command.getType()) {
                case END:
                    LOGGER.info("Received 'END' command");
                    System.out.println("Received 'END' command");
                    return;
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommand commandData = (PrivateMessageCommand) command.getData();
                    String receiver = commandData.getReceiver();
                    String message = commandData.getMessage();
                    networkServer.sendMessage(receiver, Command.messageCommand(nickname, message));
                    break;
                }
                case BROADCAST_MESSAGE: {
                    BroadcastMessageCommand commandData = (BroadcastMessageCommand) command.getData();
                    String message = commandData.getMessage();
                    networkServer.broadcastMessage(Command.messageCommand(nickname, message), this);
                    break;
                }
                default:
                    LOGGER.error("Unknown type of command : " + command.getType());
                    System.err.println("Unknown type of command : " + command.getType());
            }
        }
    }

    private Command readCommand() throws IOException {
        try {
             return (Command) in.readObject();
        } catch (ClassNotFoundException e) {

            String errorMessage = "Unknown type of object from client!";
            LOGGER.error(errorMessage);
            System.err.println(errorMessage);
            e.printStackTrace();
            sendMessage(Command.errorCommand(errorMessage));
            return null;
        }
    }

    private void authentication() throws IOException, SQLException, ClassNotFoundException {
        //new Thread(() -> {
        executorService.execute(() -> {
            try {
                Thread.sleep(120000);
                ClientHandler.this.closeConnection();
            } catch (InterruptedException e) {
            }
        });
        executorService.shutdown();
        //}).start();
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }
            if (command.getType() == CommandType.AUTH) {
                boolean successfulAuth = processAuthCommand(command);
                if (successfulAuth){
                    return;
                }
            } else {
                LOGGER.error("Unknown type of command for auth process: " + command.getType());
                System.err.println("Unknown type of command for auth process: " + command.getType());
            }
        }
    }
    private boolean processAuthCommand(Command command) throws IOException, SQLException, ClassNotFoundException {
        AuthCommand commandData = (AuthCommand) command.getData();
        String login = commandData.getLogin();
        String password = commandData.getPassword();
        String username = networkServer.getAuthService().getUsernameByLoginAndPasswordSql(login, password);
        if (username == null) {
            LOGGER.error("Отсутствует учетная запись по данному логину и паролю!");
            Command authErrorCommand = Command.authErrorCommand("Отсутствует учетная запись по данному логину и паролю!");
            sendMessage(authErrorCommand);
            return false;
        }
        else if (networkServer.isNicknameBusy(username)) {
            LOGGER.error("Данный пользователь уже авторизован!");
            Command authErrorCommand = Command.authErrorCommand("Данный пользователь уже авторизован!");
            sendMessage(authErrorCommand);
            return false;
        }
        else {
            nickname = username;
            String message = nickname + " зашел в чат!";
            LOGGER.info(message);
            networkServer.broadcastMessage(Command.messageCommand(null, message), this);
            commandData.setUsername(nickname);
            sendMessage(command);
            networkServer.subscribe(this);
            return true;
        }
    }

    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }

    public String getNickname() {
        return nickname;
    }

}
