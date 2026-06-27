package com.telump.page.auth;

import javax.swing.*;
import java.net.URL;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.telump.Main;
import com.telump.common.AuthenticationService;
import com.telump.common.State;
import com.telump.page.AppInfo;
import com.telump.user.User;

public class RegisterAccount extends JFrame {
    private String errorMessage = null;
    private State appState = null;
    private AppInfo appInfo = new AppInfo(null, null);

    public RegisterAccount(){
        super("Tel-U Marketplace Registration");
    }

    public AppInfo register() {
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
                appInfo = new AppInfo("Registrasi dibatalkan.", State.Start);
            }
        });

        // Layer 0
        JPanel window = new JPanel(new GridBagLayout());
        window.setBackground(Color.BLACK);

        // Layer 1
        JPanel formBase = new JPanel(new BorderLayout());
        formBase.setPreferredSize(new Dimension(360, 500));
        formBase.setBackground(Color.GRAY);

        // Components
        JLabel title = new JLabel("Register Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.white);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(360, 50));
        header.setBackground(Color.red);
        header.add(title, BorderLayout.CENTER);
        formBase.add(header, BorderLayout.NORTH);

        // Layer 2: Isi formulir
        JPanel form = new JPanel(null);
        form.setOpaque(false);

        JLabel username = new JLabel("Username");
        username.setForeground(Color.WHITE);
        username.setBounds(30, 10, 300, 20);
        JTextField usernameInput = new JTextField();
        usernameInput.setBounds(30, 32, 300, 35);

        JLabel password = new JLabel("Password (minimal 6 karakter)");
        password.setForeground(Color.WHITE);
        password.setBounds(30, 77, 300, 20);
        JPasswordField passwordInput = new JPasswordField();
        passwordInput.setBounds(30, 99, 300, 35);

        JLabel profilName = new JLabel("Nama Profil");
        profilName.setForeground(Color.WHITE);
        profilName.setBounds(30, 144, 300, 20);
        JTextField profilNameInput = new JTextField();
        profilNameInput.setBounds(30, 166, 300, 35);

        JLabel kontak = new JLabel("Kontak");
        kontak.setForeground(Color.WHITE);
        kontak.setBounds(30, 211, 300, 20);
        JTextField kontakInput = new JTextField();
        kontakInput.setBounds(30, 233, 300, 35);

        JLabel bio = new JLabel("Bio");
        bio.setForeground(Color.WHITE);
        bio.setBounds(30, 278, 300, 20);
        JTextField bioInput = new JTextField();
        bioInput.setBounds(30, 300, 300, 35);

        JButton submit = new JButton("Register");
        submit.setBounds(30, 370, 300, 45);

        submit.addActionListener(e -> {
            String usernameResult = usernameInput.getText();
            String passwordResult = new String(passwordInput.getPassword());
            String profilNameResult = profilNameInput.getText();
            String kontakResult = kontakInput.getText();
            String bioResult = bioInput.getText();

            User newUser = AuthenticationService.registerUser(
                usernameResult, 
                passwordResult, 
                profilNameResult, 
                kontakResult, 
                bioResult
            );

            if (newUser != null) {
                errorMessage = "Registrasi berhasil! Silakan login.";
                appState = State.Login;
            } else {
                errorMessage = "Registrasi gagal: " + AuthenticationService.getLastError();
                appState = State.Start;
            }

            appInfo = new AppInfo(errorMessage, appState);
        });

        form.add(username);
        form.add(usernameInput);
        form.add(password);
        form.add(passwordInput);
        form.add(profilName);
        form.add(profilNameInput);
        form.add(kontak);
        form.add(kontakInput);
        form.add(bio);
        form.add(bioInput);
        form.add(submit);
        
        formBase.add(form, BorderLayout.CENTER);
        window.add(formBase);
        this.add(window);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        while(true){
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
            if(appInfo.getAppState() != null){
                dispose();
                return appInfo;
            }
        }
    }
}