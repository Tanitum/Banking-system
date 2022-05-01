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
        if (account_number == 11111111) {
            throw new Exception("Это счёт админа с бесконечными деньгами, которые взялись из воздуха. Для него невозможно рассчитать баланс на дату");
        }
        if (Storage.formater.format(date).equals(Current_date.Get_current_date())) {
            return this.account_amount;
        } else if (date.getTime() > Storage.formater.parse(Current_date.Get_current_date()).getTime()) {
            throw new IllegalArgumentException("Невозможно узнать о балансе счёта в будущем.");
        } else if (account_start_date.getTime() > date.getTime()) {
            throw new IllegalArgumentException("На эту дату счёт ещё не был создан");
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
                        Credit_account credit_account = new Credit_account(item.account_id, item.client_id, item.tariff_id, item.account_type_id, item.account_number, item.Get_balance(date), item.account_start_date, item.account_end_date);
                        Transfer.Transfer_money(item.account_number, 11111111, Math.abs(credit_account.Calculate_percent()));
                        // Возможно, при слишком больших накопленных суммах процентов будет превышен кредитный лимит или лимит из-за статуса.
                    } else if (item.account_type_id == 2) {
                        Debit_account debit_account = new Debit_account(item.account_id, item.client_id, item.tariff_id, item.account_type_id, item.account_number, item.Get_balance(date), item.account_start_date, item.account_end_date);
                        Transfer.Transfer_money(11111111, item.account_number, debit_account.Calculate_percent());
                        // Не уверен, что нужны эти нулевые переводы, т.к. хранилище данных заполняется лишней информацией.
                    } else if (item.account_type_id == 3) {
                        Deposit_account deposit_account = new Deposit_account(item.account_id, item.client_id, item.tariff_id, item.account_type_id, item.account_number, item.Get_balance(date), item.account_start_date, item.account_end_date);
                        Transfer.Transfer_money(11111111, item.account_number, deposit_account.Calculate_percent());
                    }
                }
            }
            return "Проценты успешно начислены";
        } else {
            throw new SecurityException("Невозможно рассчитать проценты на дату, которая не совпадает с Current_date. Вы - админ, вы можете поменять Current_date.");
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
            throw new IllegalArgumentException("Данный счёт уже был закрыт " + Storage.formater.format(account.account_end_date));
        }
        if (account.account_amount != 0) {
            throw new IllegalArgumentException("Нельзя закрыть счёт с ненулевым балансом. Баланс счёта: " + account.account_amount);
        }
        this.account_end_date = Storage.formater.parse(Current_date.Get_current_date());
        account.account_end_date = Storage.formater.parse(Current_date.Get_current_date());
        Storage.Save(account);
    }

}
