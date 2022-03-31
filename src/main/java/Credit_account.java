import java.util.Date;

public class Credit_account extends Account{
    int credit_commission;
    double credit_limit;


    protected Credit_account(int account_id, int client_id, int tariff_id, int account_type_id, int account_number, double account_amount, Date account_start_date, Date account_end_date) {
        super(account_id, client_id, tariff_id, account_type_id, account_number, account_amount, account_start_date, account_end_date);
    }
}
