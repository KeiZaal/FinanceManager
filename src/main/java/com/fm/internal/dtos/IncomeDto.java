package com.fm.internal.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.List;

public class IncomeDto {
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$")
    private String amount;
    @NotEmpty
    private String date;
    private String note;
    private long accountId;
    private long incomeId;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> hashTags;

    public IncomeDto() {
    }

    public IncomeDto(String amount, String date, String note, List<String> hashTags, long accountId, long incomeId) {
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.accountId = accountId;
        this.incomeId = incomeId;
        this.hashTags = hashTags;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(long incomeId) {
        this.incomeId = incomeId;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }
}
