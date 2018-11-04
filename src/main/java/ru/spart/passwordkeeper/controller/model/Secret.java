package ru.spart.passwordkeeper.controller.model;

public class Secret {

    private long id;
    private String description;
    private String login;
    private String password;

    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}
}
