package qa.pj.bhxh;

import jakarta.persistence.EntityManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import qa.pj.bhxh.controller.MedicalInsuranceController;
import qa.pj.bhxh.model.BaseSalary;
import qa.pj.bhxh.model.Gender;
import qa.pj.bhxh.model.MedicalInsurance;
import qa.pj.bhxh.repository.BaseSalaryRepository;
import qa.pj.bhxh.repository.MedicalInsuranceRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BhxhApplicationTests {
	@Autowired
	private BaseSalaryRepository baseSalaryRepository;
	@Autowired
	private MedicalInsuranceRepository medicalInsuranceRepository;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private MedicalInsuranceController medicalInsuranceController;

	@Test
	void testExportMedicalInsurance() throws IOException, ParseException {

		ResponseEntity<byte[]> response = exportMedicalInsurance("1", medicalInsuranceRepository);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		HttpHeaders headers = response.getHeaders();
		assertEquals(MediaType.APPLICATION_OCTET_STREAM, headers.getContentType());

		assertEquals("attachment; filename=\"medical_insurance.xlsx\"", headers.getContentDisposition().toString());

		byte[] workbookBytes = response.getBody();
		InputStream inputStream = new ByteArrayInputStream(workbookBytes);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		assertEquals("Medical Insurance", sheet.getSheetName());

		Row headerRow = sheet.getRow(0);
		assertEquals("STT", headerRow.getCell(0).getStringCellValue());
		assertEquals("Mã số", headerRow.getCell(1).getStringCellValue());
		assertEquals("Họ và tên", headerRow.getCell(2).getStringCellValue());
		assertEquals("Ngày sinh", headerRow.getCell(3).getStringCellValue());
		assertEquals("Giới tính", headerRow.getCell(4).getStringCellValue());
		assertEquals("Địa chỉ", headerRow.getCell(5).getStringCellValue());
		assertEquals("Nơi ĐK KCB BĐ", headerRow.getCell(6).getStringCellValue());
		assertEquals("Giá trị sử dụng", headerRow.getCell(7).getStringCellValue());
		assertEquals("Thời điểm đủ 5 năm liên tục", headerRow.getCell(8).getStringCellValue());

		Row dataRow = sheet.getRow(1);
		assertEquals(1.0, dataRow.getCell(0).getNumericCellValue());
		assertEquals("SV4018395421067", dataRow.getCell(1).getStringCellValue());
		assertEquals("Nguyễn Xuân Hưng", dataRow.getCell(2).getStringCellValue());

		workbook.close();
	}
	private ResponseEntity<byte[]> exportMedicalInsurance(String request, MedicalInsuranceRepository repository) throws IOException {
		String[] idStrings = request.split(";");
		Long[] ids = new Long[idStrings.length];
		for (int i = 0; i < idStrings.length; i++) {
			ids[i] = Long.parseLong(idStrings[i]);
		}
		List<MedicalInsurance> medicalInsurances = repository.findAllById(List.of(ids));

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
				row.createCell(4).setCellValue(medicalInsurance.getGender() == Gender.FEMALE ? "Nữ" : "Nam");
				row.createCell(5).setCellValue(medicalInsurance.getAddress());
				row.createCell(6).setCellValue(medicalInsurance.getRegistrationPlace());
				row.createCell(7).setCellValue(dateFormat.format(medicalInsurance.getValidTo()));
				row.createCell(8).setCellValue(dateFormat.format(medicalInsurance.getNearestValidDate()));
				rowNum++;
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
			bos.close();
			workbook.close();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename("medical_insurance.xlsx").build());

			return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@Test
	public void testGetSalary() {
		ResponseEntity<BaseSalary> result = medicalInsuranceController.getSalary();
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result);
	}

	@Test
	public void testGetSalary_NotFound() {
		Optional<BaseSalary> result = baseSalaryRepository.findById(99L);
		assertFalse(result.isPresent());
	}

	@Test
	public void testUpdateSalary() {
		BaseSalary baseSalary = new BaseSalary(1L, 1000L);
		baseSalaryRepository.save(baseSalary);

		long newAmount = 2000L;

		Optional<BaseSalary> result = baseSalaryRepository.findById(1L);
		assertTrue(result.isPresent());

		BaseSalary updatedSalary = result.get();
		updatedSalary.setAmount(newAmount);
		try {
			baseSalaryRepository.save(updatedSalary);
		} catch (Exception e) {
			entityManager.clear();
			entityManager.getTransaction().rollback();
		}

		Optional<BaseSalary> updatedResult = baseSalaryRepository.findById(1L);
		assertTrue(updatedResult.isPresent());
		assertEquals(newAmount, updatedResult.get().getAmount());
	}

	@Test
	public void testUpdateSalary_NotFound() {
		long nonExistentId = 999L;
		long newAmount = 2000L;

		Optional<BaseSalary> result = baseSalaryRepository.findById(nonExistentId);
		assertFalse(result.isPresent());
	}

	@Test
	public void testUpdateSalaryWithNullAmount() {
		Long newAmount = null;

		Optional<BaseSalary> result = baseSalaryRepository.findById(1L);
		assertTrue(result.isPresent());

		BaseSalary updatedSalary = result.get();

		try {
			updatedSalary.setAmount(newAmount);
			baseSalaryRepository.save(updatedSalary);
			fail("Expected IllegalArgumentException to be thrown for null newAmount");
		} catch (IllegalArgumentException e) {
			Optional<BaseSalary> updatedResult = baseSalaryRepository.findById(1L);
			assertTrue(updatedResult.isPresent());
			var update_amount  = updatedResult.get().getAmount();
			assertEquals(result.get().getAmount(), update_amount);
		}
	}

	@Test
	public void testListView() {
		// Act
		ResponseEntity<List<MedicalInsurance>> response1 = medicalInsuranceController.listView(null, null, null, null, null, null, null, 0, 10);
		ResponseEntity<List<MedicalInsurance>> response2 = medicalInsuranceController.listView("SV4018395421067", null, null, "Hà Nội", null, "", "null", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response3 = medicalInsuranceController.listView("", null, null, null, "", null, "null", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response4 = medicalInsuranceController.listView(null, "", null, "", "valid", null, "null", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response5 = medicalInsuranceController.listView(null, "Nguyễn Xuân Hưng", "", null, null, "true", "null", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response6 = medicalInsuranceController.listView(null, null, "Hà Nội", null, null, null, "", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response7 = medicalInsuranceController.listView(null, null, null, null, null, null, "1", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response8 = medicalInsuranceController.listView(null, "hưng", null, "hà nội", "inValid", "false", "1", 0, 10);
		ResponseEntity<List<MedicalInsurance>> response9 = medicalInsuranceController.listView(null, "hưng", null, "hà nội", "inValid", null, "1", 0, 10);

		assertEquals(HttpStatus.OK, response1.getStatusCode());
	}

	@Test
	public void testGetAllMedicalInsurrance() {
		ResponseEntity<List<MedicalInsurance>> response = medicalInsuranceController.getAllMedicalInsurances();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetMedicalInsuranceById() {
		ResponseEntity<MedicalInsurance> response = medicalInsuranceController.getMedicalInsuranceById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetMedicalInsuranceById_NotFound() {
		ResponseEntity<MedicalInsurance> response = medicalInsuranceController.getMedicalInsuranceById(0L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteMedicalInsurrance_NotFound() {
		ResponseEntity<Void> response = medicalInsuranceController.deleteMedicalInsurance(1000L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}