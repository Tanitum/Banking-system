import java.util.Scanner;

public class Console_user_interface {
    public static Scanner scan = new Scanner(System.in);

    public static void startProgram() throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_NAME"));
        String user_name = scan.nextLine();
        if (user_name.equals("Admin")) {
            throw new SecurityException(Storage.Find_at_glossary("EXCEPTION_NOT_ADMIN"));
        }
        System.out.println(Storage.Find_at_glossary("SAY_SURNAME"));
        String user_surname = scan.nextLine();
        System.out.println(Storage.Find_at_glossary("PERSON_EXISTS?"));
        System.out.println(Storage.Find_at_glossary("PERSON_WARNING"));
        String yes_or_now = scan.nextLine();
        Person user_person;


        String user_address;
        String user_passport;
        if (yes_or_now.equals(Storage.Find_at_glossary("SAY_YES"))) {
            user_person = Storage.Find(user_name, user_surname);
            user_address = user_person.person_address;
            user_passport = user_person.person_passport;
        } else if (yes_or_now.equals(Storage.Find_at_glossary("SAY_NO"))) {
            System.out.println(Storage.Find_at_glossary("SAY_ADDRESS"));
            user_address = scan.nextLine();
            System.out.println(Storage.Find_at_glossary("SAY_PASSPORT"));
            user_passport = scan.nextLine();
            user_person = new Person(user_name, user_surname, user_address, user_passport);
            Storage.Save(user_person);
            user_person = Storage.Find(user_name, user_surname);
        } else {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_NOT_YES_OR_NO"));
        }
        System.out.println(Storage.Find_at_glossary("VIEW_PERSON_DATA") + user_person);

        System.out.println(Storage.Find_at_glossary("BANK_LIST"));
        yes_or_now = scan.nextLine();
        if (yes_or_now.equals(Storage.Find_at_glossary("SAY_YES"))) {
            System.out.println(Storage.Find_at_glossary("VIEW_BANK_LIST") + Storage.Find_all_banks());
        }

        System.out.println(Storage.Find_at_glossary("SAY_BANK_NAME"));
        String user_bank_name = scan.nextLine();
        if (user_bank_name.equals("Adminbank")) {
            throw new SecurityException(Storage.Find_at_glossary("EXCEPTION_NOT_ADMIN"));
        }
        Bank user_bank = Storage.Find(user_bank_name);
        System.out.println(Storage.Find_at_glossary("VIEW_BANK_DATA") + user_bank);
        System.out.println(Storage.Find_at_glossary("SAY_ABOUT_BANK"));
        System.out.println(Storage.Find_at_glossary("BANK_WARNING"));
        yes_or_now = scan.nextLine();
        Client user_client;
        if (yes_or_now.equals(Storage.Find_at_glossary("SAY_YES"))) {
            user_client = Storage.Find(user_name, user_surname, user_bank_name);
        } else if (yes_or_now.equals(Storage.Find_at_glossary("SAY_NO"))) {
            user_client = new Client(user_person.GetPerson_id(), user_bank.GetBank_id(), Storage.formater.parse(Current_date.Get_current_date()));
            Storage.Save(user_client);
            user_client = Storage.Find(user_name, user_surname, user_bank_name);
            System.out.println(Storage.Find_at_glossary("NEW_CLIENT_SUCCESSFULLY"));
        } else {
            throw new IllegalArgumentException(Storage.Find_at_glossary("EXCEPTION_NOT_YES_OR_NO"));
        }
        System.out.println(Storage.Find_at_glossary("VIEW_CLIENT_DATA") + user_client);

        Account user_account;
        int user_account_number;
        String user_account_type;
        if (Storage.Find_client_accounts(user_client.GetClient_id()).isEmpty()) {
            System.out.println(Storage.Find_at_glossary("BANK_FIRST_CLIENT"));
            user_account = Storage.Get_account_by_id(createAccount(user_client));
            user_account_number = user_account.account_number;
            user_account_type = user_account.account_type;
        } else {
            user_account_number = Storage.Find_client_accounts(user_client.GetClient_id()).get(0).account_number;
            user_account = Storage.Find(user_account_number);
            user_account_type = user_account.account_type;
        }
        System.out.println(Storage.Find_at_glossary("SUCCESSFUL_AUTHORIZATION"));

