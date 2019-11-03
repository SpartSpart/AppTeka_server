package ru.spart.appteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.spart.appteka.controller.model.Appointment;
import ru.spart.appteka.service.serviceException.AppointmentNotFound;
import ru.spart.appteka.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;


    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/appointment")
    public ResponseEntity<Long> addAppontment(@RequestBody Appointment appointment) throws AppointmentNotFound {
        return ResponseEntity
                .ok()
                .body(appointmentService.add(appointment));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/appointment/{id}")
    public ResponseEntity<Void> updateAppointment(@PathVariable("id") long id, @RequestBody Appointment appointment) {
        try {
            appointmentService.update(id, appointment);
        } catch (AppointmentNotFound appointmentNotFound) {
            throw new ApiNotFound();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/appointments")
    public ResponseEntity<Void> updateAllAppointments(@RequestBody List<Appointment> appointments) throws AppointmentNotFound {
        appointmentService.updateAll(appointments);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/appointment/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("id") long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(appointmentService.getApppointment(id));
        } catch (AppointmentNotFound appointmentNotFound) {
            throw new ApiNotFound();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/appointment/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") long id) {
        try {
            appointmentService.deleteAppointment(id);
        } catch (AppointmentNotFound appointmentNotFound) {
            throw new ApiNotFound();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() throws AppointmentNotFound {
        return ResponseEntity
                .ok()
                .body(appointmentService.getAllAppointments());

    }

}
