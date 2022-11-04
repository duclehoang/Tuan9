package BaiNop2;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.sound.sampled.AudioFileFormat;

;

public class ServerControoler {
    public DatagramSocket datagramSocket;
    public DatagramPacket datagramPacketRecevice, datagramPacketRecevice1, datagramPacketSend, datagramPacketSend1;
    private int buffSize = 8192;
    private int port = 1234;
    private StringTokenizer tokenizer;
    private Connection connect;

    public void Controller_UDPServer() {
        try {
            String kq=" ",kq1=" ",kq2=" ",kq3=" ",check="",check1="",check2="";
            datagramSocket = new DatagramSocket(1234);
            datagramPacketRecevice = new DatagramPacket(new byte[buffSize], buffSize);
            datagramPacketRecevice1 = new DatagramPacket(new byte[buffSize], buffSize);
           // JSONParser jsonParser = new JSONParser();
            while (true) {

                datagramSocket.receive(datagramPacketRecevice);
                datagramSocket.receive(datagramPacketRecevice1);
                String tmp = new String(datagramPacketRecevice.getData(), 0, datagramPacketRecevice.getLength());
                String tmp2 = new String(datagramPacketRecevice1.getData(), 0, datagramPacketRecevice1.getLength());
//                    System.out.println("Server recived: " + tmp + "from " + datagramPacketRecevice.getAddress().getHostAddress()
//                            + "at port " + datagramSocket.getLocalPort());


                switch (tmp2) {
                    case "1":
                        kq = " ";
                        while (!tmp.toUpperCase().equals("HELLO")) {
                            String error = "Please check your input Syntax !";
                            datagramPacketSend = new DatagramPacket(error.getBytes(), error.getBytes().length, datagramPacketRecevice.getAddress(), datagramPacketRecevice.getPort());

                            datagramSocket.send(datagramPacketSend);

                        }


                        String api = "http://api.airvisual.com/v2/countries?key=4a904b50-0833-4294-9451-4218845d299d";
                      //  System.out.println(api + "1 nè");
                        Document doc = Jsoup.connect(api).method(Connection.Method.GET).ignoreContentType(true).execute().parse();
                        JSONObject jsonObject = new JSONObject(doc.text());
                       // JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                        //   JSONObject jsonObject1=new JSONObject(jsonArray.toJSONObject(jsonArray));
                        //    System.out.println(jsonArray.get(1));
                        Iterator<String> it = jsonObject.keys();
                        while (it.hasNext()) {
                            String keys = it.next();
                            if (keys.equals("status")) {
                                continue;

                            } else {
                                JSONObject innerJson = new JSONObject(jsonObject.toString());


                                JSONArray innerArray = innerJson.getJSONArray(keys);
                                for (int i = 0; i < innerArray.length(); i++) {
                                    JSONObject innInnerObj = innerArray.getJSONObject(i);
                                    Iterator<String> InnerIterator = innInnerObj.keys();
                                    while (InnerIterator.hasNext()) {
                                        tmp = (String) innInnerObj.get(InnerIterator.next());
                                        kq = kq + "\n" + tmp;


                                    }
                                }


                            }
                        }
                        break;
                    case "2":
                        try {


                            kq = " ";
//
                            api = "http://api.airvisual.com/v2/states?country=" + tmp + "&key=4a904b50-0833-4294-9451-4218845d299d";
                            System.out.println(api);
                            connect = Jsoup.connect(api);


                            doc = connect.method(Connection.Method.GET).ignoreContentType(true).execute().parse();

                            jsonObject = new JSONObject(doc.text());
                          //  jsonArray = (JSONArray) jsonObject.get("data");
                            //   JSONObject jsonObject1=new JSONObject(jsonArray.toJSONObject(jsonArray));
                            //    System.out.println(jsonArray.get(1));
                            it = jsonObject.keys();
                            while (it.hasNext()) {
                                String keys = it.next();
                                if (keys.equals("status")) {
                                    continue;

                                } else {
                                    JSONObject innerJson = new JSONObject(jsonObject.toString());


                                    JSONArray innerArray = innerJson.getJSONArray(keys);
                                    for (int i = 0; i < innerArray.length(); i++) {
                                        JSONObject innInnerObj = innerArray.getJSONObject(i);
                                        Iterator<String> InnerIterator = innInnerObj.keys();
                                        while (InnerIterator.hasNext()) {
                                            tmp = (String) innInnerObj.get(InnerIterator.next());
                                            kq = kq + "\n" + tmp;


                                        }
                                    }


                                }
                            }
                            break;

                        } catch (HttpStatusException e) {
                            kq = " ";
                            kq = "please check your input syntax";

                            System.out.println(kq);

                            break;

                        }
                    case "3":
                        kq = " ";
                            try{

                                tokenizer = new StringTokenizer(tmp, ";");
                                check = tokenizer.nextToken();
                                check1 = tokenizer.nextToken();

                            }catch (NoSuchElementException e){
                                kq = " ";
                                kq = "please check your input syntax";
                                System.out.println(kq);

                                break;
                            }


                        try {
                            api = "https://api.airvisual.com/v2/cities?state=" + check1.trim() + "&country=" + check + "&key=4a904b50-0833-4294-9451-4218845d299d";
                            System.out.println(api);
                            doc = Jsoup.connect(api).method(Connection.Method.GET).ignoreContentType(true).execute().parse();
                            jsonObject = new JSONObject(doc.text());
                            //jsonArray = (JSONArray) jsonObject.get("data");
                            //   JSONObject jsonObject1=new JSONObject(jsonArray.toJSONObject(jsonArray));
                            //    System.out.println(jsonArray.get(1));
                            it = jsonObject.keys();
                            while (it.hasNext()) {
                                String keys = it.next();
                                if (keys.equals("status")) {
                                    continue;

                                } else {
                                    JSONObject innerJson = new JSONObject(jsonObject.toString());


                                    JSONArray innerArray = innerJson.getJSONArray(keys);
                                    for (int i = 0; i < innerArray.length(); i++) {
                                        JSONObject innInnerObj = innerArray.getJSONObject(i);
                                        Iterator<String> InnerIterator = innInnerObj.keys();
                                        while (InnerIterator.hasNext()) {
                                            tmp = (String) innInnerObj.get(InnerIterator.next());
                                            kq = kq + "\n" + tmp;


                                        }
                                    }


                                }
                            }
                            break;
                        } catch (HttpStatusException e) {
                            kq = " ";
                            kq = "please check your input syntax";
                            System.out.println(kq);

                            break;


                        }


                    case "4":
                        kq = " ";
                            try {



                                tokenizer = new StringTokenizer(tmp, ";");
                                check = tokenizer.nextToken();
                                check1 = tokenizer.nextToken();
                                check2 = tokenizer.nextToken();
                            }
                            catch (NoSuchElementException e){
                                kq = " ";
                                kq = "please check your input syntax";
                                System.out.println(kq);

                                break;

                            }
                        try {
                            api = "http://api.airvisual.com/v2/city?city=" + check2 + "&state=" + check1 + "&country=" + check + "&key=4a904b50-0833-4294-9451-4218845d299d";
                            System.out.println(api);
                            doc = Jsoup.connect(api).method(Connection.Method.GET).ignoreContentType(true).execute().parse();
                            jsonObject = new JSONObject(doc.text());
                            JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                            JSONObject jsonObjectLocation = jsonObjectData.getJSONObject("location");
                            JSONArray jsonArrayCoordinates = jsonObjectLocation.getJSONArray("coordinates");
                            JSONObject jsonObjectCurent = jsonObjectData.getJSONObject("current");
                            JSONObject jsonObjectPoluttion = jsonObjectCurent.getJSONObject("pollution");
                            JSONObject jsonObjectWeather = jsonObjectCurent.getJSONObject("weather");

                            String kqData = "---**Information area**--" + "\n" + "\tCity: " + jsonObjectData.get("city") +
                                    "\n\tState: " + jsonObjectData.get("state") + "\n\tCountry: " + jsonObjectData.get("country");
                            String kqLocation = "\n---Location area---\n" + "\tType: " + jsonObjectLocation.get("type");
                            String kqCoordinates = "\n---Coordinates---\n" + "\t0: " + jsonArrayCoordinates.get(0) + "\n" + "\t0: " + jsonArrayCoordinates.get(1);
                            String kqPollution = "\n---Pollution---\n" + "\tts: " + jsonObjectPoluttion.get("ts") + "\n\taqius: " + jsonObjectPoluttion.get("aqius") +
                                    "\n\tMainus: " + jsonObjectPoluttion.get("mainus") +
                                    "\n\tAqicn: " + jsonObjectPoluttion.get("aqicn") +
                                    "\n\tMaincn: " + jsonObjectPoluttion.get("maincn");
                            String kqWeather = "\n---Weather Area---\n" + "\tTs: " + jsonObjectWeather.get("ts") +
                                    "\n\tTp: " + jsonObjectWeather.get("tp") + "\n\tPr: " + jsonObjectWeather.get("pr") +
                                    "\n\tHu: " + jsonObjectWeather.get("hu") + "\n\tWs: " + jsonObjectWeather.get("ws") +
                                    "\n\tWd: " + jsonObjectWeather.get("wd") + "\n\tIc: " + jsonObjectWeather.get("ic");


//                        System.out.println("---**Information area**---");
//                        System.out.println("\tCity: "+jsonObjectData.get("city"));
//                        System.out.println("\tState: "+jsonObjectData.get("state"));
//                        System.out.println("\tCountry: "+jsonObjectData.get("country"));
//                        JSONObject jsonObjectLocation=jsonObjectData.getJSONObject("location");
//                       JSONArray jsonArrayCoordinates=jsonObjectLocation.getJSONArray("coordinates");
//                        System.out.println("\t---Laction area---");
//                      //  System.out.println(jsonObject2);
//                        System.out.println("\t\tType: "+jsonObjectLocation.get("type"));
//                        System.out.println("\t\tcoordinates: ");
//                        System.out.println("\t\t\t0: "+jsonArrayCoordinates.get(0));
//                        System.out.println("\t\t\t1: "+jsonArrayCoordinates.get(1));
//
//                        JSONObject jsonObjectCurent=jsonObjectData.getJSONObject("current");
//                        JSONObject jsonObjectPoluttion=jsonObjectCurent.getJSONObject("pollution");
//                        System.out.println("\t---Curent Area---");
//                        System.out.println("\t\t-Pollution Area-");
//                        System.out.println("\t\t\tts: "+jsonObjectPoluttion.get("ts"));
//                        System.out.println("\t\t\taqius: "+jsonObjectPoluttion.get("aqius"));
//                        System.out.println("\t\t\tmainus: "+jsonObjectPoluttion.get("mainus"));
//                        System.out.println("\t\t\taqicn: "+jsonObjectPoluttion.get("aqicn"));
//                        System.out.println("\t\t\tmaincn: "+jsonObjectPoluttion.get("maincn"));
//
//                        JSONObject jsonObjectWeather=jsonObjectCurent.getJSONObject("weather");
//                        System.out.println("\t\t-Weather Area-");
//                        System.out.println("\t\t\tts: "+jsonObjectWeather.get("ts"));
//                        System.out.println("\t\t\ttp: "+jsonObjectWeather.get("tp"));
//                        System.out.println("\t\t\tpr: "+jsonObjectWeather.get("pr"));
//                        System.out.println("\t\t\thu: "+jsonObjectWeather.get("hu"));
//                        System.out.println("\t\t\tws: "+jsonObjectWeather.get("ws"));
//                        System.out.println("\t\t\twd: "+jsonObjectWeather.get("wd"));
//                        System.out.println("\t\t\tic: "+jsonObjectWeather.get("ic"));


                            kq = kqData + kqLocation + kqCoordinates + kqPollution + kqWeather + "\n";


                            break;


                        }catch (HttpStatusException e){
                            kq = " ";
                            kq = "please check your input syntax";
                            System.out.println(kq);

                            break;

                        }
                }


               // System.out.println(kq);
                datagramPacketSend = new DatagramPacket(kq.getBytes(), kq.getBytes().length, datagramPacketRecevice.getAddress(), datagramPacketRecevice.getPort());
                // datagramPacketSend1 = new DatagramPacket(tmp2.getBytes(), tmp2.getBytes().length, datagramPacketRecevice1.getAddress(), datagramPacketRecevice1.getPort());
                // System.out.println("Server sent back : " + kq + " " + "to client");
                datagramSocket.send(datagramPacketSend);

                        // datagramSocket.send(datagramPacketSend1);
                }




            } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }



    }
}
