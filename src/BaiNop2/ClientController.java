package BaiNop2;

import java.io.IOException;
import java.lang.invoke.StringConcatException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Scanner;

public class ClientController {
    public int destPort=1234;
    public String hostName="localhost";
    private DatagramPacket datagramSocketRecevice,datagramSocketSend,datagramSocketSend1;
    private DatagramSocket datagramSocket;
    private InetAddress add;
    private Scanner stdin;
    private String tmp=" ";
    private byte[]data;
    private byte[]data2;
    public void UDPClientController() throws IOException {
        add=InetAddress.getByName(hostName);
        datagramSocket=new DatagramSocket();
        stdin=new Scanner(System.in);
        while (true){
           String swValue =" ";
            System.out.println(ConsoleColors.YELLOW_BOLD+"|                       MENU SELECTION                                  |");
            System.out.println(ConsoleColors.YELLOW_BOLD+"| Options:                                                              |");
            System.out.println(ConsoleColors.YELLOW_BOLD+"|        1. Show list country               ( use Hello )               |");
            System.out.println(ConsoleColors.YELLOW_BOLD+"|        2. Show list state of country      ( use Country )             |");
            System.out.println(ConsoleColors.YELLOW_BOLD+"|        3. Show list city of state         ( use Country;State )       |");
            System.out.println(ConsoleColors.YELLOW_BOLD+"|        4. Show AQI of state               (use Country;State;City )   |");
            System.out.println(ConsoleColors.YELLOW_BOLD+"|        5. Exit                            ( use bye )                 |");

            System.out.print(ConsoleColors.BLUE_BOLD+"Please enter your chose number: ");
            try {
                swValue = stdin.nextLine();
                int x=  Integer.parseInt(swValue);

            }
            catch(Exception e) {
                System.err.print("\nPlease enter a Number, Not String\n");
                continue;
            }
            System.out.println(ConsoleColors.BLUE_BOLD+ "Enter command: ");
            tmp = stdin.nextLine();








                if (tmp.equals("bye")){
                    System.out.println(ConsoleColors.WHITE_BOLD+"Client socket closed");
                    stdin.close();
                    datagramSocket.close();
                    break;
                }

                data = tmp.getBytes();
                data2 = swValue.getBytes();
                datagramSocketSend = new DatagramPacket(data, data.length, add, destPort);
                datagramSocketSend1 = new DatagramPacket(data2, data2.length, add, destPort);
                datagramSocket.send(datagramSocketSend);
                datagramSocket.send(datagramSocketSend1);
                datagramSocketRecevice = new DatagramPacket(new byte[8192], 8192);
                datagramSocket.receive(datagramSocketRecevice);

                tmp = new String(datagramSocketRecevice.getData(), 0, datagramSocketRecevice.getLength());

                System.out.println(ConsoleColors.WHITE_BOLD+"\n----Result----  " + "\n\t"+tmp);
                tmp = " ";
            }












    }


}
