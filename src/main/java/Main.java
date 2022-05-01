import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // админ может рассчитать все проценты, изменить дату, узнать и изменить любую информацию базы данных.
        // System.out.println(Storage.Get_account_by_id(-1).Calculate_percent(Storage.formater.parse(Current_date.Get_current_date())));
        System.out.println("Введите ваше имя.");
        String user_name = scan.nextLine();
        if (user_name.equals("Admin")) {
            throw new SecurityException("Вы не являетесь администратором");
        }
        System.out.println("Введите вашу фамилию.");
        String user_surname = scan.nextLine();
        System.out.println("В базе данных есть человек с данным именем и фамилией? Введите [да/нет].");
        System.out.println("Если вы ошибётесь и скажете 'нет', когда нужно было сказать 'да', то дальнейшая корректная работа программы не гарантирована, т.к. будет создан лишний человек, которого необходимо будет удалять из базы данных.");
        String yes_or_now = scan.nextLine();
        Person user_person;
        String user_address;
        String user_passport;
        if (yes_or_now.equals("да")) {
            user_person = Storage.Find(user_name, user_surname);
            user_address = user_person.person_address;
            user_passport = user_person.person_passport;
        } else if (yes_or_now.equals("нет")) {
            System.out.println("Введите ваш адрес. Введите [-], если не хотите сообщать адрес (ваши возможности могут быть ограничены).");
            user_address = scan.nextLine();
            System.out.println("Введите номер вашего паспорта. Введите [-], если не хотите сообщать номер паспорта (ваши возможности могут быть ограничены).");
            user_passport = scan.nextLine();
            user_person = new Person(user_name, user_surname, user_address, user_passport);
            Storage.Save(user_person);
            user_person = Storage.Find(user_name, user_surname);
        } else {
            throw new IllegalArgumentException("Вы ввели не [да/нет].");
        }
        System.out.println("Вот ваши данные: " + user_person);

        System.out.println("Хотите увидеть список всех банков? Введите [да], если хотите.");
        yes_or_now = scan.nextLine();
        if (yes_or_now.equals("да")) {
            System.out.println("Вот список всех банков: " + Storage.Find_all_banks());
        }

        System.out.println("Введите название банка, клиентом которого вы являетесь/хотите стать.");
        String user_bank_name = scan.nextLine();
        if (user_bank_name.equals("Adminbank")) {
            throw new SecurityException("Вы не являетесь администратором");
        }
        Bank user_bank = Storage.Find(user_bank_name);
        System.out.println("Вот ваш выбранный банк: " + user_bank);
        System.out.println("Вы уже являетесь клиентом данного банка? Введите [да/нет].");
        System.out.println("Если вы ошибётесь и скажете 'нет', когда нужно было сказать 'да', то дальнейшая корректная работа программы не гарантирована, т.к. будет создан лишний клиент, которого необходимо будет удалять из базы данных.");
        yes_or_now = scan.nextLine();
        Client user_client;
        if (yes_or_now.equals("да")) {
            user_client = Storage.Find(user_name, user_surname, user_bank_name);
        } else if (yes_or_now.equals("нет")) {
            user_client = new Client(user_person.GetPerson_id(), user_bank.GetBank_id(), Storage.formater.parse(Current_date.Get_current_date()));
            Storage.Save(user_client);
            user_client = Storage.Find(user_name, user_surname, user_bank_name);
            System.out.println("Вы стали клиентом этого банка");
        } else {
            throw new IllegalArgumentException("Вы ввели не [да/нет].");
        }
        System.out.println("Вот данные вашего клиентского аккаунта: " + user_client);

        Account user_account;
        int user_account_number;
        String user_account_type;
        if (Storage.Find_client_accounts(user_client.GetClient_id()).isEmpty()) {
            System.out.println("У вас нет ни одного счёта в данном банке. Необходимо создать хотя бы один.");
            System.out.println("Введите номер счёта. Может быть ошибка, если номер с таким счётом уже существует.");
            user_account_number = scan.nextInt();
            System.out.println("Введите название типа счёта. На выбор предлагаются [credit/debit/deposit]");
            user_account_type = scan.nextLine();
            user_account_type = scan.nextLine();
            user_account = Storage.Get_account_by_id(user_client.Create_account(user_account_type, user_account_number));
        } else {
            user_account_number = Storage.Find_client_accounts(user_client.GetClient_id()).get(0).account_number;
            user_account = Storage.Find(user_account_number);
        }
        System.out.println("Авторизация пройдена успешно.");

        boolean end = false;
        String x;
        int user_account_transfer;
        int user_account_number_to;
        while (!end) {
            System.out.println("Введите [0], если хотите увидеть подсказку по возможным действиям. Также вы можете ввести другие числа, для выполнения каких-либо действий из подсказки.");
            x = scan.nextLine();
            if (x.equals("0")) {
                System.out.println("Подсказка по возможным действиям:");
                System.out.println("Введите [1], если хотите завершить работу программы.");
                System.out.println("Введите [2], если хотите поменять ваш адрес/номер паспорта.");
                System.out.println("Введите [3], если хотите узнать ваши текущие человеческие данные.");
                System.out.println("Введите [4], если хотите узнать ваш текущий банк.");
                System.out.println("Введите [5], если хотите узнать ваши текущие клиентские данные.");
                System.out.println("Введите [6], если хотите узнать ваши данные текущего счёта.");
                System.out.println("Введите [7], если хотите увидеть данные обо всех счетах вашего текущего клиента.");
                System.out.println("Введите [8], если хотите установить текущий счёт по его номеру.");
                System.out.println("Введите [9], если хотите создать новый счёт.");
                System.out.println("Введите [10], если хотите снять деньги с вашего счёта (в банкомате).");
                System.out.println("Введите [11], если хотите положить деньги на ваш счёт (в банкомате).");
                System.out.println("Введите [12], если хотите перевести деньги по номеру счёта.");
                System.out.println("Введите [13], если хотите закрыть ваш текущий счёт.");
                System.out.println("Введите [14], если хотите узнать тип и статус вашего текущего счёта.");
                System.out.println("Введите [15], если хотите узнать количество денег на балансе счёта на конкретную дату.");
            }
            if (x.equals("1")) {
                end = true;
            }
            if (x.equals("2")) {
                System.out.println("Введите ваш адрес. Введите [-], если не хотите сообщать адрес (ваши возможности могут быть ограничены).");
                user_address = scan.nextLine();
                System.out.println("Введите номер вашего паспорта. Введите [-], если не хотите сообщать номер паспорта (ваши возможности могут быть ограничены).");
                user_passport = scan.nextLine();
                user_person.Change_person_info(user_address, user_passport);
                user_client = Storage.Find(user_name, user_surname, user_bank_name); // для перерасчета статуса человека
                System.out.println("Вот ваши новые данные: " + user_person);
                System.out.println("Вот данные вашего клиентского аккаунта (мог измениться статус): " + user_client);
            }
            if (x.equals("3")) {
                System.out.println("Вот ваши данные: " + user_person);
            }
            if (x.equals("4")) {
                System.out.println("Вот ваш выбранный банк: " + user_bank);
            }
            if (x.equals("5")) {
                System.out.println("Вот данные вашего клиентского аккаунта: " + user_client);
            }
            if (x.equals("6")) {
                System.out.println("Вот данные вашего клиентского счёта: " + user_account);
            }
            if (x.equals("7")) {
                System.out.println(Storage.Find_client_accounts(user_client.GetClient_id()));
            }
            if (x.equals("8")) {
                System.out.println("Введите номер вашего счёта. Информацию обо всех ваших клиентских счетах можно посмотреть с помощью одной из команд из подсказки.");
                user_account_number = scan.nextInt();
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("9")) {
                System.out.println("Введите номер счёта. Может быть ошибка, если номер с таким счётом уже существует.");
                user_account_number = scan.nextInt();
                System.out.println("Введите название типа счёта. На выбор предлагаются [credit/debit/deposit]");
                user_account_type = scan.nextLine();
                user_account_type = scan.nextLine();
                user_account = Storage.Get_account_by_id(user_client.Create_account(user_account_type, user_account_number));
            }
            if (x.equals("10")) {
                System.out.println("Введите сумму, которую вы хотите снять с вашего счёта.");
                user_account_transfer = scan.nextInt();
                user_account.Get_cash(user_account_transfer);
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("11")) {
                System.out.println("Введите сумму, которую вы хотите положить на ваш счёт.");
                user_account_transfer = scan.nextInt();
                user_account.Supplement_balance(user_account_transfer);
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("12")) {
                System.out.println("Введите номер счёта, на который вы хотите перевести деньги.");
                user_account_number_to = scan.nextInt();
                System.out.println("Введите сумму, которую вы хотите перевести.");
                user_account_transfer = scan.nextInt();
                user_account.Transfer_money(user_account_number_to, user_account_transfer);
                user_account = Storage.Find(user_account_number);
            }
            if (x.equals("13")) {
                user_account.Close_account();
                System.out.println("Ваш счёт был успешно закрыт.");
            }
            if (x.equals("14")) {
                System.out.println("Тип вашего текущего счёта: " + user_account.account_type);
                if (user_account.account_end_date.getTime() <= Storage.formater.parse(Current_date.Get_current_date()).getTime()) {
                    System.out.println("Ваш текущий счёт закрыт " + Storage.formater.format(user_account.account_end_date));
                } else {
                    System.out.println("Ваш текущий счёт открыт.");
                }
            }
            if (x.equals("15")) {
                System.out.println("Введите дату в формате dd.MM.yyyy (в стандартных условиях), на которую хотите узнать баланс текущего счёта.");
                String data = scan.nextLine();
                double balance = user_account.Get_balance(Storage.formater.parse(data));
                System.out.println("Баланс на дату: " + data + " равен: " + balance);
            }
        }
    }
}
