public class DebitAccount extends Account
{
    public DebitAccount(String vIBAN, int vAmmount)
    {
        super(vIBAN, vAmmount);
    }

    @Override
    public boolean withdraw(int vSum)
    {
        return super.withdraw(vSum);
    }
}
