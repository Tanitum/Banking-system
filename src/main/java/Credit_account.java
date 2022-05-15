import java.util.Date;

public class Credit_account extends Account {
    int credit_commission;
    double credit_limit;

    protected Credit_account() throws Exception {
        this.account_id = 0;
        this.account_type_id = 1;
        this.account_type = "credit";
        this.account_start_date = Storage.formater.parse(Current_date.Get_current_date());
        this.account_end_date = Storage.formater.parse("31.12.9999");
        this.account_amount = 0;
    }

    public static class CreditAccountBuilder {
        private Credit_account newCreditAccount;

        public CreditAccountBuilder() throws Exception {
            newCreditAccount = new Credit_account();
        }

        public Credit_account.CreditAccountBuilder withAccountId(int account_id) {
            newCreditAccount.account_id = account_id;
            return this;
        }

        public Credit_account.CreditAccountBuilder withClientId(int client_id) {
            newCreditAccount.client_id = client_id;
            return this;
        }

        public Credit_account.CreditAccountBuilder withTariffId(int tariff_id) throws Exception {
            newCreditAccount.tariff_id = tariff_id;
            newCreditAccount.credit_commission = Storage.Get_tariff_by_id(tariff_id).credit_commission;
            newCreditAccount.credit_limit = Storage.Get_tariff_by_id(tariff_id).credit_limit;
            return this;
        }

        public Credit_account.CreditAccountBuilder withAccountNumber(int account_number) {
            newCreditAccount.account_number = account_number;
            return this;
        }

        public Credit_account.CreditAccountBuilder withAccountAmount(double account_amount) {
            newCreditAccount.account_amount = account_amount;
            return this;
        }

        public Credit_account.CreditAccountBuilder withAccountStartDate(Date account_start_date) {
            newCreditAccount.account_start_date = account_start_date;
            return this;
        }

        public Credit_account.CreditAccountBuilder withAccountEndDate(Date account_end_date) {
            newCreditAccount.account_end_date = account_end_date;
            return this;
        }

        public Credit_account build() {
            return newCreditAccount;
        }

    }

    public int Create() throws Exception {
        return Storage.Save(new Account.AccountBuilder()
                .withClientId(client_id)
                .withTariffId(tariff_id)
                .withAccountTypeId(account_type_id)
                .withAccountNumber(account_number)
                .withAccountAmount(account_amount)
                .withAccountStartDate(Storage.formater.parse(Current_date.Get_current_date()))
                .withAccountEndDate(account_end_date)
                .build());
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
