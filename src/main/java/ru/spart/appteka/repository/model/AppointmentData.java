package ru.spart.appteka.repository.model;

import javax.persistence.*;

    @Entity
    @Table(name = "Appointment")
    public class AppointmentData {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID", nullable = false)
        private long id;

        @Column(name = "APPOINTMENT")
        private String appointment;

        public AppointmentData() {}

        public long getId() {return id;}

        public String getAppointment() {
            return appointment;
        }

        public void setAppointment(String appointment) {
            this.appointment = appointment;
        }
    }