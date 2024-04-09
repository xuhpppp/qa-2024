package qa.pj.bhxh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qa.pj.bhxh.model.BaseSalary;
import qa.pj.bhxh.model.MedicalInsurance;

public interface BaseSalaryRepository  extends JpaRepository<BaseSalary, Long> {
}
