package com.telump.page.auth;

import javax.swing.*;
import java.net.URL;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.telump.Main;
import com.telump.common.AuthenticationService;
import com.telump.common.State;
import com.telump.user.User;

public class Login extends JFrame {
    private String errorMessage = null;
    private State appState = null;
    private LoginInfo loginInfo = new LoginInfo(null, null, null);
    private User user = null;

    public Login(){
        super("Tel-U Marketplace Login");
    }

    public LoginInfo login() {
        URL imgURL = Main.class.getResource("/com/telump/asset/emblem.png");
        
        if (imgURL != null) {
            Image icon = new ImageIcon(imgURL).getImage();
            setIconImage(icon);
        }

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                loginInfo = new LoginInfo("Login dibatalkan.", State.Start, null);
            }
        });

        // Layer 0
        JPanel window = new JPanel(new GridBagLayout());
        window.setBackground(Color.BLACK);

        // Layer 1
        JPanel formBase = new JPanel(new BorderLayout());
        formBase.setPreferredSize(new Dimension(360, 320));
        formBase.setBackground(Color.GRAY);

        // Components
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(360, 50));
        header.setBackground(Color.RED);
        header.add(title, BorderLayout.CENTER);
        formBase.add(header, BorderLayout.NORTH);

        // Layer 2: Isi formulir
        JPanel form = new JPanel(null);
        form.setOpaque(false);

        JLabel username = new JLabel("Username");
        username.setForeground(Color.WHITE);
        username.setBounds(30, 10, 300, 20);
        
        JTextField usernameInput = new JTextField();
        usernameInput.setBounds(30, 35, 300, 40);

        JLabel password = new JLabel("Password");
        password.setForeground(Color.WHITE);
        password.setBounds(30, 90, 300, 20);
        
        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setBounds(30, 115, 300, 40);

        JButton submit = new JButton("Login");
        submit.setBounds(30, 190, 300, 45); // Shifted Y upward slightly to fit panel properly

        submit.addActionListener(e -> {
            String usernameResult = usernameInput.getText();
            String passwordResult = new String(passwordInput.getPassword());

            user = AuthenticationService.login(usernameResult, passwordResult);

            if (user != null) {
                errorMessage = "Selamat datang, " + user.getUsername() + "!";
                appState = State.Dashboard;
            } else {
                errorMessage = AuthenticationService.getLastError();
                appState = State.Start;
            }

            loginInfo = new LoginInfo(errorMessage, appState, user);
        });

        form.add(username);
        form.add(usernameInput);
        form.add(password);
        form.add(passwordInput);
        form.add(submit);
        
        formBase.add(form, BorderLayout.CENTER);
        window.add(formBase);
        this.add(window);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        while(true){
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
            if(loginInfo.getAppState() != null){
                dispose();
                return loginInfo;
            }
        }
    }
}