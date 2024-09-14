package com.hepexta.moneytransferservice;


import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    long id;
    AtomicInteger balance;

    public Account(int id, int balance) {
        this.id = id;
        this.balance = new AtomicInteger(balance);
    }

    public int getBalance() {
        return balance.get();
    }

    public boolean deposit(int amount) {
        ExceptionHelper.throwIf(amount <= 0, "Amount insufficient");
        balance.addAndGet(amount);
        return true;
    }

    public boolean withdraw(int amount) {
        ExceptionHelper.throwIf(amount <= 0, "Amount should not be negative");

        while (true) {
            int currentBalance = this.balance.get();
            ExceptionHelper.throwIf(currentBalance < amount, "Not enough money");

            int newBalance = currentBalance - amount;
            if (balance.compareAndSet(currentBalance, newBalance)) {
                return true;
            }
            System.out.println("Will try again");
        }
    }
}
