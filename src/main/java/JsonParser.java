import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niloofar on 7/20/2016.
 */
class JsonParser {

    static ServerData parse() {

        JSONParser jsonParser = new JSONParser();
        ServerData serverData = new ServerData();

        try {
            FileReader reader = new FileReader("core.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            Integer port = Integer.parseInt(jsonObject.get("port").toString());
            System.out.println("The Port number is:" + port);

            serverData.setPort(port);

            JSONArray jsonArrayDeposits = (JSONArray) jsonObject.get("deposits");
            List<Deposit> deposits = new ArrayList<Deposit>();

            for (int i = 0; i < jsonArrayDeposits.size() ; i++) {

                Deposit deposit = new Deposit();
                System.out.println("The " + i + " element of jsonArray array " + jsonArrayDeposits.get(i));
                JSONObject innerObject = (JSONObject) jsonArrayDeposits.get(i);
                String customerName = (String) innerObject.get("customer");
                String id = innerObject.get("id").toString();
                BigDecimal initialBaance = new BigDecimal(innerObject.get("initialBalance").toString());
                BigDecimal upperBound = new BigDecimal( innerObject.get("upperBound").toString());

                deposit.setCustomerName(customerName);
                deposit.setId(id);
                deposit.setInitialBalance(initialBaance);
                deposit.setUpperBound(upperBound);
                deposits.add(deposit);
            }

            String outlog = (String) jsonObject.get("outLog");
            System.out.println("The outLog: " + outlog);

            serverData.setOutLog(outlog);
            serverData.setDeposits(deposits);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverData;
    }
}
