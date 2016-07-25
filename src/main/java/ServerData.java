import java.util.List;

/**
 * Created by DotinSchool2 on 7/24/2016.
 */
class ServerData {

    private Integer port;
    private String outLog;
    private List<Deposit> deposits;

    Integer getPort() {
        return port;
    }

    void setPort(Integer port) {
        this.port = port;
    }

    public String getOutLog() {return outLog;}

    void setOutLog(String outLog) {
        this.outLog = outLog;
    }

    List<Deposit> getDeposits() {
        return deposits;
    }

    void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
}
