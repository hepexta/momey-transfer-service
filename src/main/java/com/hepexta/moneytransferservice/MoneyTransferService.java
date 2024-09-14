package com.hepexta.moneytransferservice;

public class MoneyTransferService {
    public boolean transfer(Account from, Account to, int amount) {
        ExceptionHelper.throwIf(amount <= 0, "Amount insufficient");

        if (from.withdraw(amount)) {
            return to.deposit(amount);
        }
        return false;
    }
}
