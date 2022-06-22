/**
 *
 *  @author Perliński Bartłomiej S22713
 *
 */

package zad1;



import java.util.List;


public class ClientTask implements Runnable{

    private Client client;
    private List<String> reqs;
    private boolean showSendRes;

    private ClientTask(Client client, List<String> reqs, boolean showSendRes) {
        this.client = client;
        this.reqs = reqs;
        this.showSendRes = showSendRes;
    }

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes){
        return new ClientTask(c,reqs,showSendRes);
    }

    String get(){
        return this.client.getClientLog();
    }

    @Override
    public void run() {
        this.client.connect();
//        System.out.println(this.client.send("login " + this.client.getId()));
//        for (String req : reqs) {
//            System.out.println(this.client.send(req));
//        }
    }
}
