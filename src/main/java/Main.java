import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        System.out.println("Введите ваше имя.");
        String user_name = scan.nextLine();
        if (user_name.equals("Admin")) {
            throw new SecurityException("Вы не являетесь администратором");
        }
        System.out.println("Введите вашу фамилию.");
        String user_surname = scan.nextLine();
        System.out.println("В базе данных есть человек с данным именем и фамилией? Введите [да/нет].");
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
        }

        //System.out.println(Storage.Find_client_accounts(1));
        //System.out.println(Current_date.Get_current_date());
        //Current_date singleton = Current_date.Set_current_date("02.03.2023");
        //Current_date anotherSingleton = Current_date.Set_current_date("02.03.2022");
        //System.out.println(singleton.Get_current_date());
        //System.out.println(anotherSingleton.Get_current_date());
        //singleton.Set_current_date("01.02.2022");
        //System.out.println(singleton.Get_current_date());
        //System.out.println(anotherSingleton.Get_current_date());
        //Current_date.Set_current_date("06.04.2022");

        //System.out.println(Storage.Find("Kate", "Petrova"));
        //System.out.println(Storage.Get_person_by_id(1));

        //System.out.println(Storage.Find( "VTB"));
        //System.out.println(Storage.Get_bank_by_id(2));
        //System.out.println(Storage.Find_all_banks());

        //System.out.println(Storage.Get_client_by_id(2));
        //System.out.println("All clients of bank 3:");
        //System.out.println(Storage.Get_bank_by_id(3).Get_all_clients());
        //System.out.println(Storage.Find_all_clients());
        //System.out.println(Storage.Find("Ivan", "Ivanov","Tinkoff"));

        //System.out.println(Storage.Get_account_by_id(2));
        //System.out.println(Storage.Find(11753421));
        //System.out.println(Storage.Find_all_accounts());
        //System.out.println("All accounts of bank 2:");
        //System.out.println(Storage.Get_bank_by_id(2).Get_all_accounts());
        //System.out.println(Storage.Find(4,"xxxxxx")); // пока не добавлена проверка типа счёта
        //System.out.println(Storage.Get_account_type_by_id(4));

        //System.out.println(Storage.Get_tariff_by_id(3));
        //System.out.println(Storage.Find_all_tariffs());
        //System.out.println("All tariffs of bank 4:");
        //System.out.println(Storage.Get_bank_by_id(4).Get_all_tariffs());

        //System.out.println(Storage.Find_all_account_types());

        //System.out.println(Storage.Get_transfer_by_id(2));
        //System.out.println(Storage.Find_all_transfers());

        //System.out.println("Запись в файл:");
        //System.out.println(Storage.Save(new Bank("Gazprombank"))); //создаем новый банк
        //System.out.println(Storage.Save(new Bank(1,"Sber"))); //меняем имя банка по его id
        //System.out.println(Storage.Save(new Bank(10,"xx"))); //меняем имя банка, id которого не существует. Будет выдаваться Exception
        //System.out.println(Storage.Find_all_banks());

        //System.out.println(Storage.Save(new Tariff(2,8,60000,35,800))); //создаем новый тариф
        //System.out.println(Storage.Save(new Tariff(4,4,6,40000,60,1))); //меняем тариф по его id
        //System.out.println(Storage.Save(new Tariff(10,2,3,11111,72,300))); //меняем тариф, id которого не существует. Будет выдаваться Exception
        //System.out.println(Storage.Find_all_tariffs());

        //System.out.println(Storage.Save(new Client(4,2, Storage.formater.parse("17.02.2014")))); //создаем нового клиента
        //System.out.println(Storage.Save(new Client(4,2,1,Storage.formater.parse("24.07.2021")))); //меняем клиента по его id
        //System.out.println(Storage.Save(new Client(10,4,1,Storage.formater.parse("25.08.2017")))); //меняем клиента, id которого не существует. Будет выдаваться Exception
        //System.out.println(Storage.Find_all_clients());

        //System.out.println(Storage.Save(new Account(2,1,2,23543567,9000,Storage.formater.parse("12.01.2005"),Storage.formater.parse("30.09.2006")))); //создаем новый счёт
        //System.out.println(Storage.Save(new Account(3,2,1,2,23543568,9050,Storage.formater.parse("12.01.2010"),Storage.formater.parse("30.09.2015")))); //меняем счёт по его id
        //System.out.println(Storage.Save(new Account(100,2,1,2,23543568,9050,Storage.formater.parse("12.01.2010"),Storage.formater.parse("30.09.2015")))); //меняем счёт, id которого не существует. Будет выдаваться Exception
        //System.out.println(Storage.Find_all_accounts());

        //System.out.println(Storage.Save(new Person("Андрей","Соколов","-","-"))); //создаем нового человека
        //System.out.println(Storage.Save(new Person(3,"Елизавета","Мягкова","-","-"))); //меняем человека по его id
        //System.out.println(Storage.Save(new Person(103,"Антон","Супрун","-","-"))); //меняем человека, id которого не существует. Будет выдаваться Exception
        //System.out.println(Storage.Find_all_persons());

        //System.out.println(Storage.Get_bank_by_id(2).Add_tariff(new Tariff(2,8,67000,35,800))); // добавить новый тариф
        //System.out.println(Storage.Get_bank_by_id(2).Add_tariff(new Tariff(1,2,8,60000,35,800))); // изменить существующий тариф
        //System.out.println(Storage.Get_bank_by_id(2).Add_tariff(new Tariff(3,8,60000,35,800))); // ошибка (нужен другой банк)

        //System.out.println(Storage.Get_bank_by_id(2).Add_client(Storage.Get_person_by_id(2))); //создать клиента в банке
        //Current_date.Set_current_date("07.04.2022"); //смена текущей даты
        //Storage.Get_bank_by_id(2).Add_client(Storage.Get_person_by_id(4)); //создать клиента в банке после смены даты
        //Storage.Get_bank_by_id(1).Add_client(Storage.Get_person_by_id(2)); //ошибка (такой клиент уже есть)

        //System.out.println(Storage.Get_person_by_id(2).Create_client(2)); //создать клиента в банке
        //System.out.println(Storage.Get_person_by_id(2).Create_client(2)); //ошибка (такой клиент уже есть)

        //Storage.Get_person_by_id(4).Change_person_info("China 15","07 38 154962");
        //System.out.println(Storage.Get_person_by_id(4).toString()); // данные человека с id=4 поменялись
        //System.out.println(Storage.Get_client_by_id(1)); //клиент был Unreliable, стал Unlimited

        //System.out.println(Storage.Save(new Transfer(2,3,60000))); //создаем новый трансфер,но не проводим его
        //System.out.println(Storage.Save(new Transfer(1,-1,"Completed",4,5,1100,Storage.formater.parse("01.03.2020")))); //меняем трансфер по его id, не проводим не каких вычислений
        //System.out.println(Storage.Save(new Transfer(1000,2,3,60000))); //меняем трансфер, id которого не существует. Будет выдаваться Exception
        //System.out.println(Storage.Find_all_transfers());

        //System.out.println(Storage.Get_client_by_id(2).Create_account("deposit",6));
        //System.out.println(Storage.Get_client_by_id(2).Create_account("credit",7));
        //Storage.Get_client_by_id(2).Create_account("debit",5); //ошибка (у этого клиента уже есть такой счёт)

        //System.out.println(Transfer.Transfer_money(11111111,23548341,1100)); //Успешный перевод
        //System.out.println(Transfer.Transfer_money(12891530,23548341,120000)); //На дебетовом счёте не хватает денег для перевода
        //System.out.println(Transfer.Transfer_money(94815200,23548341,15001.0)); //На депозитном счёте не хватает денег для перевода
        //System.out.println(Transfer.Transfer_money(35987614,23548341,200000)); // перевод отменится из-за статуса

        //System.out.println(Transfer.Transfer_money(23548341,94815200,200));
        //System.out.println(Transfer.Cancel_transfer(5));
        // System.out.println(Transfer.Cancel_transfer(100)); //нет трансфреа с таким id, будет ошибка

        //System.out.println(Storage.Get_account_by_id(1).Transfer_money(12891530,500));
        //Storage.Get_account_by_id(1).Supplement_balance(700);
        //Storage.Get_account_by_id(2).Get_cash(500);

        //System.out.println(Storage.Get_account_by_id(1).Get_balance(Storage.formater.parse("10.04.2022"))); //10.04.2022 возвращался баланс на счёте с id=1
        //System.out.println(Storage.Get_account_by_id(1).Get_balance(Storage.formater.parse("22.02.2021"))); // not emplemented yet

        //   private void Skip_period(Date date) throws Exception {
        //        throw new Exception("not emplemented yet");
        //    }
    }
}
