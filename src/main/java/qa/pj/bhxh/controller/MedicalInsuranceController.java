package qa.pj.bhxh.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qa.pj.bhxh.model.MedicalInsurance;
import qa.pj.bhxh.repository.MedicalInsuranceRepository;
import qa.pj.bhxh.service.MedicalInsuranceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medical-insurance")
public class MedicalInsuranceController {
    private final MedicalInsuranceService medicalInsuranceService;
    private final MedicalInsuranceRepository medicalInsuranceRepository;

    public MedicalInsuranceController(MedicalInsuranceService medicalInsuranceService, MedicalInsuranceRepository medicalInsuranceRepository) {
        this.medicalInsuranceService = medicalInsuranceService;
        this.medicalInsuranceRepository = medicalInsuranceRepository;
    }

    @GetMapping("/list-view")
    public ResponseEntity<List<MedicalInsurance>> listView(
            @RequestParam(required = false) String insuranceCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String registrationPlace,
            @RequestParam(required = false) String isValid,
            @RequestParam(required = false) String fiveYearsContinuous
    ) {
        List<MedicalInsurance> medicalInsuranceList = medicalInsuranceService.findInsurance();
        return ResponseEntity.ok(medicalInsuranceList);
    }

    @PostMapping
    public ResponseEntity<MedicalInsurance> createMedicalInsurance(@Valid @RequestBody MedicalInsurance medicalInsurance) {
        MedicalInsurance savedInsurance = medicalInsuranceRepository.save(medicalInsurance);
        return new ResponseEntity<>(savedInsurance, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicalInsurance>> getAllMedicalInsurances() {
        List<MedicalInsurance> medicalInsurances = medicalInsuranceRepository.findAll();
        return new ResponseEntity<>(medicalInsurances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalInsurance> getMedicalInsuranceById(@PathVariable("id") Long id) {
        Optional<MedicalInsurance> medicalInsuranceOptional = medicalInsuranceRepository.findById(id);
        if (medicalInsuranceOptional.isPresent()) {
            return new ResponseEntity<>(medicalInsuranceOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalInsurance> updateMedicalInsurance(@PathVariable("id") Long id, @Valid @RequestBody MedicalInsurance updatedInsurance) {
        Optional<MedicalInsurance> medicalInsuranceOptional = medicalInsuranceRepository.findById(id);
        if (medicalInsuranceOptional.isPresent()) {
            updatedInsurance.setId(id);
            MedicalInsurance savedInsurance = medicalInsuranceRepository.save(updatedInsurance);
            return new ResponseEntity<>(savedInsurance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalInsurance(@PathVariable("id") Long id) {
        Optional<MedicalInsurance> medicalInsuranceOptional = medicalInsuranceRepository.findById(id);
        if (medicalInsuranceOptional.isPresent()) {
            medicalInsuranceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
