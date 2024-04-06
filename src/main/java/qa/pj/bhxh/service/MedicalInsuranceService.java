package qa.pj.bhxh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qa.pj.bhxh.model.MedicalInsurance;
import qa.pj.bhxh.repository.MedicalInsuranceRepository;

import java.util.List;

@Service
public class MedicalInsuranceService {
    @Autowired
    private MedicalInsuranceRepository medicalInsuranceRepository;

    public List<MedicalInsurance> findInsurance() {
        return medicalInsuranceRepository.findAll();
    }
}
