package ru.spart.appteka.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spart.appteka.controller.model.Type;
import ru.spart.appteka.repository.TypeDataRepository;
import ru.spart.appteka.repository.model.AppointmentData;
import ru.spart.appteka.repository.model.TypeData;
import ru.spart.appteka.service.serviceException.TypeNotFoundCustom;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    private final TypeDataRepository typeDataRepository;

    public TypeService(TypeDataRepository typeDataRepository) {
        this.typeDataRepository = typeDataRepository;
    }


    @Transactional
    public Long add(Type type) throws TypeNotFoundCustom {

        TypeData typeData = new TypeData();
        typeData.setType(type.getType());
        typeDataRepository.saveAndFlush(typeData);

        return typeData.getId();
    }

    @Transactional
    public void update(long id, Type type) throws TypeNotFoundCustom {

        TypeData typeData =typeDataRepository.findById(id)
                .orElseThrow(TypeNotFoundCustom::new);

        typeData.setType(type.getType());

        typeDataRepository.saveAndFlush(typeData);
    }

    @Transactional
    public void updateAll(List<Type> types) throws TypeNotFoundCustom {
        ArrayList<Type> typeArrayList = new ArrayList<>(types);
        for (Type type : typeArrayList) {

            TypeData typeData =typeDataRepository.findByType(type.getType())
                    .orElseThrow(TypeNotFoundCustom::new);

            typeData.setType(type.getType());

            typeDataRepository.saveAndFlush(typeData);
        }

    }

    @Transactional(readOnly = true)
    public Type getType(long id) throws TypeNotFoundCustom {

        TypeData typeData =typeDataRepository.findById(id)
                .orElseThrow(TypeNotFoundCustom::new);
        Type type = new Type();
        type.setId(typeData.getId());
        type.setType(typeData.getType());

        return type;
    }

    @Transactional
    public void deleteType(long id) throws TypeNotFoundCustom {
        TypeData typeData = typeDataRepository.findById(id)
                .orElseThrow(TypeNotFoundCustom::new);
        typeDataRepository.deleteById(typeData.getId());
    }

    @Transactional(readOnly = true)
    public List<Type> getAllTypes() throws TypeNotFoundCustom {
        List<Type> allTypes = new ArrayList<>();
        List<TypeData> allTypeData = typeDataRepository.findAll();

        for (TypeData type : allTypeData) {

            Type newType = new Type();
            newType.setId(type.getId());
            newType.setType(type.getType());

            allTypes.add(newType);
        }

        return allTypes;
    }


}
