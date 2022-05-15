import java.util.Date;
import java.util.List;

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

    protected Account() throws Exception {
        this.account_id = 0;
        this.account_amount = 0;
        this.account_start_date = Storage.formater.parse(Current_date.Get_current_date());
    }

    public static class AccountBuilder {
        private Account newAccount;

        public AccountBuilder() throws Exception {
            newAccount = new Account();
        }

        public AccountBuilder withAccountId(int account_id) {
            newAccount.account_id = account_id;
            return this;
        }

        public AccountBuilder withClientId(int client_id) {
            newAccount.client_id = client_id;
            return this;
        }

        public AccountBuilder withTariffId(int tariff_id) {
            newAccount.tariff_id = tariff_id;
            return this;
        }

        public AccountBuilder withAccountTypeId(int account_type_id) {
            newAccount.account_type_id = account_type_id;
            if (account_type_id == 1) {
                newAccount.account_type = "credit";
            } else if (account_type_id == 2) {
                newAccount.account_type = "debit";
            } else if (account_type_id == 3) {
                newAccount.account_type = "deposit";
            }
            return this;
        }

        public AccountBuilder withAccountNumber(int account_number) {
            newAccount.account_number = account_number;
            return this;
        }

        public AccountBuilder withAccountAmount(double account_amount) {
            newAccount.account_amount = account_amount;
            return this;
        }

        public AccountBuilder withAccountStartDate(Date account_start_date) {
            newAccount.account_start_date = account_start_date;
            return this;
        }

        public AccountBuilder withAccountEndDate(Date account_end_date) {
            newAccount.account_end_date = account_end_date;
            return this;
        }

        public Account build() {
            return newAccount;
        }

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
        if (account_number == 11111111) {
            throw new SecurityException(Storage.Find_at_glossary("EXCEPTION_ADMIN_ACCOUNT"));
        }
        if (Storage.formater.format(date).equals(Current_date.Get_current_date())) {
            return this.account_amount;
        } else if (date.getTime() > Storage.formater.parse(Current_date.Get_current_date()).getTime()) {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_BALANCE_IN_FUTURE"));
        } else if (account_start_date.getTime() > date.getTime()) {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_NO_ACCOUNT_AT_DATE"));
        } else {
            double balance = 0;
            List<Transfer> transfers = Storage.Find_all_transfers();
            for (Transfer item : transfers) {
                if (item.transfer_date.getTime() <= date.getTime() && item.transfer_status != Transfer.Transfer_status.Canceled) {
                    if (item.account_from.account_number == account_number) {
                        balance -= item.transfer_size;
                    }
                    if (item.account_to.account_number == account_number) {
                        balance += item.transfer_size;
                    }
                }
            }
            return balance;
        }
    }

    public String Calculate_percent(Date date) throws Exception {
        if (date.equals(Storage.formater.parse(Current_date.Get_current_date()))) {
            List<Account> Accounts = Storage.Find_all_accounts();
            for (Account item : Accounts) {
                if (date.getTime() < item.account_end_date.getTime() && date.getTime() >= item.account_start_date.getTime() && item.account_number != 11111111) {
                    if (item.account_type_id == 1) {
                        Credit_account credit_account = new Credit_account.CreditAccountBuilder()
                                .withAccountId(item.account_id)
                                .withClientId(item.client_id)
                                .withTariffId(item.tariff_id)
                                .withAccountNumber(item.account_number)
                                .withAccountAmount(item.Get_balance(date))
                                .withAccountStartDate(item.account_start_date)
                                .withAccountEndDate(item.account_end_date)
                                .build();
                        Transfer.Transfer_money(item.account_number, 11111111, Math.abs(credit_account.Calculate_percent()));
                        // Возможно, при слишком больших накопленных суммах процентов будет превышен кредитный лимит или лимит из-за статуса.
                    } else if (item.account_type_id == 2) {
                        Debit_account debit_account = new Debit_account.DebitAccountBuilder()
                                .withAccountId(item.account_id)
                                .withClientId(item.client_id)
                                .withTariffId(item.tariff_id)
                                .withAccountNumber(item.account_number)
                                .withAccountAmount(item.Get_balance(date))
                                .withAccountStartDate(item.account_start_date)
                                .withAccountEndDate(item.account_end_date)
                                .build();
                        Transfer.Transfer_money(11111111, item.account_number, debit_account.Calculate_percent());
                        // Не уверен, что нужны эти нулевые переводы, т.к. хранилище данных заполняется лишней информацией.
                    } else if (item.account_type_id == 3) {
                        Deposit_account deposit_account = new Deposit_account.DepositAccountBuilder()
                                .withAccountId(item.account_id)
                                .withClientId(item.client_id)
                                .withTariffId(item.tariff_id)
                                .withAccountNumber(item.account_number)
                                .withAccountAmount(item.Get_balance(date))
                                .withAccountStartDate(item.account_start_date)
                                .withAccountEndDate(item.account_end_date)
                                .build();
                        Transfer.Transfer_money(11111111, item.account_number, deposit_account.Calculate_percent());
                    }
                }
            }
            return Storage.Find_at_glossary("PERCENT_ADDED");
        } else {
            throw new SecurityException(Storage.Find_at_glossary("EXCEPTION_PERCENT_AT_DATE"));
        }
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
        Account account = Storage.Find(account_number);
        if (account.account_end_date.getTime() <= Storage.formater.parse(Current_date.Get_current_date()).getTime()) {
            throw new IllegalArgumentException(Storage.Find_at_glossary("ACCOUNT_CLOSED") + Storage.formater.format(account.account_end_date));
        }
        if (account.account_amount != 0) {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_BALANCE_NOT_NULL") + account.account_amount);
        }
        this.account_end_date = Storage.formater.parse(Current_date.Get_current_date());
        account.account_end_date = Storage.formater.parse(Current_date.Get_current_date());
        Storage.Save(account);
    }

}
