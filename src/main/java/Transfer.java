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

    protected Transfer(int transfer_id, int account_from_id, int account_to_id, double transfer_size) throws Exception {
        this.transfer_id = transfer_id;
        this.original_transfer_id = -1;
        this.transfer_status = Transfer_status.Completed;
        this.account_from = Storage.Get_account_by_id(account_from_id);
        this.account_to = Storage.Get_account_by_id(account_to_id);
        this.transfer_size = transfer_size;
        this.transfer_date = Storage.formater.parse(Current_date.Get_current_date());
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

    protected Transfer(int account_from_id, int account_to_id, double transfer_size) throws Exception {
        this.transfer_id = 0;
        this.original_transfer_id = -1;
        this.transfer_status = Transfer_status.Completed;
        this.account_from = Storage.Get_account_by_id(account_from_id);
        this.account_to = Storage.Get_account_by_id(account_to_id);
        this.transfer_size = transfer_size;
        this.transfer_date = Storage.formater.parse(Current_date.Get_current_date());
    }

    @Override
    public String toString() {
        return transfer_id + ";" + original_transfer_id + ";" + transfer_status + ";" + account_from.GetAccount_id() + ";" + account_to.GetAccount_id() + ";" + transfer_size + ";" + Storage.formater.format(transfer_date);
    }


    public static int Transfer_money(int account_number_from, int account_number_to, double transfer_size) throws Exception {
        if (transfer_size < 0) {
            throw new Exception("Нельзя выполнять переводы на отрицательную сумму.");
        }
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
            throw new Exception("Не существует счёта, с которого вы хотите сделать перевод.");
        }
        if (Account_to.account_number != account_number_to) {
            throw new Exception("Не существует счёта, на который вы хотите сделать перевод.");
        }
        Client.Client_status status = Account_from.Get_client().client_status;
        if (status.equals(Client.Client_status.Unreliable)) {
            if (transfer_size > Account_from.Get_tariff().status_limit) {
                throw new Exception("Ваш статус не позволяет вам сделать перевод выше суммы: " + Account_from.Get_tariff().status_limit);
            }
        }
        int Transfer_id = -2;
        if (Account_from.account_type.equals("credit")) {
            double max_available = Account_from.account_amount + Account_from.Get_tariff().credit_limit;
            if (max_available < transfer_size) {
                throw new Exception("У кредитного счёта при выполнении перевода будет превышен кредитный лимит. Максимально можно перевести: " + max_available);
            } else {
                Account_from.account_amount = Account_from.account_amount - transfer_size;
                Account_to.account_amount = Account_to.account_amount + transfer_size;
                Storage.Save(Account_from);
                Storage.Save(Account_to);
                Transfer_id = Storage.Save(new Transfer(Account_from.GetAccount_id(), Account_to.GetAccount_id(), transfer_size));
            }
        }
        if (Account_from.account_type.equals("debit")) {
            if (Account_from.account_amount < transfer_size) {
                throw new Exception("На дебетовом счёте не хватает денег для перевода. На нём сейчас лежит: " + Account_from.account_amount);
            } else {
                Account_from.account_amount = Account_from.account_amount - transfer_size;
                Account_to.account_amount = Account_to.account_amount + transfer_size;
                Storage.Save(Account_from);
                Storage.Save(Account_to);
                Transfer_id = Storage.Save(new Transfer(Account_from.GetAccount_id(), Account_to.GetAccount_id(), transfer_size));
            }
        }
        if (Account_from.account_type.equals("deposit")) {
            if (Account_from.account_end_date.after(Storage.formater.parse(Current_date.Get_current_date()))) {
                throw new Exception("С депозитного счёта нельзя снимать деньги до закрытия счёта. Счёт будет закрыт: " + Storage.formater.format(Account_from.account_end_date));
            } else {
                if (Account_from.account_amount < transfer_size) {
                    throw new Exception("На депозитном счёте не хватает денег для перевода. На нём сейчас лежит: " + Account_from.account_amount);
                } else {
                    Account_from.account_amount = Account_from.account_amount - transfer_size;
                    Account_to.account_amount = Account_to.account_amount + transfer_size;
                    Storage.Save(Account_from);
                    Storage.Save(Account_to);
                    Transfer_id = Storage.Save(new Transfer(Account_from.GetAccount_id(), Account_to.GetAccount_id(), transfer_size));
                }
            }
        }
        return Transfer_id;
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
