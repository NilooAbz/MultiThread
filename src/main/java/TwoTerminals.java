/**
 * Created by DotinSchool2 on 7/25/2016.
 */
public class TwoTerminals {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Terminal.main(new String[]{"terminal1.xml"});
                Terminal.main(new String[] {"terminal2.xml"});
            }
        }).start();

        //Terminal.main(new String[] {"terminal2.xml"});
    }


}
