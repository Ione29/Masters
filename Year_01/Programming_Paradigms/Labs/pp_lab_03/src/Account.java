public abstract class Account implements Comparable<Account>
{
    private String IBAN;
    private int ammount;

    protected Account(String vIBAN, int vAmmount)
    {
        this.IBAN = vIBAN;
        this.ammount = vAmmount;
    }

    public int getAmmount()
    {
        return ammount;
    }
    public String getIBAN()
    {
        return IBAN;
    }

    protected boolean withdraw(int vAmmount)
    {
        this.ammount -= vAmmount;
        return true;
    }

    public void deposit(int vAmmount)
    {
        if(vAmmount > 0)
            this.ammount += vAmmount;
    }

    @Override
    public int compareTo(Account vAccount)
    {
        if(this == vAccount)
            return 0;
        if(this.ammount != vAccount.ammount)
            return this.ammount - vAccount.ammount;
        return this.IBAN.compareTo(vAccount.IBAN);
    }
}
