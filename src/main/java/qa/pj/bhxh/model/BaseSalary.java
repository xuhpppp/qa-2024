package qa.pj.bhxh.model;

import jakarta.persistence.*;

@Entity
public class BaseSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    public Long amount;

    public BaseSalary(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public BaseSalary() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.amount = amount;
    }
}
