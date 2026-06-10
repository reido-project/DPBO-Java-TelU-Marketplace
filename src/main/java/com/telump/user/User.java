package com.telump.user;

import com.telump.common.Authenticatable;
import java.util.List;

public abstract class User implements Authenticatable {
    private String username;           
    private String password;           
    private String profilname;         
    private String kontak;             
    private String bio;               
    private UserStatus status;       
    private UserRole role;            


    public User(String username, String password, String profilname, String kontak, String bio, UserRole role) {
        this.username = username;
        this.password = password;
        this.profilname = profilname;
        this.kontak = kontak;
        this.bio = bio;
        this.role = role;
        this.status = UserStatus.ACTIVE;  
    }

    public abstract String displayRole();
    public abstract List<String> getPermissions();

    @Override
    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            this.status = UserStatus.ACTIVE;
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        System.out.println(this.username + " telah logout");
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getProfilname() {
        return profilname;
    }
    public void setProfilname(String profilname) {
        this.profilname = profilname;
    }
    public String getKontak() {
        return kontak;
    }
    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", profilname='" + profilname + '\'' +
                ", kontak='" + kontak + '\'' +
                ", bio='" + bio + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
