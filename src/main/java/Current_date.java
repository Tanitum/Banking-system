public final class Current_date {
    private static Current_date instance;
    private String date;

    private Current_date(String date) {
        this.date = date;
    }

    public static Current_date Set_current_date(String date) throws  Exception{
        if (instance==null){
            instance = new Current_date(date);
        }
        instance.date=date;
        return instance;
    }

    public static String Get_current_date() throws  Exception{
        if (instance==null){
            instance = new Current_date(Storage.formater.format(System.currentTimeMillis()));
        }
        return instance.date;
    }
}
