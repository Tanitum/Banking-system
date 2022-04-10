import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

public class Client {
    int client_id;
    int person_id;
    int bank_id;
    Date client_start_date = new Date();
    enum Client_status{
        Unreliable,
        Unlimited
    }
    Client_status client_status;

    public int GetClient_id ()
    {
        return client_id;
    }

    protected Client (int client_id, int person_id, int bank_id, Date client_start_date) throws Exception {
        this.client_id=client_id;
        this.person_id=person_id;
        this.bank_id=bank_id;
        this.client_start_date=client_start_date;
        this.Client_status_checker();
    }

    protected Client (int person_id, int bank_id, Date client_start_date) throws Exception {
        this.client_id=0;
        this.person_id=person_id;
        this.bank_id=bank_id;
        this.client_start_date=client_start_date;
        this.Client_status_checker();
    }

    @Override
    public String toString(){
        return client_id+ ";"+person_id+ ";"+ bank_id+ ";"+Storage.formater.format(client_start_date) + ";"+client_status;
    }

    public Person Get_person() throws Exception {
        return Storage.Get_person_by_id(person_id);
    }
    public Bank Get_bank() throws Exception {
        return Storage.Get_bank_by_id(bank_id);
    }

    public int  Create_account(String account_type,int account_number) throws Exception {
        List<Account> Accounts =Storage.Find_all_accounts();
        for (Account item : Accounts)
        {
            if (item.account_type.equals(account_type) && item.client_id==client_id){
                throw new Exception("Счёт данного типа у данного клиента уже существует. id этого счёта: " +item.account_id);
            }
            if (item.account_number==account_number){
                throw new Exception("Счёт c данным номером уже существует. Придумайте другой номер счёта");
            }
        }
        List<Tariff> Bank_tariffs=Storage.Get_bank_by_id(bank_id).Get_all_tariffs();
        int account_type_id;
        Date end_date;
        if (account_type.equals("credit") || account_type.equals("Credit")){
            end_date=Storage.formater.parse("31.12.9999");
            account_type_id=1;
        }else if (account_type.equals("debit") || account_type.equals("Debit")){
            end_date=Storage.formater.parse("31.12.9999");
            account_type_id=2;
        }else {  //deposit
            end_date=Storage.formater.parse(Current_date.Get_current_date());
            //31536000000 миллисекунд в году
            //по умолчанию депозит будет закрыт через 1 год
            long deposit_time =Storage.formater.parse(Current_date.Get_current_date()).getTime();
            deposit_time+=31536000000L;
            end_date.setTime(deposit_time);
            account_type_id=3;
        }
        //по умолчанию берется первый найденный тариф в банке, в котором создан данный клиент.
        return Storage.Save(new Account(client_id, Bank_tariffs.get(0).GetTariff_id(), account_type_id,account_number,0,Storage.formater.parse(Current_date.Get_current_date()),end_date));
    }

    public void  Close_account(int account_number) throws Exception {
        throw new Exception("not emplemented yet");
    }

    private void  Client_status_checker() throws Exception {
        if (this.Get_person().person_address.equals("-") || this.Get_person().person_passport.equals("-")){
            this.client_status=Client_status.Unreliable;
        }else{
            this.client_status=Client_status.Unlimited;
        }
    }

}
