import java.util.List;

/**
 * Created by DotinSchool2 on 7/24/2016.
 */
public class ServerData {

    private Integer port;
    private String outLog;
    private List<Deposit> deposits;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getOutLog() {
        return outLog;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
}
