package qa.pj.bhxh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
public class MedicalInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    @NotBlank
    // bao hiem y te hien tai co do dai la 15 ky tu
    private String insuranceCode;

    @Column(nullable = false)
    @NotBlank
    private String fullName;

    @Column(nullable = false)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotBlank
    private String registrationPlace;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    // for 5 years continuous mark
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nearestValidDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validFrom;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validTo;

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationPlace() {
        return registrationPlace;
    }

    public void setRegistrationPlace(String registrationPlace) {
        this.registrationPlace = registrationPlace;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getNearestValidDate() {
        return nearestValidDate;
    }

    public void setNearestValidDate(Date nearestValidDate) {
        this.nearestValidDate = nearestValidDate;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
