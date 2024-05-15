package qa.pj.bhxh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String insuranceCode;

    @Column(nullable = false)
    @NotBlank
    private String fullName;

    @Column(nullable = false)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotBlank
    private String registrationPlace;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date creationDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date nearestValidDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date validFrom;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

    public String getFullName() {
        return fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getRegistrationPlace() {
        return registrationPlace;
    }

    public Date getNearestValidDate() {
        return nearestValidDate;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }
}
