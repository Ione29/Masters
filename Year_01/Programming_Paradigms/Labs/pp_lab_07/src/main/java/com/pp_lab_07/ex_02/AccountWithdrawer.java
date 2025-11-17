package com.pp_lab_07.ex_02;

public class AccountWithdrawer implements Runnable {
    private final BankAccount account;
    private final long amount;
    private final int iterations;
    private final long pauseMs;

    public AccountWithdrawer(BankAccount account, long amount, int iterations, long pauseMs) {
        this.account = account;
        this.amount = amount;
        this.iterations = iterations;
        this.pauseMs = pauseMs;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            account.withdraw(amount);
            try {
                Thread.sleep(pauseMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}