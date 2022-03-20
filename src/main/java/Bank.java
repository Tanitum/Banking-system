import  java.util.*;
public class Bank {
    private int bank_id;
    String bank_name;

    public int GetBank_id ()
    {
        return bank_id;
    }

    protected Bank (int bank_id, String bank_name){
        this.bank_name=bank_name;
        this.bank_id=bank_id;
    }

    @Override
    public String toString(){
        return bank_id+ ";"+bank_name;
    }

    public static List<Bank>  Get_all_banks() throws Exception {
        List<Bank> Banks =Storage.Find_all_banks();
         return Banks;
    }

    public static List<Account>  Get_all_accounts() throws Exception {
        List<Account> Accounts =Storage.Find_all_accounts();
        return Accounts;
    }

    public static List<Client>  Get_all_clients() throws Exception {
        List<Client> Clients =Storage.Find_all_clients();
        return Clients;
    }

    public static List<Tariff>  Get_all_tariffs() throws Exception {
        List<Tariff> Tariffs =Storage.Find_all_tariffs();
        return Tariffs;
    }

    public int Add_tariff(Tariff tariff) throws Exception {
        throw new Exception("not emplemented yet");
        //  return 7;
    }

    public int Add_client(Person person) throws Exception {
        throw new Exception("not emplemented yet");
        //  return 6;
    }

}
