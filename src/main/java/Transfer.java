public class Transfer {
    private int transfer_id;
    private int original_transfer_id;
    enum Transfer_status{
        Completed,
        Canceled,
        Rejected
    }
    Transfer_status transfer_status;
    Account account_from;
    Account account_to;
    double transfer_size;

    public int  Transfer_money(int account_number_from, int account_number_to, double transfer_size) throws Exception {
        throw new Exception("not emplemented yet");
        //   return 5;
    }

    protected Transfer_status  Cancel_transfer() throws Exception {
        throw new Exception("not emplemented yet");
       // return transfer_status;
    }

}
