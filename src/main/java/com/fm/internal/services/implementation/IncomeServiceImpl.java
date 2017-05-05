package com.fm.internal.services.implementation;

import com.fm.internal.daos.IncomeDao;
import com.fm.internal.dtos.IncomeDto;
import com.fm.internal.models.Account;
import com.fm.internal.models.Income;
import com.fm.internal.models.User;
import com.fm.internal.services.AccountService;
import com.fm.internal.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeDao dao;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UtilServiceImpl utilService;

    @Override
    public void addIncome(Income income) {
        dao.add(income);
        income.getAccount().setBalance(getBalanceAfterAddingIncome(income));
        accountService.updateAccount(income.getAccount());
    }

    private BigDecimal getBalanceAfterAddingIncome(Income income) {
        Account account = income.getAccount();
        return utilService.recountAccountBalance(account);
    }

    @Override
    public void updateIncome(Income income) {
        dao.update(income);
    }

    @Override
    public void deleteIncome(Income income) {
        dao.delete(income);
    }

    @Override
    public Income findById(long id) {
        return dao.getById(id);
    }

    @Override
    public List<Income> findAllIncomesInAccount(Account account) {
        return dao.getAccountIncomes(account);
    }

    @Override
    public List<Income> findIncomesInAccountByDate(Account account, LocalDate start, LocalDate end) {
        return dao.getAccountIncomesByDate(account, start, end);
    }

    @Override
    public List<Income> getPageOfIncomes(Account account, int first, int limit) {
        return dao.getIncomesPage(account, first, limit);
    }

    @Override
    public List<Income> getUserIncomesPage(User user, int first, int limit) {
        return dao.getUserIncomesPage(user, first, limit);
    }

    @Override
    public Long getUserIncomesNumber(User user) {
        return dao.getUserIncomesNumber(user);
    }

    @Override
    public Long getAmountOfIncomesInAccount(Account account) {
        return dao.getAccountIncomeAmount(account);
    }

    @Override
    public Income createIncomeFromDto(IncomeDto incomeDto) {
        Account account = accountService.findAccountById(incomeDto.getAccountId());
        Income income = new Income();
        income.setAccount(account);
        income.setNote(incomeDto.getNote());
        income.setAmount(new BigDecimal(incomeDto.getAmount()));
        income.setDate(LocalDate.parse(incomeDto.getDate()));
        income.setTime(LocalTime.now());
        return income;
    }

    @Override
    public BigDecimal sumOfAllIncomes(List<Income> incomes) {
        if (incomes.size() == 0) {
            return BigDecimal.ZERO;
        }
        return incomes.stream().map(Income::getAmount).reduce(BigDecimal::add).get();
    }

}
