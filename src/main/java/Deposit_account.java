import java.util.Date;

public class Deposit_account extends Account {
    long basic_deposit_time = 1000L * 60 * 60 * 24 * 365; // 1 год
    int account_percent;

    protected Deposit_account() throws Exception {
        this.account_id = 0;
        this.account_type_id = 3;
        this.account_type = "deposit";
        this.account_start_date = Storage.formater.parse(Current_date.Get_current_date());
        this.account_end_date.setTime(account_start_date.getTime() + basic_deposit_time);
        this.account_amount = 0;
        this.basic_deposit_time = 1000L * 60 * 60 * 24 * 365;
    }

    public static class DepositAccountBuilder {
        private Deposit_account newDepositAccount;

        public DepositAccountBuilder() throws Exception {
            newDepositAccount = new Deposit_account();
        }

        public Deposit_account.DepositAccountBuilder withAccountId(int account_id) {
            newDepositAccount.account_id = account_id;
            return this;
        }

        public Deposit_account.DepositAccountBuilder withClientId(int client_id) {
            newDepositAccount.client_id = client_id;
            return this;
        }

        public Deposit_account.DepositAccountBuilder withTariffId(int tariff_id) throws Exception {
            newDepositAccount.tariff_id = tariff_id;
            newDepositAccount.account_percent = Storage.Get_tariff_by_id(tariff_id).account_percent;
            return this;
        }

        public Deposit_account.DepositAccountBuilder withAccountNumber(int account_number) {
            newDepositAccount.account_number = account_number;
            return this;
        }

        public Deposit_account.DepositAccountBuilder withAccountAmount(double account_amount) {
            newDepositAccount.account_amount = account_amount;
            return this;
        }

        public Deposit_account.DepositAccountBuilder withAccountStartDate(Date account_start_date) {
            newDepositAccount.account_start_date = account_start_date;
            newDepositAccount.account_end_date.setTime(account_start_date.getTime() + newDepositAccount.basic_deposit_time);
            return this;
        }

        public Deposit_account.DepositAccountBuilder withAccountEndDate(Date account_end_date) {
            newDepositAccount.account_end_date = account_end_date;
            return this;
        }

        public Deposit_account build() {
            return newDepositAccount;
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
        if (account_amount >= 0) {
            calculated_percent = Math.ceil((account_amount * (account_percent * 0.01)) * 1000) / 1000; //1000 -> 3 знака после запятой
            account_amount = account_amount + calculated_percent;
        }
        return calculated_percent;
    }
}
