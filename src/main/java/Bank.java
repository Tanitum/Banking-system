import Exceptions.ObjectAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private int bank_id;
    String bank_name;

    public int GetBank_id() {
        return bank_id;
    }

    protected Bank(int bank_id, String bank_name) {
        this.bank_name = bank_name;
        this.bank_id = bank_id;
    }

    protected Bank(String bank_name) {
        this(0, bank_name);
    }

    @Override
    public String toString() {
        return bank_id + ";" + bank_name;
    }

    public List<Account> Get_all_accounts() throws Exception {
        List<Account> Accounts = Storage.Find_all_accounts();
        List<Account> Bank_accounts = new ArrayList<Account>();
        for (Account item : Accounts) {
            if (this.bank_id == item.Get_client().Get_bank().GetBank_id()) {
                Bank_accounts.add(item);
            }
        }
        return Bank_accounts;
    }

    public List<Client> Get_all_clients() throws Exception {
        List<Client> Clients = Storage.Find_all_clients();
        List<Client> Bank_clients = new ArrayList<Client>();
        for (Client item : Clients) {
            if (this.bank_id == item.Get_bank().GetBank_id()) {
                Bank_clients.add(item);
            }
        }
        return Bank_clients;
    }

    public List<Tariff> Get_all_tariffs() throws Exception {
        List<Tariff> Tariffs = Storage.Find_all_tariffs();
        List<Tariff> Bank_tariffs = new ArrayList<Tariff>();
        for (Tariff item : Tariffs) {
            if (this.bank_id == item.GetBank_id()) {
                Bank_tariffs.add(item);
            }
        }
        return Bank_tariffs;
    }

    public int Add_tariff(Tariff tariff) throws Exception {
        if (tariff.GetBank_id() == bank_id) {
            return Storage.Save(tariff);
        } else {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_BAD_BANK_OF_TARIFF"));
        }
    }

    public int Add_client(Person person) throws Exception {
        List<Client> Clients = Storage.Find_all_clients();
        for (Client item : Clients) {
            if (item.person_id == person.GetPerson_id() && item.bank_id == bank_id) {
                throw new ObjectAlreadyExistsException(Storage.Find_at_glossary("EXCEPTION_PERSON_ALREADY_CLIENT") + item.client_id);
            }
        }
        return Storage.Save(new Client(person.GetPerson_id(), bank_id, Storage.formater.parse(Current_date.Get_current_date())));
    }

}
