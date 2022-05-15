import Exceptions.ObjectAlreadyExistsException;

import java.util.Date;
import java.util.List;

public class Client {
    int client_id;
    int person_id;
    int bank_id;
    Date client_start_date = new Date();

    enum Client_status {
        Unreliable, Unlimited
    }

    Client_status client_status;

    public int GetClient_id() {
        return client_id;
    }

    protected Client(int client_id, int person_id, int bank_id, Date client_start_date) throws Exception {
        this.client_id = client_id;
        this.person_id = person_id;
        this.bank_id = bank_id;
        this.client_start_date = client_start_date;
        this.Client_status_checker();
    }

    protected Client(int person_id, int bank_id, Date client_start_date) throws Exception {
        this(0, person_id, bank_id, client_start_date);
    }

    @Override
    public String toString() {
        return client_id + ";" + person_id + ";" + bank_id + ";" + Storage.formater.format(client_start_date) + ";" + client_status;
    }

    public Person Get_person() throws Exception {
        return Storage.Get_person_by_id(person_id);
    }

    public Bank Get_bank() throws Exception {
        return Storage.Get_bank_by_id(bank_id);
    }

    private void Check_new_account(String account_type, int account_number) throws Exception {
        List<Account> Accounts = Storage.Find_all_accounts();
        for (Account item : Accounts) {
            if (item.account_type.equals(account_type) && item.client_id == client_id) {
                throw new ObjectAlreadyExistsException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_WITH_TYPE_ALREADY_EXISTS") + item.account_id);
            }
            if (item.account_number == account_number) {
                throw new ObjectAlreadyExistsException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_WITH_NUMBER_ALREADY_EXISTS"));
            }
        }
    }

    public int Create_account(String account_type, int account_number) throws Exception {
        Check_new_account(account_type, account_number);
        List<Tariff> Bank_tariffs = Storage.Get_bank_by_id(bank_id).Get_all_tariffs();
        if (account_type.equals("credit") || account_type.equals("Credit")) {
            Credit_account credit_account = new Credit_account.CreditAccountBuilder()
                    .withClientId(client_id)
                    .withTariffId(Bank_tariffs.get(0).GetTariff_id())
                    .withAccountNumber(account_number)
                    .build();
            return credit_account.Create();
        } else if (account_type.equals("debit") || account_type.equals("Debit")) {
            Debit_account debit_account = new Debit_account.DebitAccountBuilder()
                    .withClientId(client_id)
                    .withTariffId(Bank_tariffs.get(0).GetTariff_id())
                    .withAccountNumber(account_number)
                    .build();
            return debit_account.Create();
        } else if (account_type.equals("deposit") || account_type.equals("Deposit")) {
            Deposit_account deposit_account = new Deposit_account.DepositAccountBuilder()
                    .withClientId(client_id)
                    .withTariffId(Bank_tariffs.get(0).GetTariff_id())
                    .withAccountNumber(account_number)
                    .build();
            return deposit_account.Create();
        } else {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_TYPE"));
        }
        //по умолчанию берется первый найденный тариф в банке, в котором создан данный клиент.
    }

    public void Close_account(int account_number) throws Exception {
        Account account = Storage.Find(account_number);
        if (account.client_id == client_id) {
            account.Close_account();
        } else {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_CLOSING_ACCOUNT_CLIENT"));
        }
    }

    private void Client_status_checker() throws Exception {
        if (this.Get_person().person_address.equals("-") || this.Get_person().person_passport.equals("-")) {
            this.client_status = Client_status.Unreliable;
        } else {
            this.client_status = Client_status.Unlimited;
        }
    }

}
