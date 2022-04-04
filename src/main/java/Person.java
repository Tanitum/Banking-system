public class Person {
    private int person_id;
    String person_name;
    String person_surname;
    String person_address;
    String person_passport;

    public int GetPerson_id ()
    {
        return person_id;
    }

    public Person (String person_name, String person_surname){
        this.person_id=0;
        this.person_name=person_name;
        this.person_surname=person_surname;
    }

    protected Person (int person_id, String person_name, String person_surname){
        this ( person_id, person_name,  person_surname, "-",  "-");
    }

    protected Person (int person_id, String person_name, String person_surname,String person_address, String person_passport){
        this.person_name=person_name;
        this.person_surname=person_surname;
        this.person_id=person_id;
        this.person_address=person_address;
        this.person_passport=person_passport;
    }

    protected Person (String person_name, String person_surname,String person_address, String person_passport){
        this.person_name=person_name;
        this.person_surname=person_surname;
        this.person_id=0;
        this.person_address=person_address;
        this.person_passport=person_passport;
    }

    @Override
    public String toString(){
        return person_id+ ";"+person_name+ ";"+ person_surname+ ";"+person_address+ ";"+ person_passport;
    }

    public void  Change_person_info(String person_address, String person_passport) throws Exception {
        throw new Exception("not emplemented yet");
    }

    public int Create_client(int bank_id) throws Exception {
        throw new Exception("not emplemented yet");
        //return 2;
    }

}
