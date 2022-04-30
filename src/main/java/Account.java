import java.util.Date;

public class Account {
    int account_id;
    int client_id;
    int tariff_id;
    int account_type_id;
    int account_number;
    double account_amount;
    Date account_start_date = new Date();
    Date account_end_date = new Date();
    String account_type;
    int account_percent;

    public int GetAccount_id() {
        return account_id;
    }

    protected Account(int account_id, int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) {
        this.account_id = account_id;
        this.client_id = client_id;
        this.tariff_id = tariff_id;
        this.account_type_id = account_type_id;
        this.account_number = account_number;
        this.account_amount = account_amount;
        this.account_start_date = account_start_date;
        this.account_end_date = account_end_date;
        if (account_type_id == 1) {
            this.account_type = "credit";
        } else if (account_type_id == 2) {
            this.account_type = "debit";
        } else if (account_type_id == 3) {
            this.account_type = "deposit";
        }
    }

    protected Account(int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) {
        this(0, client_id, tariff_id, account_type_id, account_number, account_amount, account_start_date, account_end_date);
    }

    protected Account() {
        this.account_id = 0;
    }

    @Override
    public String toString() {
        return account_id + ";" + client_id + ";" + tariff_id + ";" + account_type_id + ";" + account_number + ";" + account_amount + ";" + Storage.formater.format(account_start_date) + ";" + Storage.formater.format(account_end_date);
    }

    public Client Get_client() throws Exception {
        return Storage.Get_client_by_id(client_id);
    }

    public String Get_account_type() throws Exception {
        return Storage.Get_account_type_by_id(account_type_id);
    }

    public Tariff Get_tariff() throws Exception {
        return Storage.Get_tariff_by_id(tariff_id);
    }

    public double Get_balance(Date date) throws Exception {
        if (Storage.formater.format(date).equals(Current_date.Get_current_date())) {
            return this.account_amount;
        } else {
            throw new Exception("not emplemented yet");
        }
    }

    public String Calculate_percent(Date date) throws Exception {
        throw new Exception("not emplemented yet");
        //return "x";
    }

    public int Transfer_money(int account_number_to, double transfer_size) throws Exception {
        return Transfer.Transfer_money(this.account_number, account_number_to, transfer_size);
    }

    public void Get_cash(double amount) throws Exception {
        Transfer.Transfer_money(this.account_number, 11111111, amount);
    }

    public void Supplement_balance(double amount) throws Exception {
        Transfer.Transfer_money(11111111, this.account_number, amount);
    }

    public void Close_account() throws Exception {
        throw new Exception("not emplemented yet");
    }

}
