package com.fm.internal.services;

import com.fm.internal.daos.GoalDao;
import com.fm.internal.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

@ContextConfiguration("classpath:spring-utils.xml")
public class AccountServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoalService goalService;

    private final static String USER_EMAIL = "user@email";

    @BeforeMethod
    public void setUp() throws Exception {
        Currency rub = new Currency("Ruble", BigDecimal.ONE, BigDecimal.ONE, 999, "RUB");
        currencyService.addOrUpdateCurrency(rub);
        User user = new User(USER_EMAIL, "password", new UserInfo("name", "surname", currencyService.findCurrencyByCharCode("RUB")));
        userService.createUser(user);
        Account acc1 = new Account("visa", BigDecimal.valueOf(1234), null, userService.findByEmail(USER_EMAIL),
                currencyService.findCurrencyByCharCode("RUB"));
        Account acc2 = new Account("mastercard", BigDecimal.valueOf(4321), null, userService.findByEmail(USER_EMAIL),
                currencyService.findCurrencyByCharCode("RUB"));
        Goal goal = new Goal("My summer trip", BigDecimal.ZERO, BigDecimal.ZERO, null, userService.findByEmail(USER_EMAIL), currencyService.findCurrencyByCharCode("RUB"));
        accountService.createAccount(acc1);
        accountService.createAccount(acc2);
        goalService.addGoal(goal);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        if (user != null) {
            userService.deleteUser(user);
        }
    }

    @Test
    public void testFindAllUserAccounts() throws Exception {
        final int ACCOUNTS_AMOUNT = 4;
        User user = userService.findByEmail(USER_EMAIL);
        Assert.assertEquals(accountService.findAllUserAccounts(user).size(), ACCOUNTS_AMOUNT);
        Assert.assertEquals(goalService.getGoalsByUser(user).size(), 1);
        //accountService.findAllUserAccounts(user).forEach(account -> System.out.println(account.getClass()));
        System.out.printf("%s, %d, %s", goalService.getGoalsByUser(user).get(0).getName(), goalService.getGoalsByUser(user).get(0).getId(), goalService.getGoalsByUser(user).get(0).getClass().toString());
    }

    @Test
    public void testFindUserAccountByName() throws Exception {
        final String USER_ACCOUNT_NAME = "visa";
        final int USER_ACCOUNT_BALANCE = 1234;
        User user = userService.findByEmail(USER_EMAIL);
        Assert.assertEquals(accountService.findUserAccountByName(user, USER_ACCOUNT_NAME).getName(), USER_ACCOUNT_NAME);
        Assert.assertEquals(accountService.findUserAccountByName(user, USER_ACCOUNT_NAME).getBalance().intValue(), USER_ACCOUNT_BALANCE);
    }

    @Test
    public void testSearchInEmptyTable() throws Exception {
        userService.deleteUser(userService.findByEmail("user@email"));
        User user = userService.findByEmail("user@email");
        Assert.assertNull(user);
        Assert.assertNull(accountService.findUserAccountByName(user, "visa"));
        Assert.assertNull(accountService.findAllUserAccounts(user));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAccountDelete() throws Exception {
        User user = userService.findByEmail(USER_EMAIL);
        userService.deleteUser(user);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Account> accounts = session.createQuery("Select a From Account a where a.user = :user")
                .setParameter("user", user).getResultList();
        session.getTransaction().commit();
        session.close();
        Assert.assertEquals(0, accounts.size());
    }

}