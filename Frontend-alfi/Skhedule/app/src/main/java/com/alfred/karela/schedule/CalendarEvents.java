package com.alfred.karela.schedule;

import java.io.Serializable;

/**
 * Created by Alfred on 16-03-2017.
 */

public class CalendarEvents implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String emailId;

    public CalendarEvents() {

    }
    public CalendarEvents(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
