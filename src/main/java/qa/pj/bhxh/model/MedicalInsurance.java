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

    // TODO: must validate data before creating or updating
}
