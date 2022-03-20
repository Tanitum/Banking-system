import java.io.File;
import  java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class Storage{
    private static String Person_file_name ="src/main/resources/Person.csv";
    private static String Bank_file_name ="src/main/resources/Bank.csv";
    private static String Client_file_name ="src/main/resources/Client.csv";
    private static String Account_file_name ="src/main/resources/Account.csv";
    private static String Account_type_file_name ="src/main/resources/Account_type.csv";
    private static String Tariff_file_name ="src/main/resources/Tariff.csv";
    private static String Transfer_file_name ="src/main/resources/Transfer.csv";
    private static SimpleDateFormat formater =new SimpleDateFormat("dd.MM.yyyy");

    private static List<String[]> Read_file(String object_type) throws Exception {
        String file_name="";
        switch (object_type) {
            case "Person":
                file_name=Person_file_name;
                break;
            case "Bank":
                file_name=Bank_file_name;
                break;
            case "Client":
                file_name=Client_file_name;
                break;
            case "Account":
                file_name=Account_file_name;
                break;
            case "Account_type":
                file_name=Account_type_file_name;
                break;
            case "Tariff":
                file_name=Tariff_file_name;
                break;
            case "Transfer":
                file_name=Transfer_file_name;
                break;
            default:
                break;
        }
        File f=new File(file_name);
        Scanner scan=new Scanner(f);
        List<String[]> listOfString = new ArrayList<String[]>();
        while(scan.hasNextLine()){
            String[] linesArray=scan.nextLine().split(";");
            listOfString.add(linesArray);
        }
        return listOfString;
    }

    public static Person Find(String person_name, String person_surname) throws Exception {
        List<String[]> list = Read_file("Person");
        for (String[] item : list)
        {
            if (item[1].equals(person_name) && item[2].equals(person_surname)){
                return new Person(Integer.valueOf(item[0]),item[1],item[2],item[3],item[4]);
            }
        }
        return null;
    }

    public static Person Get_person_by_id(Integer person_id) throws Exception {
        List<String[]> list = Read_file("Person");
        for (String[] item : list)
        {
            if (person_id.toString().equals(item[0])){
                return new Person(Integer.valueOf(item[0]),item[1],item[2],item[3],item[4]);
            }
        }
        return null;
    }

    public int Save(Person person) throws Exception {
        throw new Exception("not emplemented yet");
        //return person_id;
    }

    public static Client Find(String person_name,String person_surname,String bank_name) throws Exception {
        List<Client> list = Find_all_clients();
        Person p;
        for (Client item : list)
        {
            p=item.Get_person();
            if (item.Get_bank().bank_name.equals(bank_name) && p.person_name.equals(person_name) && p.person_surname.equals(person_surname)){
                return item;
            }
        }
        return null;
    }

    public static List<Client> Find_all_clients() throws Exception {
        List<String[]> list = Read_file("Client");
        List<Client> Client_list = new ArrayList<Client>();
        for (String[] item : list)
        {
            Client_list.add(new Client(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),formater.parse(item[3])));
        }
        return Client_list ;
    }

    public static Client Get_client_by_id(Integer client_id) throws Exception {
        List<String[]> list = Read_file("Client");
        for (String[] item : list)
        {
            if (client_id.toString().equals(item[0])){
                return new Client(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),formater.parse(item[3]));
            }
        }
        return null;
    }

    public int Save(Client client) throws Exception {
        throw new Exception("not emplemented yet");
        //return client_id;
    }

    public static Bank Find(String bank_name) throws Exception {
        List<String[]> list = Read_file("Bank");
        for (String[] item : list)
        {
            if (item[1].equals(bank_name)){
                return new Bank(Integer.valueOf(item[0]),item[1]);
            }
        }
        return null;
    }

    public static List<Bank> Find_all_banks() throws Exception {
        List<String[]> list = Read_file("Bank");
        List<Bank> Bank_list = new ArrayList<Bank>();
        for (String[] item : list)
        {
            Bank_list.add(new Bank(Integer.valueOf(item[0]),item[1]));
        }
        return Bank_list ;
    }

    public static Bank Get_bank_by_id(Integer bank_id) throws Exception {
        List<String[]> list = Read_file("Bank");
        for (String[] item : list)
        {
            if (bank_id.toString().equals(item[0])){
                return new Bank(Integer.valueOf(item[0]),item[1]);
            }
        }
        return null;
    }

    public int Save(Bank bank) throws Exception {
        throw new Exception("not emplemented yet");
        //return bank_id;
    }

    public static List<Account> Find(Integer client_id, String account_type) throws Exception { // пока не добавлена проверка типа счёта
        List<Account> list = Find_all_accounts();
        List<Account> Account_list = new ArrayList<Account>();
        for (Account item : list)
        {
            if (item.Get_client().client_id==client_id /*  && item.Get_account_type().account_type.equals(account_type)*/){
                Account_list.add(item);
            }
        }
        return Account_list;
    }

    public static Account Find(Integer account_number) throws Exception {
        List<String[]> list = Read_file("Account");
        for (String[] item : list)
        {
            if (account_number.toString().equals(item[4])){
                return new Account(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),Integer.valueOf(item[3]),Integer.valueOf(item[4]),Double.valueOf(item[5]),formater.parse(item[6]),formater.parse(item[7]));
            }
        }
        return null;
    }

    public static List<Account> Find_all_accounts() throws Exception {
        List<String[]> list = Read_file("Account");
        List<Account> Account_list = new ArrayList<Account>();
        for (String[] item : list)
        {
            Account_list.add(new Account(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),Integer.valueOf(item[3]),Integer.valueOf(item[4]),Double.valueOf(item[5]),formater.parse(item[6]),formater.parse(item[7])));
        }
        return Account_list;
    }

    public static Account Get_account_by_id(Integer account_id) throws  Exception {
        List<String[]> list = Read_file("Account");
        for (String[] item : list)
        {
            if (account_id.toString().equals(item[0])){
                return new Account(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),Integer.valueOf(item[3]),Integer.valueOf(item[4]),Double.valueOf(item[5]),formater.parse(item[6]),formater.parse(item[7]));
            }
        }
        return null;
    }

    public int Save(Account account) throws Exception {
        throw new Exception("not emplemented yet");
        //return account_id;
    }

    public static List<Tariff> Find_all_tariffs() throws Exception {
        List<String[]> list = Read_file("Tariff");
        List<Tariff> Tariff_list = new ArrayList<Tariff>();
        for (String[] item : list)
        {
            Tariff_list.add(new Tariff(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),Double.valueOf(item[3]),Integer.valueOf(item[4]),Double.valueOf(item[5])));
        }
        return Tariff_list;
    }

    public static Tariff Get_tariff_by_id(Integer tariff_id) throws  Exception {
        List<String[]> list = Read_file("Tariff");
        for (String[] item : list)
        {
            if (tariff_id.toString().equals(item[0])){
                return new Tariff(Integer.valueOf(item[0]),Integer.valueOf(item[1]),Integer.valueOf(item[2]),Double.valueOf(item[3]),Integer.valueOf(item[4]),Double.valueOf(item[5]));
            }
        }
        return null;
    }

    public int Save(Tariff tariff) throws Exception {
        throw new Exception("not emplemented yet");
        //return tariff_id;
    }

    public static String Get_account_type_by_id(Integer account_id) throws  Exception {
        List<Account> list = Find_all_accounts();
        List<String[]> type_list = Read_file("Account_type");
        for (Account item : list)
        {
            if (item.account_id== account_id){
                for (String[] item2 : type_list)
                {
                    if (new Integer (item.account_type_id).toString().equals(item2[0])){
                               return item2[1];
                    }
                }
            }
        }
        return null;
    }

    public static List<String> Find_all_account_types() throws Exception {
        List<String[]> list = Read_file("Account_type");
        List<String> Account_type_list = new ArrayList<String>();
        for (String[] item : list)
        {
            Account_type_list.add(item[0]+";"+item[1]);
        }
        return Account_type_list;
    }
}
