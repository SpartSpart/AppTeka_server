package ru.spart.appteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spart.appteka.repository.model.TypeData;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface TypeDataRepository extends JpaRepository<TypeData,Long> {

        Optional<TypeData> findById(Long id);
        Optional<TypeData> findByType(String type);

        List<TypeData> findAll();

    }

