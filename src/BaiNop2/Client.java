package BaiNop2;

import java.io.IOException;

public class Client {
    public static void main(String args[]) throws IOException {
        ClientController clientController=new ClientController();
        clientController.UDPClientController();
    }
}
