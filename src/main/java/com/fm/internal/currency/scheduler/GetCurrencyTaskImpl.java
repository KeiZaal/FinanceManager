package com.fm.internal.currency.scheduler;

import com.fm.internal.currency.CbrClient;
import com.fm.internal.currency.CurrencyData;
import com.fm.internal.currency.dao.CurrencyDao;
import com.fm.internal.currency.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetCurrencyTaskImpl implements GetCurrencyTask {

    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private CbrClient client;

    @Override
    public void execute() {
        List<CurrencyData.ValuteCursOnDate> allCurrencyCursForNow = client.getAllCurrencyCursForNow();
        allCurrencyCursForNow.forEach(currencyData -> {
            Currency currency = new Currency();
            currency.setCharacterCode(currencyData.getChCode());
            currency.setCode(currencyData.getCode());
            currency.setCurs(currencyData.getCurs());
            currency.setName(currencyData.getName());
            currency.setNominal(currencyData.getNom());
            currencyDao.add(currency);
        });
    }
}