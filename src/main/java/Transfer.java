import java.util.Date;
import java.util.List;

public class Transfer {
    private int transfer_id;
    private int original_transfer_id;

    enum Transfer_status {
        Completed,
        Canceled,
        Rejected,
        In_progress
    }

    Transfer_status transfer_status;
    Account account_from;
    Account account_to;
    double transfer_size;
    Date transfer_date = new Date();

    public int GetTransfer_id() {
        return transfer_id;
    }

    public int GetOriginalTransfer_id() {
        return original_transfer_id;
    }

    public Transfer_status GetTransfer_status() {
        return transfer_status;
    }

    protected Transfer(int transfer_id, int original_transfer_id, String transfer_status, int account_from_id, int account_to_id, double transfer_size, Date transfer_date) throws Exception {
        this.transfer_id = transfer_id;
        this.original_transfer_id = original_transfer_id;
        this.transfer_status = Transfer_status.valueOf(transfer_status);
        this.account_from = Storage.Get_account_by_id(account_from_id);
        this.account_to = Storage.Get_account_by_id(account_to_id);
        this.transfer_size = transfer_size;
        this.transfer_date = transfer_date;
    }

    protected Transfer(int transfer_id, int account_from_id, int account_to_id, double transfer_size) throws Exception {
        this(transfer_id, -1, Transfer_status.Completed.toString(), account_from_id, account_to_id, transfer_size, Storage.formater.parse(Current_date.Get_current_date()));
    }

    protected Transfer(int account_from_id, int account_to_id, double transfer_size) throws Exception {
        this(0, account_from_id, account_to_id, transfer_size);
    }

    @Override
    public String toString() {
        return transfer_id + ";" + original_transfer_id + ";" + transfer_status + ";" + account_from.GetAccount_id() + ";" + account_to.GetAccount_id() + ";" + transfer_size + ";" + Storage.formater.format(transfer_date);
    }

    private static void Check_new_transfer_accounts_exist(int account_number_from, int account_number_to) throws Exception {
        List<Account> Accounts = Storage.Find_all_accounts();
        Account Account_from = new Account();
        Account Account_to = new Account();
        for (Account item : Accounts) {
            if (item.account_number == account_number_from) {
                Account_from = item;
            }
            if (item.account_number == account_number_to) {
                Account_to = item;
            }
        }
        if (Account_from.account_number != account_number_from) {
            throw new IllegalArgumentException("Не существует счёта, с которого вы хотите сделать перевод.");
        }
        if (Account_to.account_number != account_number_to) {
            throw new IllegalArgumentException("Не существует счёта, на который вы хотите сделать перевод.");
        }
    }

    private static void Check_new_transfer_accounts_not_closed(int account_number_from, int account_number_to) throws Exception {
        List<Account> Accounts = Storage.Find_all_accounts();
        Account Account_from = new Account();
        Account Account_to = new Account();
        for (Account item : Accounts) {
            if (item.account_number == account_number_from) {
                Account_from = item;
            }
            if (item.account_number == account_number_to) {
                Account_to = item;
            }
        }
        if (Account_from.account_end_date.getTime() <= Storage.formater.parse(Current_date.Get_current_date()).getTime() && Account_from.account_type_id != 3) {
            throw new IllegalArgumentException("Cчёт, с которого отправляются деньги, закрыт.");
        }
        if (Account_to.account_end_date.getTime() <= Storage.formater.parse(Current_date.Get_current_date()).getTime()) {
            throw new IllegalArgumentException("Cчёт, на который отправляются деньги, закрыт.");
        }
    }

    private static void Check_new_transfer_money_available(int account_number_from, double transfer_size) throws Exception {
        if (transfer_size < 0) {
            throw new NumberFormatException("Нельзя выполнять переводы на отрицательную сумму.");
        }
        Account Account_from = Storage.Find(account_number_from);
        if (Account_from.Get_client().client_status.equals(Client.Client_status.Unreliable)) {
            if (transfer_size > Account_from.Get_tariff().status_limit) {
                throw new SecurityException("Ваш статус не позволяет вам сделать перевод выше суммы: " + Account_from.Get_tariff().status_limit);
            }
        }
        if (Account_from.account_type.equals("credit")) {
            double max_available = Account_from.account_amount + Account_from.Get_tariff().credit_limit;
            if (max_available < transfer_size) {
                throw new IllegalArgumentException("У кредитного счёта при выполнении перевода будет превышен кредитный лимит. Максимально можно перевести: " + max_available);
            }
        }
        if (Account_from.account_type.equals("debit")) {
            if (Account_from.account_amount < transfer_size) {
                throw new IllegalArgumentException("На дебетовом счёте не хватает денег для перевода. На нём сейчас лежит: " + Account_from.account_amount);
            }
        }
        if (Account_from.account_type.equals("deposit")) {
            if (Account_from.account_end_date.after(Storage.formater.parse(Current_date.Get_current_date()))) {
                throw new IllegalArgumentException("С депозитного счёта нельзя снимать деньги до закрытия счёта. Счёт будет закрыт: " + Storage.formater.format(Account_from.account_end_date));
            } else {
                if (Account_from.account_amount < transfer_size) {
                    throw new IllegalArgumentException("На депозитном счёте не хватает денег для перевода. На нём сейчас лежит: " + Account_from.account_amount);
                }
            }
        }
    }

    public static int Transfer_money(int account_number_from, int account_number_to, double transfer_size) throws Exception {
        Check_new_transfer_accounts_exist(account_number_from, account_number_to);
        Check_new_transfer_accounts_not_closed(account_number_from, account_number_to);
        Check_new_transfer_money_available(account_number_from, transfer_size);

        Account Account_from = Storage.Find(account_number_from);
        Account Account_to = Storage.Find(account_number_to);
        Account_from.account_amount = Account_from.account_amount - transfer_size;
        Account_to.account_amount = Account_to.account_amount + transfer_size;
        Storage.Save(Account_from);
        Storage.Save(Account_to);
        return Storage.Save(new Transfer(Account_from.GetAccount_id(), Account_to.GetAccount_id(), transfer_size));
    }

    protected static int Cancel_transfer(int transfer_id) throws Exception {
        Transfer start_transfer = Storage.Get_transfer_by_id(transfer_id);
        if (start_transfer.transfer_status != Transfer_status.Completed) {
            throw new Exception("Трансфер нельзя отменить, т.к. он: " + start_transfer.transfer_status);
        }
        Transfer reverse_transfer = Storage.Get_transfer_by_id(Transfer.Transfer_money(start_transfer.account_to.account_number, start_transfer.account_from.account_number, start_transfer.transfer_size));
        start_transfer.transfer_status = Transfer_status.Rejected;
        reverse_transfer.original_transfer_id = start_transfer.transfer_id;
        Storage.Save(start_transfer);
        Storage.Save(reverse_transfer);
        return reverse_transfer.GetTransfer_id();
    }

}
