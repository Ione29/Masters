package com.pp_lab_07.ex_02;

public class AccountMain {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // simple integer balance

        // start 3 depositors
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new AccountDepositor(account, 200, 5, 200));
            t.setName("Depositor-" + i);
            t.start();
        }

        // start 3 withdrawers
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new AccountWithdrawer(account, 150, 6, 300));
            t.setName("Withdrawer-" + i);
            t.start();
        }
    }
}
