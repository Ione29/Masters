import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client
{
    private String name;
    private LocalDate bday;
    private String CNP;
    private ArrayList<Account> accounts = new ArrayList<>();

    public Client(String vName, LocalDate vBday, String vCNP)
    {
        this.name = vName;
        this.bday = vBday;
        this.CNP = vCNP;
    }

    public String getName(){
        return name;
    }

    public void setName(String vName){
        this.name = vName;
    }

    public LocalDate getBday(){
        return bday;
    }

    public void setBday(LocalDate vBday){
        this.bday = vBday;
    }

    public String getCNP(){
        return CNP;
    }
    
    public void setCNP(String vCNP){
        this.CNP = vCNP;
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public void addAccont(Account vAccount){
        if(accounts.stream().noneMatch(acc -> acc.getIBAN().equals(vAccount.getIBAN())))
            this.accounts.add(vAccount);
    }

    public boolean removeAccount(String vIBAN){
        return this.accounts.removeIf(acc -> acc.getIBAN().equals(vIBAN));
    }

    public void addAccount(Account vAccount){
        this.accounts.add(vAccount);
    }
}
