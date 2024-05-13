package qa.pj.bhxh.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.pj.bhxh.model.MedicalInsurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalInsuranceRepository extends JpaRepository<MedicalInsurance, Long> {
}
