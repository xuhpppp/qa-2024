package qa.pj.bhxh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qa.pj.bhxh.model.MedicalInsurance;
import qa.pj.bhxh.service.MedicalInsuranceService;

import java.util.List;

@RestController
@RequestMapping("/medical-insurance")
public class MedicalInsuranceController {
    @Autowired
    private MedicalInsuranceService medicalInsuranceService;

    @GetMapping("/list-view")
    public ResponseEntity<Void> listView(
            @RequestParam String insuranceCode,
            @RequestParam String name,
            @RequestParam String city,
            @RequestParam String registrationPlace,
            @RequestParam String isValid,
            @RequestParam String fiveYearsContinuous
    ) {
        List<MedicalInsurance> medicalInsuranceList = medicalInsuranceService.findInsurance();
        System.out.println(medicalInsuranceList.size());

        return ResponseEntity.ok().build();
    }
}
