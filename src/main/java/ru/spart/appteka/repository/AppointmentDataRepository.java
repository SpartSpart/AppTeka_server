package ru.spart.appteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spart.appteka.repository.model.AppointmentData;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentDataRepository extends JpaRepository<AppointmentData,Long> {

        Optional<AppointmentData> findById(Long id);
        Optional<AppointmentData> findByAppointment(String type);

        List<AppointmentData> findAll();
    }
