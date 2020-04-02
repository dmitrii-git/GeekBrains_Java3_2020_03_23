package ru.geekbrains.java2.client.view;

import ru.geekbrains.java2.client.controller.ClientController;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import ru.geekbrains.java2.client.model.FileService;


public class ClientChat extends JFrame {

    private JPanel mainPanel;
    private JList<String> usersList;
    private JTextField messageTextField;
    private JButton sendButton;
    private JTextArea chatText;
    private FileService file = new FileService();
    private int readFile = 0;


    private ClientController controller;




    public ClientChat(ClientController controller) {
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        addListeners();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.shutdown();
            }
        });

    }


    private void addListeners() {
        sendButton.addActionListener(e -> ClientChat.this.sendMessage());
        messageTextField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        appendOwnMessage(message);

        if (usersList.getSelectedIndex() < 1) {
            controller.sendMessageToAllUsers(message);
        }
        else {
            String username = usersList.getSelectedValue();
            controller.sendPrivateMessage(username, message);
        }

        messageTextField.setText(null);
    }

    public void appendMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String username = ClientChat.this.getName();
                if (readFile == 0) {
                    try {
                        chatText.append(file.readFromFile(username));
                        readFile = 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                chatText.append(message);
                chatText.append(System.lineSeparator());
                try {
                    file.writeToFile(username, message);
                    file.writeToFile(username, System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void appendOwnMessage(String message) {
        appendMessage("Я: " + message);
    }


    public void showError(String message) {
        JOptionPane.showMessageDialog(this, "Failed to send message!");
    }

    public void updateUsers(List<String> users) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addAll(users);
            usersList.setModel(model);
        });
    }

}
