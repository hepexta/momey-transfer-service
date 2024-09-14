package com.hepexta.moneytransferservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferServiceTest {

    private Account account1;
    private Account account2;
    private MoneyTransferService transferService;

    @BeforeEach
    void setup() {
        account1 = new Account(1, 1000);
        account2 = new Account(2, 500);
        transferService = new MoneyTransferService();
    }

    @Test
    void testInitialBalance() {
        assertEquals(1000, account1.getBalance(), "Account 1 initial balance should be 1000");
        assertEquals(500, account2.getBalance(), "Account 2 initial balance should be 500");
    }

    @Test
    void testDeposit() {
        assertTrue(account1.deposit(200));
        assertEquals(1200, account1.getBalance(), "Account 1 balance should be 1200 after deposit");
    }

    @Test
    void testWithdrawSufficientBalance() {
        assertTrue(account1.withdraw(300), "Withdrawal should succeed when sufficient balance is available");
        assertEquals(700, account1.getBalance(), "Account 1 balance should be 700 after withdrawal");
    }

    @Test
    void testWithdrawInsufficientBalance() {
        assertThrows(RuntimeException.class, () -> account2.withdraw(600), "Withdrawal should fail when insufficient balance");
        assertEquals(500, account2.getBalance(), "Account 2 balance should remain 500 after failed withdrawal");
    }

    @Test
    void testTransferSufficientBalance() {
        assertTrue(transferService.transfer(account1, account2, 300), "Transfer should succeed");
        assertEquals(700, account1.getBalance(), "Account 1 balance should be 700 after transfer");
        assertEquals(800, account2.getBalance(), "Account 2 balance should be 800 after transfer");
    }

    @Test
    void testTransferInsufficientBalance() {
        assertThrows(RuntimeException.class, () -> transferService.transfer(account2, account1, 600), "Withdrawal should fail when insufficient balance");
        assertEquals(1000, account1.getBalance(), "Account 1 balance should remain 1000 after failed transfer");
        assertEquals(500, account2.getBalance(), "Account 2 balance should remain 500 after failed transfer");
    }

    @Test
    void testNegativeDepositThrowsException() {
        assertThrows(RuntimeException.class, () -> account1.deposit(-100), "Negative deposit should throw exception");
    }

    @Test
    void testNegativeWithdrawThrowsException() {
        assertThrows(RuntimeException.class, () -> account1.withdraw(-200), "Negative withdrawal should throw exception");
    }
}

