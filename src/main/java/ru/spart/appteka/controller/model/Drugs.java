package ru.spart.appteka.controller.model;

import java.util.Date;

public class Drugs {
    private long id;
    private String name;
    private String type;
    private String count;
    private String appointment;
    private Date date;
    private long user_id;

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getCount() {return count;}

    public void setCount(String count) {this.count = count;}

    public String getAppointment() {return appointment;}

    public void setAppointment(String appointment) {this.appointment = appointment;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public long getUser_id (long user_id) {return user_id;}

    public void setUser_id(long user_id) {this.user_id = user_id;}

}