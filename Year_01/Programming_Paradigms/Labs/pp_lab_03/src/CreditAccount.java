public class CreditAccount extends Account
{
    private int maxCredit;

    public CreditAccount(String vIBAN, int vAmmount, int vMaxCredit)
    {
        super(vIBAN, vAmmount);
        this.maxCredit = vMaxCredit;
    }
    @Override
    public boolean withdraw(int vAmmount)
    {
        if(this.getAmmount() + maxCredit > vAmmount)
            super.withdraw(vAmmount);
        return false;
    }

}
