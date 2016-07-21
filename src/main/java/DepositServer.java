/**
 * Created by Niloofar on 7/20/2016.
 */
public class DepositServer {

    private Long port;
    private String outLog;
    private Deposits deposit;

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public String getOutLog() {return outLog;}

    public void setOutLog(String outLog) {this.outLog = outLog;}

    public Deposits getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposits deposit) {
        this.deposit = deposit;
    }
}
