package ru.spart.appteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.spart.appteka.controller.model.Drugs;
import ru.spart.appteka.service.serviceException.DrugsNotFound;
import ru.spart.appteka.service.DrugsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppTekaController {

    private final DrugsService drugsService;


    @Autowired
    public AppTekaController(DrugsService drugsService) {
        this.drugsService = drugsService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/drug")
    public ResponseEntity<Long> addDrug(@RequestBody Drugs drug) throws DrugsNotFound {
        return ResponseEntity
                .ok()
                .body(drugsService.add(drug));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/drug/{id}")
    public ResponseEntity<Void> updateDrugs(@PathVariable("id") long id, @RequestBody Drugs drug) {
        try {
            drugsService.update(id, drug);
        } catch (DrugsNotFound drugsNotFound) {
            throw new ApiNotFound();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/drugs")
    public ResponseEntity<Void> updateAllDrugs(@RequestBody List<Drugs> drugs) throws DrugsNotFound {
        drugsService.updateAll(drugs);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/drug/{id}")
    public ResponseEntity<Drugs> getDrug(@PathVariable("id") long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(drugsService.getDrug(id));
        } catch (DrugsNotFound drugsNotFound) {
            throw new ApiNotFound();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/drug/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable("id") long id) {
        try {
            drugsService.deleteDrug(id);
        } catch (DrugsNotFound drugsNotFound) {
            throw new ApiNotFound();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/drugs")
    public ResponseEntity<List<Drugs>> getAllDrugs() throws DrugsNotFound {
        return ResponseEntity
                .ok()
                .body(drugsService.getAllDrugs());
        //
    }

}
