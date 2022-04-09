import java.io.FileNotFoundException;
import java.util.Date;
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

    public int  Create_account(String account_type) throws Exception {
        throw new Exception("not emplemented yet");
       // return 8;
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
