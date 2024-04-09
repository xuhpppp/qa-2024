package qa.pj.bhxh.controller;

import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qa.pj.bhxh.model.BaseSalary;
import qa.pj.bhxh.model.Gender;
import qa.pj.bhxh.model.MedicalInsurance;
import qa.pj.bhxh.repository.BaseSalaryRepository;
import qa.pj.bhxh.repository.MedicalInsuranceRepository;
import qa.pj.bhxh.service.MedicalInsuranceService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medical-insurance")
public class MedicalInsuranceController {
    private final MedicalInsuranceService medicalInsuranceService;
    private final MedicalInsuranceRepository medicalInsuranceRepository;

    private final BaseSalaryRepository baseSalaryRepository;

    public MedicalInsuranceController(MedicalInsuranceService medicalInsuranceService, MedicalInsuranceRepository medicalInsuranceRepository, BaseSalaryRepository baseSalaryRepository) {
        this.medicalInsuranceService = medicalInsuranceService;
        this.medicalInsuranceRepository = medicalInsuranceRepository;
        this.baseSalaryRepository = baseSalaryRepository;
    }

    @GetMapping("/list-view")
    public ResponseEntity<List<MedicalInsurance>> listView(
            @RequestParam(required = false) String insuranceCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String registrationPlace,
            @RequestParam(required = false) String isValid,
            @RequestParam(required = false) String fiveYearsContinuous,
            @RequestParam(required = false) String earningGrade,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int page_size
    ) {
        List<MedicalInsurance> allMedicalInsurances = medicalInsuranceRepository.findAll();

        if (insuranceCode != null && !insuranceCode.isEmpty()) {
            allMedicalInsurances.removeIf(medicalInsurance ->
                    !medicalInsurance.getInsuranceCode().toLowerCase().contains(insuranceCode.toLowerCase()));
        }

        if (earningGrade != null && !earningGrade.isEmpty()) {
            char gradeChar = earningGrade.charAt(0);
            allMedicalInsurances.removeIf(medicalInsurance ->
                    medicalInsurance.getInsuranceCode().charAt(2) != gradeChar);
        }

        if (name != null && !name.isEmpty()) {
            allMedicalInsurances.removeIf(medicalInsurance ->
                    !medicalInsurance.getFullName().toLowerCase().contains(name.toLowerCase()));
        }

        if (city != null && !city.isEmpty()) {
            allMedicalInsurances.removeIf(medicalInsurance ->
                    !medicalInsurance.getAddress().toLowerCase().contains(city.toLowerCase()));
        }

        if (registrationPlace != null && !registrationPlace.isEmpty()) {
            allMedicalInsurances.removeIf(medicalInsurance ->
                    !medicalInsurance.getRegistrationPlace().toLowerCase().contains(registrationPlace.toLowerCase()));
        }

        if (isValid != null && !isValid.isEmpty()) {
            LocalDate today = LocalDate.now();

            if ("valid".equals(isValid)) {
                allMedicalInsurances.removeIf(medicalInsurance ->
                        !(new Date(medicalInsurance.getValidFrom().getTime()).toLocalDate().isBefore(today) &&
                                new Date(medicalInsurance.getValidTo().getTime()).toLocalDate().isAfter(today)));
            } else {
                allMedicalInsurances.removeIf(medicalInsurance ->
                        !(new Date(medicalInsurance.getValidFrom().getTime()).toLocalDate().isAfter(today) ||
                                new Date(medicalInsurance.getValidTo().getTime()).toLocalDate().isBefore(today)));
            }

        }

        if (fiveYearsContinuous != null && !fiveYearsContinuous.isEmpty()) {
            LocalDate today = LocalDate.now();
            if ("true".equals(fiveYearsContinuous)) {
                allMedicalInsurances.removeIf(medicalInsurance ->
                        !(new Date(medicalInsurance.getNearestValidDate().getTime()).toLocalDate().isAfter(today)));
            } else {
                allMedicalInsurances.removeIf(medicalInsurance ->
                        !(new Date(medicalInsurance.getNearestValidDate().getTime()).toLocalDate().isBefore(today)));
            }
        }


        int startIndex = page * page_size;
        int endIndex = Math.min(startIndex + page_size, allMedicalInsurances.size());
        List<MedicalInsurance> paginatedMedicalInsurances = allMedicalInsurances.subList(startIndex, endIndex);

        return ResponseEntity.ok(paginatedMedicalInsurances);

    }

    @GetMapping("/get-salary")
    public ResponseEntity<BaseSalary> getSalary() {
        int id = 1;
        Optional<BaseSalary> salary = baseSalaryRepository.findById((long) id);
        return salary.map(baseSalary -> new ResponseEntity<>(baseSalary, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/update-salary")
    public ResponseEntity<BaseSalary> updateSalary( @RequestParam Long amount) {
        int id = 1;
        Optional<BaseSalary> salaryOptional = baseSalaryRepository.findById((long) id);

        if (salaryOptional.isPresent()) {
            BaseSalary baseSalary = salaryOptional.get();

            baseSalary.amount = amount;

            BaseSalary updatedSalary = baseSalaryRepository.save(baseSalary);

            return new ResponseEntity<>(updatedSalary, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/export_xls")
    public ResponseEntity<byte[]> exportMedicalInsurance(@RequestParam String request) {
        String[] idStrings = request.split(";");
        Long[] ids = new Long[idStrings.length];
        for (int i = 0; i < idStrings.length; i++) {
            ids[i] = Long.parseLong(idStrings[i]);
        }
        List<MedicalInsurance> medicalInsurances = medicalInsuranceRepository.findAllById(List.of(ids));

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Medical Insurance");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("STT");
            headerRow.createCell(1).setCellValue("Mã số");
            headerRow.createCell(2).setCellValue("Họ và tên");
            headerRow.createCell(3).setCellValue("Ngày sinh");
            headerRow.createCell(4).setCellValue("Giới tính");
            headerRow.createCell(5).setCellValue("Địa chỉ");
            headerRow.createCell(6).setCellValue("Nơi ĐK KCB BĐ");
            headerRow.createCell(7).setCellValue("Giá trị sử dụng");
            headerRow.createCell(8).setCellValue("Thời điểm đủ 5 năm liên tục");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int rowNum = 1;
            for (MedicalInsurance medicalInsurance : medicalInsurances) {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum);
                row.createCell(1).setCellValue(medicalInsurance.getInsuranceCode());
                row.createCell(2).setCellValue(medicalInsurance.getFullName());
                row.createCell(3).setCellValue(dateFormat.format(medicalInsurance.getDob()));
                row.createCell(4).setCellValue(medicalInsurance.getGender() == Gender.FEMALE ? "Nữ": "Nam");
                row.createCell(5).setCellValue(medicalInsurance.getAddress());
                row.createCell(6).setCellValue(medicalInsurance.getRegistrationPlace());
                row.createCell(7).setCellValue(dateFormat.format(medicalInsurance.getValidTo()));
                row.createCell(8).setCellValue("null");
                rowNum++;
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] bytes = bos.toByteArray();
            bos.close();
            workbook.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "medical_insurance.xlsx");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
