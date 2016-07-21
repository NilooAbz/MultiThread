import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class JsonParser {

    public JsonParser() {

        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();

        try {
            FileReader reader = new FileReader("core.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            Long port = (Long) jsonObject.get("port");
            System.out.println("The Port number is:" + port);

            Deposits deposit = new Deposits();
            DepositServer depositServer = new DepositServer();

            depositServer.setPort(port);
            //System.out.println("kkk" + depositServer.getPort());

            JSONArray deposits = (JSONArray) jsonObject.get("deposits");

            for (int i = 0; i < deposits.size() ; i++) {
                System.out.println("The " + i + " element of deposits array " + deposits.get(i));
                JSONObject innerObject = new JSONObject();
                String customerName = (String) innerObject.get("customer");
                Integer id = (Integer) innerObject.get("id");
                BigDecimal initialBaance = (BigDecimal) innerObject.get("initialBalance");
                BigDecimal upperBound = (BigDecimal) innerObject.get("upperBound");

                deposit.setCustomerName(customerName);
                deposit.setId(id);
                deposit.setInitialBalance(initialBaance);
                deposit.setUpperBound(upperBound);
            }

            String outlog = (String) jsonObject.get("outLog");
            System.out.println("The outLog: " + outlog);

            depositServer.setOutLog(outlog);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
