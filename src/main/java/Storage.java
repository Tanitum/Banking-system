import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private static String Person_file_name = "src/main/resources/Person.csv";
    private static String Bank_file_name = "src/main/resources/Bank.csv";
    private static String Client_file_name = "src/main/resources/Client.csv";
    private static String Account_file_name = "src/main/resources/Account.csv";
    private static String Account_type_file_name = "src/main/resources/Account_type.csv";
    private static String Tariff_file_name = "src/main/resources/Tariff.csv";
    private static String Transfer_file_name = "src/main/resources/Transfer.csv";

    private static String Glossary_file_name = "src/main/resources/Glossary.csv";

    public static SimpleDateFormat formater;

    static {
        try {
            formater = new SimpleDateFormat(Storage.Find_at_glossary("DATE_FORMAT"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String[]> Read_file(String object_type) throws Exception {
        String file_name = "";
        switch (object_type) {
            case "Person":
                file_name = Person_file_name;
                break;
            case "Bank":
                file_name = Bank_file_name;
                break;
            case "Client":
                file_name = Client_file_name;
                break;
            case "Account":
                file_name = Account_file_name;
                break;
            case "Account_type":
                file_name = Account_type_file_name;
                break;
            case "Tariff":
                file_name = Tariff_file_name;
                break;
            case "Transfer":
                file_name = Transfer_file_name;
                break;
            case "Glossary":
                file_name = Glossary_file_name;
                break;
            default:
                break;
        }
        File f = new File(file_name);
        Scanner scan = new Scanner(f);
        List<String[]> listOfString = new ArrayList<String[]>();
        while (scan.hasNextLine()) {
            String[] linesArray = scan.nextLine().split(";");
            listOfString.add(linesArray);
        }
        return listOfString;
    }

    public static Person Find(String person_name, String person_surname) throws Exception {
        List<String[]> list = Read_file("Person");
        for (String[] item : list) {
            if (item[1].equals(person_name) && item[2].equals(person_surname)) {
                return new Person(Integer.valueOf(item[0]), item[1], item[2], item[3], item[4]);
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_PERSON_NOT_EXISTS"));
    }

    public static List<Person> Find_all_persons() throws Exception {
        List<String[]> list = Read_file("Person");
        List<Person> Person_list = new ArrayList<Person>();
        for (String[] item : list) {
            Person_list.add(new Person(Integer.valueOf(item[0]), String.valueOf(item[1]), String.valueOf(item[2]), String.valueOf(item[3]), String.valueOf(item[4])));
        }
        return Person_list;
    }

    public static Person Get_person_by_id(Integer person_id) throws Exception {
        List<String[]> list = Read_file("Person");
        for (String[] item : list) {
            if (person_id.toString().equals(item[0])) {
                return new Person(Integer.valueOf(item[0]), item[1], item[2], item[3], item[4]);
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_PERSON_NOT_EXISTS"));
    }

    public static int Save(Person person) throws Exception {
        List<Person> Person_list = Storage.Find_all_persons();
        int person_id = person.GetPerson_id();
        FileWriter Fw = new FileWriter(Person_file_name);
        if (person_id == 0) {
            int max_id = -2;
            for (Person item : Person_list) {
                if (item.GetPerson_id() > max_id) {
                    max_id = item.GetPerson_id();
                }
                Fw.write(item.GetPerson_id() + ";" + item.person_name + ";" + item.person_surname + ";" + item.person_address + ";" + item.person_passport);
                Fw.append('\n');
            }
            person_id = max_id + 1;
            Person_list.add(new Person(person_id, person.person_name, person.person_surname, person.person_address, person.person_passport));
            Fw.write(person_id + ";" + person.person_name + ";" + person.person_surname + ";" + person.person_address + ";" + person.person_passport);
            Fw.append('\n');
        } else {
            int index = 0;
            boolean is_not_found = true;
            for (Person item : Person_list) {
                if (item.GetPerson_id() == person_id) {
                    is_not_found = false;
                    Person_list.set(index, person);
                }
                Fw.write(Person_list.get(index).GetPerson_id() + ";" + Person_list.get(index).person_name + ";" + Person_list.get(index).person_surname + ";" + Person_list.get(index).person_address + ";" + Person_list.get(index).person_passport);
                Fw.append('\n');
                index += 1;
            }
            if (is_not_found) {
                Fw.close();
                throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_PERSON_NOT_EXISTS"));
            }
        }
        Fw.close();
        return person_id;
    }

    public static Client Find(String person_name, String person_surname, String bank_name) throws Exception {
        List<Client> list = Find_all_clients();
        Person p;
        for (Client item : list) {
            p = item.Get_person();
            if (item.Get_bank().bank_name.equals(bank_name) && p.person_name.equals(person_name) && p.person_surname.equals(person_surname)) {
                return item;
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_CLIENT_NOT_EXISTS"));
    }

    public static List<Client> Find_all_clients() throws Exception {
        List<String[]> list = Read_file("Client");
        List<Client> Client_list = new ArrayList<Client>();
        for (String[] item : list) {
            Client_list.add(new Client(Integer.valueOf(item[0]), Integer.valueOf(item[1]), Integer.valueOf(item[2]), formater.parse(item[3])));
        }
        return Client_list;
    }

    public static Client Get_client_by_id(Integer client_id) throws Exception {
        List<String[]> list = Read_file("Client");
        for (String[] item : list) {
            if (client_id.toString().equals(item[0])) {
                return new Client(Integer.valueOf(item[0]), Integer.valueOf(item[1]), Integer.valueOf(item[2]), formater.parse(item[3]));
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_CLIENT_NOT_EXISTS"));
    }

    public static int Save(Client client) throws Exception {
        List<Client> Client_list = Storage.Find_all_clients();
        int client_id = client.GetClient_id();
        FileWriter Fw = new FileWriter(Client_file_name);
        if (client_id == 0) {
            int max_id = -2;
            for (Client item : Client_list) {
                if (item.GetClient_id() > max_id) {
                    max_id = item.GetClient_id();
                }
                Fw.write(item.GetClient_id() + ";" + item.person_id + ";" + item.bank_id + ";" + formater.format(item.client_start_date));
                Fw.append('\n');
            }
            client_id = max_id + 1;
            Client_list.add(new Client(client_id, client.person_id, client.bank_id, client.client_start_date));
            Fw.write(client_id + ";" + client.person_id + ";" + client.bank_id + ";" + formater.format(client.client_start_date));
            Fw.append('\n');
        } else {
            int index = 0;
            boolean is_not_found = true;
            for (Client item : Client_list) {
                if (item.GetClient_id() == client_id) {
                    is_not_found = false;
                    Client_list.set(index, client);
                }
                Fw.write(Client_list.get(index).GetClient_id() + ";" + Client_list.get(index).person_id + ";" + Client_list.get(index).bank_id + ";" + formater.format(Client_list.get(index).client_start_date));
                Fw.append('\n');
                index += 1;
            }
            if (is_not_found) {
                Fw.close();
                throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_CLIENT_NOT_EXISTS"));
            }
        }
        Fw.close();
        return client_id;
    }

    public static Bank Find(String bank_name) throws Exception {
        List<String[]> list = Read_file("Bank");
        for (String[] item : list) {
            if (item[1].equals(bank_name)) {
                return new Bank(Integer.valueOf(item[0]), item[1]);
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_BANK_NOT_EXISTS"));
    }

    public static List<Bank> Find_all_banks() throws Exception {
        List<String[]> list = Read_file("Bank");
        List<Bank> Bank_list = new ArrayList<Bank>();
        for (String[] item : list) {
            Bank_list.add(new Bank(Integer.valueOf(item[0]), item[1]));
        }
        return Bank_list;
    }

    public static Bank Get_bank_by_id(Integer bank_id) throws Exception {
        List<String[]> list = Read_file("Bank");
        for (String[] item : list) {
            if (bank_id.toString().equals(item[0])) {
                return new Bank(Integer.valueOf(item[0]), item[1]);
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_BANK_NOT_EXISTS"));
    }

    public static int Save(Bank bank) throws Exception {
        List<Bank> Bank_list = Storage.Find_all_banks();
        int bank_id = bank.GetBank_id();
        FileWriter Fw = new FileWriter(Bank_file_name);
        if (bank_id == 0) {
            int max_id = -2;
            for (Bank item : Bank_list) {
                if (item.GetBank_id() > max_id) {
                    max_id = item.GetBank_id();
                }
                Fw.write(item.GetBank_id() + ";" + item.bank_name);
                Fw.append('\n');
            }
            bank_id = max_id + 1;
            Bank_list.add(new Bank(bank_id, bank.bank_name));
            Fw.write(bank_id + ";" + bank.bank_name);
            Fw.append('\n');
        } else {
            int index = 0;
            boolean is_not_found = true;
            for (Bank item : Bank_list) {
                if (item.GetBank_id() == bank_id) {
                    is_not_found = false;
                    Bank_list.set(index, bank);
                }
                Fw.write(Bank_list.get(index).GetBank_id() + ";" + Bank_list.get(index).bank_name);
                Fw.append('\n');
                index += 1;
            }
            if (is_not_found) {
                Fw.close();
                throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_BANK_NOT_EXISTS"));
            }
        }
        Fw.close();
        return bank_id;
    }

    public static List<Account> Find_client_accounts(Integer client_id) throws Exception {
        List<Account> list = Find_all_accounts();
        List<Account> Account_list = new ArrayList<Account>();
        for (Account item : list) {
            if (item.Get_client().client_id == client_id) {
                Account_list.add(item);
            }
        }
        return Account_list;
    }

    public static Account Find(Integer account_number) throws Exception {
        List<String[]> list = Read_file("Account");
        for (String[] item : list) {
            if (account_number.toString().equals(item[4])) {
                return new Account.AccountBuilder()
                        .withAccountId(Integer.valueOf(item[0]))
                        .withClientId(Integer.valueOf(item[1]))
                        .withTariffId(Integer.valueOf(item[2]))
                        .withAccountTypeId(Integer.valueOf(item[3]))
                        .withAccountNumber(Integer.valueOf(item[4]))
                        .withAccountAmount(Double.valueOf(item[5]))
                        .withAccountStartDate(formater.parse(item[6]))
                        .withAccountEndDate(formater.parse(item[7]))
                        .build();
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_NOT_EXISTS"));
    }

    public static List<Account> Find_all_accounts() throws Exception {
        List<String[]> list = Read_file("Account");
        List<Account> Account_list = new ArrayList<Account>();
        for (String[] item : list) {
            Account_list.add(new Account.AccountBuilder()
                    .withAccountId(Integer.valueOf(item[0]))
                    .withClientId(Integer.valueOf(item[1]))
                    .withTariffId(Integer.valueOf(item[2]))
                    .withAccountTypeId(Integer.valueOf(item[3]))
                    .withAccountNumber(Integer.valueOf(item[4]))
                    .withAccountAmount(Double.valueOf(item[5]))
                    .withAccountStartDate(formater.parse(item[6]))
                    .withAccountEndDate(formater.parse(item[7]))
                    .build());
        }
        return Account_list;
    }

    public static Account Get_account_by_id(Integer account_id) throws Exception {
        List<String[]> list = Read_file("Account");
        for (String[] item : list) {
            if (account_id.toString().equals(item[0])) {
                return new Account.AccountBuilder()
                        .withAccountId(Integer.valueOf(item[0]))
                        .withClientId(Integer.valueOf(item[1]))
                        .withTariffId(Integer.valueOf(item[2]))
                        .withAccountTypeId(Integer.valueOf(item[3]))
                        .withAccountNumber(Integer.valueOf(item[4]))
                        .withAccountAmount(Double.valueOf(item[5]))
                        .withAccountStartDate(formater.parse(item[6]))
                        .withAccountEndDate(formater.parse(item[7]))
                        .build();
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_NOT_EXISTS"));
    }

    public static int Save(Account account) throws Exception {
        List<Account> Account_list = Storage.Find_all_accounts();
        int account_id = account.GetAccount_id();
        FileWriter Fw = new FileWriter(Account_file_name);
        if (account_id == 0) {
            int max_id = -2;
            for (Account item : Account_list) {
                if (item.GetAccount_id() > max_id) {
                    max_id = item.GetAccount_id();
                }
                Fw.write(item.GetAccount_id() + ";" + item.client_id + ";" + item.tariff_id + ";" + item.account_type_id + ";" + item.account_number + ";" + item.account_amount + ";" + formater.format(item.account_start_date) + ";" + formater.format(item.account_end_date));
                Fw.append('\n');
            }
            account_id = max_id + 1;
            Account_list.add(new Account.AccountBuilder()
                    .withAccountId(account_id)
                    .withClientId(account.client_id)
                    .withTariffId(account.tariff_id)
                    .withAccountTypeId(account.account_type_id)
                    .withAccountNumber(account.account_number)
                    .withAccountAmount(account.account_amount)
                    .withAccountStartDate(account.account_start_date)
                    .withAccountEndDate(account.account_end_date)
                    .build());
            Fw.write(account_id + ";" + account.client_id + ";" + account.tariff_id + ";" + account.account_type_id + ";" + account.account_number + ";" + account.account_amount + ";" + formater.format(account.account_start_date) + ";" + formater.format(account.account_end_date));
            Fw.append('\n');
        } else {
            int index = 0;
            boolean is_not_found = true;
            for (Account item : Account_list) {
                if (item.GetAccount_id() == account_id) {
                    is_not_found = false;
                    Account_list.set(index, account);
                }
                Fw.write(Account_list.get(index).GetAccount_id() + ";" + Account_list.get(index).client_id + ";" + Account_list.get(index).tariff_id + ";" + Account_list.get(index).account_type_id + ";" + Account_list.get(index).account_number + ";" + Account_list.get(index).account_amount + ";" + formater.format(Account_list.get(index).account_start_date) + ";" + formater.format(Account_list.get(index).account_end_date));
                Fw.append('\n');
                index += 1;
            }
            if (is_not_found) {
                Fw.close();
                throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_NOT_EXISTS"));
            }
        }
        Fw.close();
        return account_id;
    }

    public static List<Tariff> Find_all_tariffs() throws Exception {
        List<String[]> list = Read_file("Tariff");
        List<Tariff> Tariff_list = new ArrayList<Tariff>();
        for (String[] item : list) {
            Tariff_list.add(new Tariff(Integer.valueOf(item[0]), Integer.valueOf(item[1]), Integer.valueOf(item[2]), Double.valueOf(item[3]), Integer.valueOf(item[4]), Double.valueOf(item[5])));
        }
        return Tariff_list;
    }

    public static Tariff Get_tariff_by_id(Integer tariff_id) throws Exception {
        List<String[]> list = Read_file("Tariff");
        for (String[] item : list) {
            if (tariff_id.toString().equals(item[0])) {
                return new Tariff(Integer.valueOf(item[0]), Integer.valueOf(item[1]), Integer.valueOf(item[2]), Double.valueOf(item[3]), Integer.valueOf(item[4]), Double.valueOf(item[5]));
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_TARIFF_NOT_EXISTS"));
    }

    public static int Save(Tariff tariff) throws Exception {
        List<Tariff> Tariff_list = Storage.Find_all_tariffs();
        int tariff_id = tariff.GetTariff_id();
        FileWriter Fw = new FileWriter(Tariff_file_name);
        if (tariff_id == 0) {
            int max_id = -2;
            for (Tariff item : Tariff_list) {
                if (item.GetTariff_id() > max_id) {
                    max_id = item.GetTariff_id();
                }
                Fw.write(item.GetTariff_id() + ";" + item.bank_id + ";" + item.account_percent + ";" + item.credit_limit + ";" + item.credit_commission + ";" + item.status_limit);
                Fw.append('\n');
            }
            tariff_id = max_id + 1;
            Tariff_list.add(new Tariff(tariff_id, tariff.bank_id, tariff.account_percent, tariff.credit_limit, tariff.credit_commission, tariff.status_limit));
            Fw.write(tariff_id + ";" + tariff.bank_id + ";" + tariff.account_percent + ";" + tariff.credit_limit + ";" + tariff.credit_commission + ";" + tariff.status_limit);
            Fw.append('\n');
        } else {
            int index = 0;
            boolean is_not_found = true;
            for (Tariff item : Tariff_list) {
                if (item.GetTariff_id() == tariff_id) {
                    is_not_found = false;
                    Tariff_list.set(index, tariff);
                }
                Fw.write(Tariff_list.get(index).GetTariff_id() + ";" + Tariff_list.get(index).bank_id + ";" + Tariff_list.get(index).account_percent + ";" + Tariff_list.get(index).credit_limit + ";" + Tariff_list.get(index).credit_commission + ";" + Tariff_list.get(index).status_limit);
                Fw.append('\n');
                index += 1;
            }
            if (is_not_found) {
                Fw.close();
                throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_TARIFF_NOT_EXISTS"));
            }
        }
        Fw.close();
        return tariff_id;
    }

    public static String Get_account_type_by_id(Integer account_id) throws Exception {
        List<Account> list = Find_all_accounts();
        List<String[]> type_list = Read_file("Account_type");
        for (Account item : list) {
            if (item.account_id == account_id) {
                for (String[] item2 : type_list) {
                    if (new Integer(item.account_type_id).toString().equals(item2[0])) {
                        return item2[1];
                    }
                }
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_ACCOUNT_TYPE_NOT_EXISTS"));
    }

    public static List<String> Find_all_account_types() throws Exception {
        List<String[]> list = Read_file("Account_type");
        List<String> Account_type_list = new ArrayList<String>();
        for (String[] item : list) {
            Account_type_list.add(item[0] + ";" + item[1]);
        }
        return Account_type_list;
    }

    public static Transfer Get_transfer_by_id(Integer transfer_id) throws Exception {
        List<String[]> list = Read_file("Transfer");
        for (String[] item : list) {
            if (transfer_id.toString().equals(item[0])) {
                return new Transfer(Integer.valueOf(item[0]), Integer.valueOf(item[1]), item[2], Integer.valueOf(item[3]), Integer.valueOf(item[4]), Double.valueOf(item[5]), formater.parse(item[6]));
            }
        }
        throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_TRANSFER_NOT_EXISTS"));
    }

    public static List<Transfer> Find_all_transfers() throws Exception {
        List<String[]> list = Read_file("Transfer");
        List<Transfer> Transfer_list = new ArrayList<Transfer>();
        for (String[] item : list) {
            Transfer_list.add(new Transfer(Integer.valueOf(item[0]), Integer.valueOf(item[1]), item[2], Integer.valueOf(item[3]), Integer.valueOf(item[4]), Double.valueOf(item[5]), formater.parse(item[6])));
        }
        return Transfer_list;
    }

    public static int Save(Transfer transfer) throws Exception {
        List<Transfer> Transfer_list = Storage.Find_all_transfers();
        int transfer_id = transfer.GetTransfer_id();
        FileWriter Fw = new FileWriter(Transfer_file_name);
        if (transfer_id == 0) {
            int max_id = -2;
            for (Transfer item : Transfer_list) {
                if (item.GetTransfer_id() > max_id) {
                    max_id = item.GetTransfer_id();
                }
                Fw.write(item.GetTransfer_id() + ";" + item.GetOriginalTransfer_id() + ";" + item.transfer_status + ";" + item.account_from.GetAccount_id() + ";" + item.account_to.GetAccount_id() + ";" + item.transfer_size + ";" + formater.format(item.transfer_date));
                Fw.append('\n');
            }
            transfer_id = max_id + 1;
            Transfer_list.add(new Transfer(transfer_id, transfer.GetOriginalTransfer_id(), transfer.GetTransfer_status().toString(), transfer.account_from.GetAccount_id(), transfer.account_to.GetAccount_id(), transfer.transfer_size, transfer.transfer_date));
            Fw.write(transfer_id + ";" + transfer.GetOriginalTransfer_id() + ";" + transfer.transfer_status + ";" + transfer.account_from.GetAccount_id() + ";" + transfer.account_to.GetAccount_id() + ";" + transfer.transfer_size + ";" + formater.format(transfer.transfer_date));
            Fw.append('\n');
        } else {
            int index = 0;
            boolean is_not_found = true;
            for (Transfer item : Transfer_list) {
                if (item.GetTransfer_id() == transfer_id) {
                    is_not_found = false;
                    Transfer_list.set(index, transfer);
                }
                Fw.write(Transfer_list.get(index).GetTransfer_id() + ";" + Transfer_list.get(index).GetOriginalTransfer_id() + ";" + Transfer_list.get(index).GetTransfer_status().toString() + ";" + Transfer_list.get(index).account_from.GetAccount_id() + ";" + Transfer_list.get(index).account_to.GetAccount_id() + ";" + Transfer_list.get(index).transfer_size + ";" + formater.format(Transfer_list.get(index).transfer_date));
                Fw.append('\n');
                index += 1;
            }
            if (is_not_found) {
                Fw.close();
                throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_TRANSFER_NOT_EXISTS"));
            }
        }
        Fw.close();
        return transfer_id;
    }

    public static String Find_at_glossary(String variable) throws Exception {
        List<String[]> list = Read_file("Glossary");
        for (String[] item : list) {
            if (item[0].equals(variable)) {
                return item[1];
            }
        }
        return "Совпадение в словаре не найдено. В нём нет переменной: " + variable;
    }

}
