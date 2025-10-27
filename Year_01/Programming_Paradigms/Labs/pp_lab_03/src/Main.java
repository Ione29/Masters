import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        Client client1 = new Client("Alexandru-Mihail Ionita", LocalDate.of(2002, 6, 10), "5020610123456");
        Client client2 = new Client("Tiberiu-Adrian Petre", LocalDate.of(2002, 4, 20), "5020420211183");

        DebitAccount debit = new DebitAccount("RO00BANK0001", 1000);
        CreditAccount credit = new CreditAccount("RO00BANK0002", 1000000000, 500);

        client1.addAccount(debit);
        client1.addAccount(credit);

        debit.deposit(300);
        debit.withdraw(500);
        debit.withdraw(2000);

        debit = new DebitAccount("RO00BANK0003", 10000);
        credit = new CreditAccount("RO00BANK0004", 159, 2000);

        credit.withdraw(400);
        credit.withdraw(200);

        client1.addAccount(new DebitAccount("RO00BANK0001", 9999));
        System.out.println("Accounts:");
        client1.getAccounts().forEach(acc -> System.out.println(acc.getIBAN() + " - " + acc.getAmmount()));
        client1.removeAccount("RO00BANK0001");
        client1.removeAccount("RO00BANK9999");
        System.out.println("\nAfter removal:");
        client1.getAccounts().forEach(acc -> System.out.println(acc.getIBAN() + " - " + acc.getAmmount()));

        client2.addAccount(debit);
        client2.addAccount(credit);

        List<Account> accounts = client2.getAccounts();
        accounts.addAll(client1.getAccounts());
        accounts = accounts.stream().sorted().toList();
        
        System.out.println("\nSorted accounts:");
        for (Account account : accounts)
            System.out.println(account.getIBAN() + " - " + account.getAmmount());
    }
}
