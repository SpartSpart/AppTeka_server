package ru.spart.passwordkeeper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spart.passwordkeeper.repository.model.Secret_Data;

@Repository
public interface Secret_Data_Repository extends JpaRepository<Secret_Data,Long> {

}