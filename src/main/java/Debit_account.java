import java.util.Date;

public class Debit_account extends Account {

    protected Debit_account() throws Exception {
        this.account_id = 0;
        this.account_type_id = 2;
        this.account_type = "debit";
        this.account_start_date = Storage.formater.parse(Current_date.Get_current_date());
        this.account_end_date = Storage.formater.parse("31.12.9999");
        this.account_amount = 0;
    }

    public static class DebitAccountBuilder {
        private Debit_account newDebitAccount;

        public DebitAccountBuilder() throws Exception {
            newDebitAccount = new Debit_account();
        }

        public Debit_account.DebitAccountBuilder withAccountId(int account_id) {
            newDebitAccount.account_id = account_id;
            return this;
        }

        public Debit_account.DebitAccountBuilder withClientId(int client_id) {
            newDebitAccount.client_id = client_id;
            return this;
        }

        public Debit_account.DebitAccountBuilder withTariffId(int tariff_id) throws Exception {
            newDebitAccount.tariff_id = tariff_id;
            return this;
        }

        public Debit_account.DebitAccountBuilder withAccountNumber(int account_number) {
            newDebitAccount.account_number = account_number;
            return this;
        }

        public Debit_account.DebitAccountBuilder withAccountAmount(double account_amount) {
            newDebitAccount.account_amount = account_amount;
            return this;
        }

        public Debit_account.DebitAccountBuilder withAccountStartDate(Date account_start_date) {
            newDebitAccount.account_start_date = account_start_date;
            return this;
        }

        public Debit_account.DebitAccountBuilder withAccountEndDate(Date account_end_date) {
            newDebitAccount.account_end_date = account_end_date;
            return this;
        }

        public Debit_account build() {
            return newDebitAccount;
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
        return 0;
    }
}
