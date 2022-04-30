import java.util.Date;

public class Deposit_account extends Account {
    long basic_deposit_time = 1000L * 60 * 60 * 24 * 365; // 1 год

    protected Deposit_account(int account_id, int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) {
        super(account_id, client_id, tariff_id, account_type_id, account_number, account_amount, account_start_date, account_end_date);
        this.account_type = "deposit";
    }

    protected Deposit_account(int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) {
        this(0, client_id, tariff_id, account_type_id, account_number, account_amount, account_start_date, account_end_date);
    }

    protected Deposit_account(int client_id, int tariff_id, int account_number) throws Exception {
        this(0, client_id, tariff_id, 3, account_number, 0, Storage.formater.parse(Current_date.Get_current_date()), null);
        this.account_end_date.setTime(Storage.formater.parse(Current_date.Get_current_date()).getTime() + basic_deposit_time);
    }

    public int Create() throws Exception {
        return Storage.Save(new Account(client_id, tariff_id, account_type_id, account_number, account_amount, Storage.formater.parse(Current_date.Get_current_date()), account_end_date));
    }
}
