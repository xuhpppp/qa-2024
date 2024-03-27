package qa.pj.bhxh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
public class MedicalInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    @NotBlank
    // bao hiem y te hien tai co do dai la 15 ky tu
    private String insuranceCode;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotBlank
    // TODO: add enum Regions later
    private String region;

    @Column(nullable = false)
    @NotBlank
    private String registrationPlace;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validFrom;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validTo;
}
