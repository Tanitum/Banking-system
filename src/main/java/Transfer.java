import java.util.Date;

public class Transfer {
    private int transfer_id;
    private int original_transfer_id;
    enum Transfer_status{
        Completed,
        Canceled,
        Rejected,
        In_progress
    }
    Transfer_status transfer_status;
    Account account_from;
    Account account_to;
    double transfer_size;
    Date transfer_date = new Date();

    public int GetTransfer_id ()
    {
        return transfer_id;
    }
    public int GetOriginalTransfer_id ()
    {
        return original_transfer_id;
    }
    public Transfer_status GetTransfer_status()
    {
        return transfer_status;
    }

    protected Transfer (int transfer_id,int account_from_id, int account_to_id, double transfer_size) throws Exception {
        this.transfer_id=transfer_id;
        this.original_transfer_id=-1;
        this.transfer_status=Transfer_status.In_progress;
        this.account_from=Storage.Get_account_by_id(account_from_id);
        this.account_to=Storage.Get_account_by_id(account_to_id);
        this.transfer_size=transfer_size;
        this.transfer_date=Storage.formater.parse(Current_date.Get_current_date());
    }

    protected Transfer (int transfer_id,int original_transfer_id,String transfer_status, int account_from_id, int account_to_id, double transfer_size,Date transfer_date) throws Exception {
        this.transfer_id=transfer_id;
        this.original_transfer_id=original_transfer_id;
        this.transfer_status=Transfer_status.valueOf(transfer_status);
        this.account_from=Storage.Get_account_by_id(account_from_id);
        this.account_to=Storage.Get_account_by_id(account_to_id);
        this.transfer_size=transfer_size;
        this.transfer_date=transfer_date;
    }

    protected Transfer (int account_from_id, int account_to_id, double transfer_size) throws Exception {
        this.transfer_id=0;
        this.original_transfer_id=-1;
        this.transfer_status=Transfer_status.In_progress;
        this.account_from=Storage.Get_account_by_id(account_from_id);
        this.account_to=Storage.Get_account_by_id(account_to_id);
        this.transfer_size=transfer_size;
        this.transfer_date=Storage.formater.parse(Current_date.Get_current_date());
    }

    @Override
    public String toString(){
        return transfer_id+ ";"+original_transfer_id+ ";"+transfer_status+ ";"+account_from.GetAccount_id()+ ";"+account_to.GetAccount_id()+ ";"+transfer_size+ ";"+Storage.formater.format(transfer_date);
    }


    public static int Transfer_money(int account_number_from, int account_number_to, double transfer_size) throws Exception {
        throw new Exception("not emplemented yet");
        //   return 5;
    }

    protected static Transfer_status  Cancel_transfer() throws Exception {
        throw new Exception("not emplemented yet");
       // return transfer_status;
    }

}
