import  java.util.*;
import java.io.File;
public class Main {
    public static void main(String[] args) throws  Exception{

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

     //   private void Skip_period(Date date) throws Exception {
    //        throw new Exception("not emplemented yet");
    //    }
    }
}