        boolean end = false;
        String x;
        while (!end) {
            System.out.println(Storage.Find_at_glossary("HELP_0"));
            x = scan.nextLine();
            if (x.equals("0")) {
                getInfo();
            }
            if (x.equals("1")) {
                end = true;
            }
            if (x.equals("2")) {
                changeAddressAndPassport(user_address, user_passport, user_person, user_client, user_name, user_surname, user_bank_name);
                user_client = Storage.Find(user_name, user_surname, user_bank_name);
                user_address = user_person.person_address;
                user_passport = user_person.person_passport;
            }
            if (x.equals("3")) {
                System.out.println(Storage.Find_at_glossary("VIEW_PERSON_DATA") + user_person);
            }
            if (x.equals("4")) {
                System.out.println(Storage.Find_at_glossary("VIEW_BANK_DATA") + user_bank);
            }
            if (x.equals("5")) {
                System.out.println(Storage.Find_at_glossary("VIEW_CLIENT_DATA") + user_client);
            }
            if (x.equals("6")) {
                System.out.println(Storage.Find_at_glossary("VIEW_ACCOUNT_DATA") + user_account);
            }
            if (x.equals("7")) {
                System.out.println(Storage.Find_client_accounts(user_client.GetClient_id()));
            }
            if (x.equals("8")) {
                System.out.println(Storage.Find_at_glossary("SAY_ACCOUNT_NUMBER"));
                user_account_number = scan.nextInt();
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("9")) {
                user_account = Storage.Get_account_by_id(createAccount(user_client));
                user_account_number = user_account.account_number;
                user_account_type = user_account.account_type;
            }
            if (x.equals("10")) {
                getCash(user_account);
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("11")) {
                supplementBalance(user_account);
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("12")) {
                transferMoney(user_account);
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("13")) {
                user_account.Close_account();
                System.out.println(Storage.Find_at_glossary("ACCOUNT_CLOSED_SUCCESSFUL"));
            }
            if (x.equals("14")) {
                getTypeAndStatusOfAccount(user_account);
            }
            if (x.equals("15")) {
                getBalanceAtDate(user_account);
            }
        }
    }

    private static void getInfo() throws Exception {
        System.out.println(Storage.Find_at_glossary("HELP"));
        System.out.println(Storage.Find_at_glossary("HELP_1"));
        System.out.println(Storage.Find_at_glossary("HELP_2"));
        System.out.println(Storage.Find_at_glossary("HELP_3"));
        System.out.println(Storage.Find_at_glossary("HELP_4"));
        System.out.println(Storage.Find_at_glossary("HELP_5"));
        System.out.println(Storage.Find_at_glossary("HELP_6"));
        System.out.println(Storage.Find_at_glossary("HELP_7"));
        System.out.println(Storage.Find_at_glossary("HELP_8"));
        System.out.println(Storage.Find_at_glossary("HELP_9"));
        System.out.println(Storage.Find_at_glossary("HELP_10"));
        System.out.println(Storage.Find_at_glossary("HELP_11"));
        System.out.println(Storage.Find_at_glossary("HELP_12"));
        System.out.println(Storage.Find_at_glossary("HELP_13"));
        System.out.println(Storage.Find_at_glossary("HELP_14"));
        System.out.println(Storage.Find_at_glossary("HELP_15"));
    }

    private static void changeAddressAndPassport(String user_address, String user_passport, Person user_person, Client user_client, String user_name, String user_surname, String user_bank_name) throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_ADDRESS"));
        user_address = scan.nextLine();
        System.out.println(Storage.Find_at_glossary("SAY_PASSPORT"));
        user_passport = scan.nextLine();
        user_person.Change_person_info(user_address, user_passport);
        user_client = Storage.Find(user_name, user_surname, user_bank_name); // для перерасчета статуса человека
        System.out.println(Storage.Find_at_glossary("VIEW_NEW_PERSON_DATA") + user_person);
        System.out.println(Storage.Find_at_glossary("VIEW_CLIENT_DATA") + user_client);
    }

    private static int createAccount(Client user_client) throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_NEW_ACCOUNT_NUMBER"));
        int user_account_number = scan.nextInt();
        System.out.println(Storage.Find_at_glossary("SAY_ACCOUNT_TYPE"));
        String user_account_type = scan.nextLine();
        user_account_type = scan.nextLine();
        Account user_account = Storage.Get_account_by_id(user_client.Create_account(user_account_type, user_account_number));
        return user_account.account_id;
    }

    private static void getCash(Account user_account) throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_CASH_BALANCE"));
        double user_account_transfer = scan.nextDouble();
        user_account.Get_cash(user_account_transfer);
    }

    private static void supplementBalance(Account user_account) throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_SUPPLEMENT_BALANCE"));
        double user_account_transfer = scan.nextDouble();
        user_account.Supplement_balance(user_account_transfer);
    }

    private static void transferMoney(Account user_account) throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_ACCOUNT_NUMBER_TO"));
        int user_account_number_to = scan.nextInt();
        System.out.println(Storage.Find_at_glossary("SAY_TRANSFER_BALANCE"));
        double user_account_transfer = scan.nextDouble();
        user_account.Transfer_money(user_account_number_to, user_account_transfer);
    }

    private static void getTypeAndStatusOfAccount(Account user_account) throws Exception {
        System.out.println(Storage.Find_at_glossary("ACCOUNT_TYPE") + user_account.account_type);
        if (user_account.account_end_date.getTime() <= Storage.formater.parse(Current_date.Get_current_date()).getTime()) {
            System.out.println(Storage.Find_at_glossary("ACCOUNT_CLOSED") + Storage.formater.format(user_account.account_end_date));
        } else {
            System.out.println(Storage.Find_at_glossary("ACCOUNT_OPENED"));
        }
    }

    private static void getBalanceAtDate(Account user_account) throws Exception {
        System.out.println(Storage.Find_at_glossary("SAY_DATE_OF_ACCOUNT"));
        String data = scan.nextLine();
        double balance = user_account.Get_balance(Storage.formater.parse(data));
        System.out.println(Storage.Find_at_glossary("BALANCE_ON_DATE") + data + Storage.Find_at_glossary("EQUALS") + balance);
    }
}