package qa.pj.bhxh.model;

import jakarta.persistence.*;

@Entity
public class BaseSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    public Long amount;
}
