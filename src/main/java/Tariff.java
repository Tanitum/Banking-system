public class Tariff {
    private int tariff_id;
    public int bank_id;
    int account_percent;
    double credit_limit;
    int credit_commission;
    double status_limit;

    public int GetTariff_id() {
        return tariff_id;
    }

    public int GetBank_id() {
        return bank_id;
    }

    protected Tariff(int tariff_id, int bank_id, int account_percent, double credit_limit, int credit_commission, double status_limit) {
        this.tariff_id = tariff_id;
        this.bank_id = bank_id;
        this.account_percent = account_percent;
        this.credit_limit = credit_limit;
        this.credit_commission = credit_commission;
        this.status_limit = status_limit;
    }

    protected Tariff(int bank_id, int account_percent, double credit_limit, int credit_commission, double status_limit) {
        this(0, bank_id, account_percent, credit_limit, credit_commission, status_limit);
    }

    @Override
    public String toString() {
        return tariff_id + ";" + bank_id + ";" + account_percent + ";" + credit_limit + ";" + credit_commission + ";" + status_limit;
    }

}
