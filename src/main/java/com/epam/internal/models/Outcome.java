package com.epam.internal.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime date;

    @Column
    private String note;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id", foreignKey = @ForeignKey(name = "fk_account_id"))
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false, name = "outcome_type_id", foreignKey = @ForeignKey(name = "fk_outcome_type_id"))
    private OutcomeType outcomeType;

    public Outcome() {
    }

    public Outcome(BigDecimal amount, LocalDateTime date, Account account, OutcomeType outcomeType) {
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.outcomeType = outcomeType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }
}
