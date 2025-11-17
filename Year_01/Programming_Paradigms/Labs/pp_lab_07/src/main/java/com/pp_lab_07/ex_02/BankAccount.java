package com.pp_lab_07.ex_02;

public class BankAccount {
    private long balance;

    public BankAccount(long vBalance) {
        this.balance = vBalance;
    }

    public synchronized void deposit(long depositSum) {
        if (depositSum <= 0) return;
        balance += depositSum;
        System.out.printf("%s deposited %d -> balance=%d%n",
                Thread.currentThread().getName(),
                depositSum,
                balance);
    }

    public synchronized boolean withdraw(long withdrawSum) {
        if (withdrawSum <= 0) return false;
        if (balance < withdrawSum) {
            System.out.printf("%s failed withdraw %d (insufficient) -> balance=%d%n",
                    Thread.currentThread().getName(),
                    withdrawSum,
                    balance);
            return false;
        }
        balance -= withdrawSum;
        System.out.printf("%s withdrew %d -> balance=%d%n",
                Thread.currentThread().getName(),
                withdrawSum,
                balance);
        return true;
    }

    public synchronized long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Balance: " + this.balance;
    }
}