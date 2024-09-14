package com.hepexta.moneytransferservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferThreadSafetyTest {
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
    void testConcurrentTransfers() throws InterruptedException {
        int numThreads = 100;

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < numThreads; i++) {
            scheduleTransfer(executorService, latch, account1, account2, 2);
            scheduleTransfer(executorService, latch, account2, account1, 1);
        }

        latch.countDown();

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);


        // Assert final balances
        assertEquals(900, account1.getBalance(), "Final balance of account 1 is incorrect");
        assertEquals(600, account2.getBalance(), "Final balance of account 2 is incorrect");
    }

    private void scheduleTransfer(ExecutorService executorService, CountDownLatch latch, Account account1, Account account2, int amount) {
        executorService.submit(
                () -> {
                    try {
                        latch.await();
                        transferService.transfer(account1, account2, amount);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
    }
}
