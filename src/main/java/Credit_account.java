import java.util.Date;

public class Credit_account extends Account {
    int credit_commission;
    double credit_limit;

    protected Credit_account(int account_id, int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) throws Exception {
        super(account_id, client_id, tariff_id, account_type_id, account_number, account_amount, account_start_date, account_end_date);
        this.account_type = "credit";
        this.account_type_id = 1;
        this.credit_commission = Storage.Get_tariff_by_id(tariff_id).credit_commission;
        this.credit_limit = Storage.Get_tariff_by_id(tariff_id).credit_limit;
    }

    protected Credit_account(int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) throws Exception {
        this(0, client_id, tariff_id, account_type_id, account_number, account_amount, account_start_date, account_end_date);
    }

    protected Credit_account(int client_id, int tariff_id, int account_number) throws Exception {
        this(0, client_id, tariff_id, 1, account_number, 0, Storage.formater.parse(Current_date.Get_current_date()), Storage.formater.parse("31.12.9999"));
    }

    public int Create() throws Exception {
        return Storage.Save(new Account(client_id, tariff_id, account_type_id, account_number, account_amount, Storage.formater.parse(Current_date.Get_current_date()), account_end_date));
    }

    public double Calculate_percent() throws Exception {
        double calculated_percent = 0;
        if (this.account_amount < 0) {
            calculated_percent = Math.ceil((account_amount * (credit_commission * 0.01)) * 1000) / 1000; //1000 -> 3 знака после запятой
            account_amount = account_amount + calculated_percent;
        }
        return calculated_percent;
    }
}
