import  java.util.*;
import java.io.File;
public class Main {
    public static void main(String[] args) throws  Exception{
        //System.out.println(Current_date.Get_current_date());
        //Current_date singleton = Current_date.Set_current_date("02.03.2023");
        //Current_date anotherSingleton = Current_date.Set_current_date("02.03.2022");
        //System.out.println(singleton.Get_current_date());
        //System.out.println(anotherSingleton.Get_current_date());
        //singleton.Set_current_date("01.02.2022");
        //System.out.println(singleton.Get_current_date());
        //System.out.println(anotherSingleton.Get_current_date());
        //Current_date.Set_current_date("06.04.2022");

        System.out.println(Storage.Find("Kate", "Petrova"));
        System.out.println(Storage.Get_person_by_id(1));

        System.out.println(Storage.Find( "VTB"));
        System.out.println(Storage.Get_bank_by_id(2));
        System.out.println(Storage.Find_all_banks());

        System.out.println(Storage.Get_client_by_id(2));
        System.out.println("All clients of bank 3:");
        System.out.println(Storage.Get_bank_by_id(3).Get_all_clients());
        System.out.println(Storage.Find_all_clients());
        System.out.println(Storage.Find("Ivan", "Ivanov","Tinkoff"));

        System.out.println(Storage.Get_account_by_id(2));
        System.out.println(Storage.Find(11753421));
        System.out.println(Storage.Find_all_accounts());
        System.out.println("All accounts of bank 2:");
        System.out.println(Storage.Get_bank_by_id(2).Get_all_accounts());
        System.out.println(Storage.Find(4,"xxxxxx")); // пока не добавлена проверка типа счёта
        System.out.println(Storage.Get_account_type_by_id(4));

        System.out.println(Storage.Get_tariff_by_id(3));
        System.out.println(Storage.Find_all_tariffs());
        System.out.println("All tariffs of bank 4:");
        System.out.println(Storage.Get_bank_by_id(4).Get_all_tariffs());

        System.out.println(Storage.Find_all_account_types());

        System.out.println(Storage.Get_transfer_by_id(2));
        System.out.println(Storage.Find_all_transfers());

        System.out.println("Запись в файл:");
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

        System.out.println(Storage.Save(new Transfer(2,3,60000))); //создаем новый трансфер,но не проводим его
        System.out.println(Storage.Save(new Transfer(1,-1,"Completed",4,5,1100,Storage.formater.parse("01.03.2020")))); //меняем трансфер по его id, не проводим не каких вычислений
        System.out.println(Storage.Save(new Transfer(1000,2,3,60000))); //меняем трансфер, id которого не существует. Будет выдаваться Exception
        System.out.println(Storage.Find_all_transfers());

     //   private void Skip_period(Date date) throws Exception {
    //        throw new Exception("not emplemented yet");
    //    }
    }
}
