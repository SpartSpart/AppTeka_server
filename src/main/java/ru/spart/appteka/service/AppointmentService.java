package ru.spart.appteka.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spart.appteka.controller.model.Appointment;
import ru.spart.appteka.repository.AppointmentDataRepository;
import ru.spart.appteka.repository.model.AppointmentData;
import ru.spart.appteka.service.serviceException.AppointmentNotFound;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentDataRepository appointmentDataRepository;

    public AppointmentService(AppointmentDataRepository appointmentDataRepository) {
        this.appointmentDataRepository = appointmentDataRepository;
    }


    @Transactional
    public Long add(Appointment appointment) throws AppointmentNotFound {

        AppointmentData appointmentData = new AppointmentData();
        appointmentData.setAppointment(appointment.getAppointment());
        appointmentDataRepository.saveAndFlush(appointmentData);

        return appointmentData.getId();
    }

    @Transactional
    public void update(long id, Appointment appointment) throws AppointmentNotFound {

        AppointmentData appointmentData =appointmentDataRepository.findById(id)
                .orElseThrow(AppointmentNotFound::new);

        appointmentData.setAppointment(appointment.getAppointment());

        appointmentDataRepository.saveAndFlush(appointmentData);
    }

    @Transactional
    public void updateAll(List<Appointment> appointments) throws AppointmentNotFound {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>(appointments);
        for (Appointment appointment : appointmentArrayList) {

            AppointmentData appointmentData =appointmentDataRepository.findByAppointment(appointment.getAppointment())
                    .orElseThrow(AppointmentNotFound::new);

            appointmentData.setAppointment(appointment.getAppointment());

            appointmentDataRepository.saveAndFlush(appointmentData);
        }

    }

    @Transactional(readOnly = true)
    public Appointment getApppointment(long id) throws AppointmentNotFound {

        AppointmentData appointmentData =appointmentDataRepository.findById(id)
                .orElseThrow(AppointmentNotFound::new);
        Appointment appointment = new Appointment();
        appointment.setId(appointmentData.getId());
        appointment.setAppointment(appointmentData.getAppointment());

        return appointment;
    }

    @Transactional
    public void deleteAppointment(long id) throws AppointmentNotFound {
       AppointmentData appointmentData = appointmentDataRepository.findById(id)
                .orElseThrow(AppointmentNotFound::new);
        appointmentDataRepository.deleteById(appointmentData.getId());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAllAppointments() throws AppointmentNotFound {
        List<Appointment> allAppointments = new ArrayList<>();
        List<AppointmentData> allAppointmentData = appointmentDataRepository.findAll();

        for (AppointmentData appointment : allAppointmentData) {

            Appointment newAppointment = new Appointment();
            newAppointment.setId(appointment.getId());
            newAppointment.setAppointment(appointment.getAppointment());

            allAppointments.add(newAppointment);
        }

        return allAppointments;
    }


}
