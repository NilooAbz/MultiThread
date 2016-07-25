import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Niloofar on 7/20/2016.
 */
class Transaction implements Serializable{

    private String transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private String deposit;


    String getTransactionId() {
        return transactionId;
    }

    void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    String getTransactionType() {
        return transactionType;
    }

    void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    String getDeposit() {
        return deposit;
    }

    void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
