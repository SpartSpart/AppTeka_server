package ru.spart.appteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.spart.appteka.controller.model.Appointment;
import ru.spart.appteka.controller.model.Type;
import ru.spart.appteka.service.TypeService;
import ru.spart.appteka.service.serviceException.AppointmentNotFound;
import ru.spart.appteka.service.AppointmentService;
import ru.spart.appteka.service.serviceException.TypeNotFoundCustom;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TypeController {

    private final TypeService typeService;


    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/type")
    public ResponseEntity<Long> addType(@RequestBody Type type) throws TypeNotFoundCustom {
        return ResponseEntity
                .ok()
                .body(typeService.add(type));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/type/{id}")
    public ResponseEntity<Void> updateType(@PathVariable("id") long id, @RequestBody Type type) {
        try {
            typeService.update(id, type);
        } catch (TypeNotFoundCustom typeNotFoundCustom) {
            throw new ApiNotFound();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/types")
    public ResponseEntity<Void> updateAllTypes(@RequestBody List<Type> types) throws TypeNotFoundCustom {
        typeService.updateAll(types);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/type/{id}")
    public ResponseEntity<Type> getType(@PathVariable("id") long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(typeService.getType(id));
        } catch (TypeNotFoundCustom typeNotFoundCustom) {
            throw new ApiNotFound();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/type/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable("id") long id) {
        try {
            typeService.deleteType(id);
        } catch (TypeNotFoundCustom typeNotFoundCustom) {
            throw new ApiNotFound();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/types")
    public ResponseEntity<List<Type>> getAllTypes() throws TypeNotFoundCustom {
        return ResponseEntity
                .ok()
                .body(typeService.getAllTypes());

    }

}
