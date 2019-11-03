package ru.spart.appteka.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spart.appteka.controller.model.Drugs;
import ru.spart.appteka.repository.AppointmentDataRepository;
import ru.spart.appteka.repository.DrugsDataRepository;
import ru.spart.appteka.repository.TypeDataRepository;
import ru.spart.appteka.repository.UserDataRepository;
import ru.spart.appteka.repository.model.AppointmentData;
import ru.spart.appteka.repository.model.DrugsData;
import ru.spart.appteka.repository.model.TypeData;
import ru.spart.appteka.repository.model.UserData;
import ru.spart.appteka.service.serviceException.DrugsNotFound;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrugsService {

    private final DrugsDataRepository drugsDataRepository;
    private final UserDataRepository userDataRepository;
    private final TypeDataRepository typeDataRepository;
    private final AppointmentDataRepository appointmentDataRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public DrugsService(DrugsDataRepository drugsDataRepository, UserDataRepository userDataRepository, UserDetailsServiceImpl userDetailsServiceImpl,
                        TypeDataRepository typeDataRepository, AppointmentDataRepository appointmentDataRepository) {
        this.drugsDataRepository = drugsDataRepository;
        this.userDataRepository = userDataRepository;
        this.typeDataRepository = typeDataRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.appointmentDataRepository = appointmentDataRepository;
    }

    @Transactional
    public Long add(Drugs drugs) throws DrugsNotFound {

        DrugsData drugsData = new DrugsData();
        TypeData typeData = typeDataRepository.findByType(drugs.getType())
                .orElseThrow(DrugsNotFound::new);
        AppointmentData appointmentData =appointmentDataRepository.findByAppointment(drugs.getAppointment())
                .orElseThrow(DrugsNotFound::new);

        drugsData.setName(drugs.getName());
        drugsData.setType_id(typeData.getId());
        drugsData.setCount(drugs.getCount());
        drugsData.setAppointent_id(appointmentData.getId());
        drugsData.setDate(drugs.getDate());
        drugsData.setUserData(getUserData());

        drugsDataRepository.saveAndFlush(drugsData);
        return drugsData.getId();
    }

    @Transactional
    public void update(long id, Drugs drugs) throws DrugsNotFound {

        DrugsData drugsData = drugsDataRepository.findByIdAndUserData(id, getUserData())
                .orElseThrow(DrugsNotFound::new);
        TypeData typeData = typeDataRepository.findByType(drugs.getType())
                .orElseThrow(DrugsNotFound::new);
        AppointmentData appointmentData =appointmentDataRepository.findByAppointment(drugs.getAppointment())
                .orElseThrow(DrugsNotFound::new);

        drugsData.setName(drugs.getName());
        drugsData.setType_id(typeData.getId());
        drugsData.setCount(drugs.getCount());
        drugsData.setAppointent_id(appointmentData.getId());
        drugsData.setDate(drugs.getDate());

        drugsDataRepository.saveAndFlush(drugsData);
    }

    @Transactional
    public void updateAll(List<Drugs> drugs) throws DrugsNotFound {
        ArrayList<Drugs> drugsArrayList = new ArrayList<>(drugs);
        for (Drugs drug : drugsArrayList) {

            DrugsData drugsData = (drugsDataRepository.findById(drug.getId())
                        .orElseThrow(DrugsNotFound::new));
            TypeData typeData = typeDataRepository.findByType(drug.getType())
                    .orElseThrow(DrugsNotFound::new);
            AppointmentData appointmentData =appointmentDataRepository.findByAppointment(drug.getAppointment())
                    .orElseThrow(DrugsNotFound::new);

            drugsData.setName(drug.getName());
            drugsData.setType_id(typeData.getId());
            drugsData.setCount(drug.getCount());
            drugsData.setAppointent_id(appointmentData.getId());
            drugsData.setDate(drug.getDate());

            drugsDataRepository.saveAndFlush(drugsData);
            }

        }

    @Transactional(readOnly = true)
    public Drugs getDrug(long id) throws DrugsNotFound {
        DrugsData drugsData = drugsDataRepository.findByIdAndUserData(id, getUserData())
                .orElseThrow(DrugsNotFound::new);
        TypeData typeData = typeDataRepository.findById(drugsData.getType_id())
                .orElseThrow(DrugsNotFound::new);
        AppointmentData appointmentData =appointmentDataRepository.findById(drugsData.getAppointent_id())
                .orElseThrow(DrugsNotFound::new);
        Drugs drugs = new Drugs();
        drugs.setId(drugsData.getId());
        drugs.setName(drugsData.getName());
        drugs.setType(typeData.getType());
        drugs.setCount(drugsData.getCount());
        drugs.setAppointment(appointmentData.getAppointment());
        drugs.setDate(drugsData.getDate());

        return drugs;
    }

    @Transactional
    public void deleteDrug(long id) throws DrugsNotFound {
        DrugsData drugsData = drugsDataRepository.findByIdAndUserData(id, getUserData())
                .orElseThrow(DrugsNotFound::new);
        drugsDataRepository.deleteById(drugsData.getId());
    }

    @Transactional
    public List<Drugs> getAllDrugs() throws DrugsNotFound {
        List<Drugs> allDrugs = new ArrayList<>();
        List<DrugsData> allDrugsData = drugsDataRepository.findAllByUserData(getUserData());

        for (DrugsData drug : allDrugsData) {
            TypeData typeData = typeDataRepository.findById(drug.getType_id())
                    .orElseThrow(DrugsNotFound::new);
            AppointmentData appointmentData =appointmentDataRepository.findById(drug.getAppointent_id())
                    .orElseThrow(DrugsNotFound::new);
            Drugs newDrug = new Drugs();
            newDrug.setId(drug.getId());
            newDrug.setName(drug.getName());
            newDrug.setType(typeData.getType());
            newDrug.setCount(drug.getCount());
            newDrug.setAppointment(appointmentData.getAppointment());
            newDrug.setDate(drug.getDate());

            allDrugs.add(newDrug);
        }

        return allDrugs;
    }

    private UserData getUserData() {
        UserDetails userDetails = userDetailsServiceImpl.getCurrent();
        return userDataRepository.findByUserLogin(userDetails.getUsername())
                .orElseThrow(RuntimeException::new);
    }
}
