package com.epam.internal.daos;

import com.epam.internal.models.Account;
import com.epam.internal.models.Outcome;
import com.epam.internal.models.Outcome_;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OutcomeDao extends GenericDao<Outcome> {

    public OutcomeDao() {
        super(Outcome.class);
    }

    @Transactional
    public List<Outcome> getAllAccountsOutcomes(Account account) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        return session.createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getOutcomeInAccountByDate(Account account, LocalDate start, LocalDate end) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        Predicate equalDate = criteriaBuilder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return session.createQuery(query).getResultList();
    }
}
