import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maverick on 5/1/17.
 */
public class HomeClass {
    public static void main(String[] args){
        MongoHelper mongoObject = new MongoHelper();
        try{
            WebCategorizer wc = new WebCategorizer();
            ArrayList<String> ab = new ArrayList<>();
            ab.add("facebook.com");
            ab.add("orkut.com");
            ab.add("google.com");
            wc.getWebCategories(ab);
            /*
            // Get Users list from MongoDb
            mongoObject.connect();
            // Get previous day browsing data for each User
*/
            // Generate transactions from browsing data 
            HashMap<Integer, JSONObject> historyList = breakData();
/*           List<String> transactionList = new ArrayList<String>();
            for(int i : historyList.keySet()){
                JSONObject tempJSON = historyList.get(i);
                StringBuilder tempTransaction = new StringBuilder();
                for(Object key : tempJSON.keySet()){
                    if((long)tempJSON.get(key) > 30) {
                        tempTransaction.append((String)key+" ");
                    }
                }
                if(tempTransaction.toString().length() != 0)
                    transactionList.add(tempTransaction.toString());
            }
            transactionList.set(1,"newtab mongodb.github.io");

            for (String s: transactionList
                    ) {
                System.out.println(s);
            }
            // Generate rules

            GenerateRules gr = new GenerateRules();
            ArrayList<JSONObject> rules = gr.generateRules(transactionList);
            for(JSONObject rule:rules) {
                System.out.println(rule);
            }

            // Save rules in MongoDb



            mongoObject.disconnect();
            */
        }
        catch(Exception ex) {
            if(ex.getClass().getName().equals("UnknownHostException")){
                mongoObject.disconnect();
                System.out.println("Error in connecting to MongoDb");
            }
        }
    }

    private static HashMap<Integer, JSONObject> breakData(){
        //Dummy Data
        String s = "[" +
                "                {" +
                "                        \"T1\" : 1493624668716," +
                "                        \"T2\" : 1493624704594," +
                "                        \"hostname\" : \"mongodb.github.io\"," +
                "                        \"pathname\" : \"/node-mongodb-native/2.2/api/\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624711112," +
                "                        \"T2\" : 1493624765099," +
                "                        \"hostname\" : \"angular-tutorial.quora.com\"," +
                "                        \"pathname\" : \"/Make-a-Todo-Chrome-Extension-with-AngularJS-1\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624766449," +
                "                        \"T2\" : 1493624778930," +
                "                        \"hostname\" : \"angular-tutorial.quora.com\"," +
                "                        \"pathname\" : \"/Make-a-Todo-Chrome-Extension-with-AngularJS-1\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624782293," +
                "                        \"T2\" : 1493624802577," +
                "                        \"hostname\" : \"newtab\"," +
                "                        \"pathname\" : \"/\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624802578," +
                "                        \"T2\" : 1493624807890," +
                "                        \"hostname\" : \"www.google.com\"," +
                "                        \"pathname\" : \"/search\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624811631," +
                "                        \"T2\" : 1493624838607," +
                "                        \"hostname\" : \"developer.chrome.com\"," +
                "                        \"pathname\" : \"/extensions/tabs\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624838607," +
                "                        \"T2\" : 1493624884959," +
                "                        \"hostname\" : \"developer.chrome.com\"," +
                "                        \"pathname\" : \"/extensions/tabs\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624884959," +
                "                        \"T2\" : 1493624897009," +
                "                        \"hostname\" : \"newtab\"," +
                "                        \"pathname\" : \"/\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624897010," +
                "                        \"T2\" : 1493624908105," +
                "                        \"hostname\" : \"www.google.com\"," +
                "                        \"pathname\" : \"/search\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624908106," +
                "                        \"T2\" : 1493624923002," +
                "                        \"hostname\" : \"www.google.com\"," +
                "                        \"pathname\" : \"/search\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624923003," +
                "                        \"T2\" : 1493624955170," +
                "                        \"hostname\" : \"developer.chrome.com\"," +
                "                        \"pathname\" : \"/extensions/idle\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624970728," +
                "                        \"T2\" : 1493624997953," +
                "                        \"hostname\" : \"www.responsivemiracle.com\"," +
                "                        \"pathname\" : \"/best-materialize-css-templates/\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493624997953," +
                "                        \"T2\" : 1493625006126," +
                "                        \"hostname\" : \"maxartkiller.in\"," +
                "                        \"pathname\" : \"/rock-on-materialize-responsive-admin-html-template/\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493625006127," +
                "                        \"T2\" : 1493767823," +
                "                        \"hostname\" : \"maxartkiller.in\"," +
                "                        \"pathname\" : \"/website/rockon/rockon_blue/pages/index.html\"" +
                "                }," +
                "                {" +
                "                        \"T1\" : 1493531780065," +
                "                        \"T2\" : 1493532100139," +
                "                        \"hostname\" : \"newtab\"," +
                "                        \"pathname\" : \"/\"" +
                "                }" +
                "        ]";
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            JSONArray inputData = (JSONArray)obj;
            HashMap<Integer, JSONObject> historyList = new HashMap<Integer, JSONObject>();
            for(Object urlHistory : inputData){
                JSONObject historyRecord = (JSONObject)urlHistory;
                addToSlot(historyList, historyRecord);
                Timestamp T1 = new Timestamp((long) historyRecord.get("T1"));
                Timestamp T2 = new Timestamp((long) historyRecord.get("T2"));
            }
            return historyList;
        }
        catch(Exception ex) {
            System.out.println(ex.getClass().getName().toString());
            return new HashMap<Integer, JSONObject>();
        }
    }

    private static void addToSlot(HashMap<Integer, JSONObject> historyList, JSONObject historyRecord){
        long t1 = (Long) historyRecord.get("T1");
        long t2 = (Long) historyRecord.get("T2");
        Timestamp T1 = new Timestamp(t1);
        Timestamp T2 = new Timestamp(t2);
        String url = (String)historyRecord.get("hostname");
        Timestamp T3 = T1;
        while(T3.getHours() != T2.getHours()) {
        	T3 = addTransaction(url,T3,T2,historyList,historyRecord);
        }
    }
    
    private static Timestamp addTransaction(String url, Timestamp T1,  Timestamp T2, HashMap<Integer, JSONObject> historyList, JSONObject historyRecord) {
    	int key = T1.getHours();
    	Timestamp T3 = null;
    	if(T2.getHours() - T1.getHours() > 2) {
    	Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(T1.getTime());
        cal.add(Calendar.HOUR, 2);
        T3 = new Timestamp(cal.getTime().getTime());
    	} else {
    		T3 = T2;
    	}
    	long t1 = T1.getSeconds();
        long t2 = T3.getSeconds();
    	if(historyList.containsKey(key/2)){
            JSONObject tempJSON = (JSONObject) historyList.get(key/2);
            if(tempJSON.containsKey(url)){
                tempJSON.put(url, (Long)tempJSON.get(url) + (t2-t1));
            }
            else{
                tempJSON.put(url, (t2-t1));
            }
        }
        else{
            JSONObject tempJSON = new JSONObject();
            tempJSON.put(url, (t2-t1) );
            historyList.put(key/2, tempJSON);
        }
    	
    	return T3;
    }
}
