package com.fm.internal.services.implementation;

import com.fm.internal.models.*;
import com.fm.internal.services.HashTagService;
import com.fm.internal.services.IncomeService;
import com.fm.internal.services.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UtilServiceImpl {
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private HashTagService hashTagService;

    public BigDecimal recountAccountBalance(Account account) {
        List<Outcome> allOutcomesInAccount = outcomeService.findAllOutcomesInAccount(account);
        List<Income> allIncomesInAccount = incomeService.findAllIncomesInAccount(account);
        BigDecimal sumOfIncomes = incomeService.sumOfAllIncomes(allIncomesInAccount);
        BigDecimal sumOfOutcomes = outcomeService.sumOfAllOutcomes(allOutcomesInAccount);
        return sumOfIncomes.subtract(sumOfOutcomes);
    }

    public List<HashTag> parseHashTags(User user, List<String> stringHashTags) {
        List<HashTag> hashTags = new ArrayList<>();
        if (stringHashTags != null && !stringHashTags.isEmpty()) {
            for (String stringHashTag : stringHashTags) {
                HashTag hashTag = hashTagService.getHashTagByUserAndText(user, stringHashTag);
                if (hashTag == null && stringHashTag.trim().length() > 0) {
                    hashTagService.addHashTag(new HashTag(stringHashTag, user));
                    hashTags.add(hashTagService.getHashTagByUserAndText(user, stringHashTag));
                } else {
                    hashTags.add(hashTag);
                }
            }
        }
        return hashTags;
    }
}
