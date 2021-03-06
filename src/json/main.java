/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class main {
 static String sURL = "http://portal.chmi.cz/files/portal/docs/uoco/web_generator/aqindex_cze.json";
    /**
     * @param args the command line arguments
     */
    public  static void main(String[] args) {
        // TODO code application logic here
        String result = GET(sURL);
        main Main = new main();
        //Main.onPostExecute(result);
        
        Scanner sc = new Scanner(System.in, "Windows-1250");
        Test test = new Test();
        
        databaze d = new databaze();
        test.setDatabaze(d);
        test.maine(result,"Stations");
        String volba = "";
        String stanice = "";
        String region = "";
        // hlavní cyklus
        while (!volba.equals("5")) {
                //diar.vypisUvodniObrazovku();
                System.out.println();
                System.out.println("Vyberte si akci:");
                System.out.println("1 - vypis vsechny regiony");
                System.out.println("2 - vypis vsechnyStanice");
                System.out.println("22 - vypis Stanice dle regionu");
                System.out.println("3 - zadej stanici");
                System.out.println("4 - zadej region");
                System.out.println("r - refresh");
                System.out.println("5 - Konec");
                volba = sc.nextLine();
                System.out.println();
                // reakce na volbu
                switch (volba) {
                        case "1":
                                d.vypisRegiony();
                                break;
                        case "2":
                                d.vypisStanice();
                                break;
                        case "22":
                                System.out.println("napis kod regionu:");
                                stanice = sc.nextLine();
                                System.out.println();
                                d.vypisStanice(stanice);
                                break;        
                        case "3":
                                System.out.println("napis kod stanice:");
                                stanice = sc.nextLine();
                                System.out.println();
                                d.vypisStanici(stanice);  
                                break;
                        case "4":
                                System.out.println("napis kod regionu:");
                                region = sc.nextLine();
                                System.out.println();
                                d.vypisRegion(region);  
                                break;        
                        case "r":
                                System.out.println("refresh..");
                                test.setDatabaze(d);
                                result = GET(sURL);
                                test.maine(result,"Stations");
                                System.out.println(d.vypisInfo());
                                break;
                        case "5":
                                System.out.println("Libovolnou klávesou ukončíte program...");
                                break;                              
                        default:
                                System.out.println("Neplatná volba, stiskněte libovolnou klávesu a opakujte volbu.");
                                break;
                }
        }

       // test.maine(result,"Regions");
        
       // ArrayList<Region> regiony = d.vypisRegiony();
    }
    public void onPostExecute(String result) {

           /* StringBuilder text = parse(result,"Legend","Description");
            text.append(parse(result,"Legend","Color"));
            text.append(parse(result,"States","Name"));
            text.append(parse(result,"States","Regions"));
            System.out.println(text);
            */
            //return true;
    }
    
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = HttpClientBuilder.create().build();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
           e.printStackTrace();
        }

        return result;
    }
        
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream,"UTF-8"));
        String line = "";
        String result = "";
		StringBuilder sb = new StringBuilder();

        while((line = bufferedReader.readLine()) != null)
            sb.append( line + "\n" );

        String sJson = null;
		sJson = sb.toString ();
		inputStream.close();


        
        return sJson;

    }
    
    public StringBuilder parse ( String result, String objekt, String item) {
        JSONObject jsonObject;
        List<String> allNames = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        //sb.append(objekt + " - " + item + "\n");
        sb.append("{\"" + item + "\":");
        try
        {

            jsonObject = new JSONObject(result);

            JSONArray cast = jsonObject.getJSONArray(objekt);


            for (int i=0; i<cast.length(); i++) {

                JSONObject index = cast.getJSONObject(i);

                if ( index.getJSONArray("Regions")instanceof JSONArray ) {
                    sb.append( i + " - array\n" );
                }
                else  {
					result = index.getString(item);
                    sb.append( result + "\n"  );
                }


            }
            sb.append(  "}"  ); // konec json
        }catch (JSONException e) {
                e.printStackTrace();
            }
        return sb;
        }
    
    // TEST
    
}
