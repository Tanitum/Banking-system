import Exceptions.ObjectAlreadyExistsException;

import java.util.List;

public class Person {
    private int person_id;
    String person_name;
    String person_surname;
    String person_address;
    String person_passport;

    public int GetPerson_id() {
        return person_id;
    }

    public Person(String person_name, String person_surname) {
        this(0, person_name, person_surname, "-", "-");
    }

    protected Person(int person_id, String person_name, String person_surname) {
        this(person_id, person_name, person_surname, "-", "-");
    }

    protected Person(int person_id, String person_name, String person_surname, String person_address, String person_passport) {
        this.person_name = person_name;
        this.person_surname = person_surname;
        this.person_id = person_id;
        this.person_address = person_address;
        this.person_passport = person_passport;
    }

    protected Person(String person_name, String person_surname, String person_address, String person_passport) {
        this(0, person_name, person_surname, person_address, person_passport);
    }

    @Override
    public String toString() {
        return person_id + ";" + person_name + ";" + person_surname + ";" + person_address + ";" + person_passport;
    }

    public void Change_person_info(String person_address, String person_passport) throws Exception {
        this.person_address = person_address;
        this.person_passport = person_passport;
        Storage.Save(new Person(person_id, person_name, person_surname, person_address, person_passport));
    }

    public int Create_client(int bank_id) throws Exception {
        List<Client> Clients = Storage.Find_all_clients();
        for (Client item : Clients) {
            if (item.person_id == person_id && item.bank_id == bank_id) {
                throw new ObjectAlreadyExistsException(Storage.Find_at_glossary("EXCEPTION_PERSON_ALREADY_CLIENT") + item.client_id);
            }
        }
        return Storage.Save(new Client(person_id, bank_id, Storage.formater.parse(Current_date.Get_current_date())));
    }

}
