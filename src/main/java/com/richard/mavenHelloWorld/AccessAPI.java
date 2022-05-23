package com.richard.mavenHelloWorld;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class AccessAPI {
	
	public static void main(String[] args) throws Exception {
		String host = "https://online-movie-database.p.rapidapi.com/auto-complete";
		String charset = "UTF-8";
		String rapidApiHost = "online-movie-database.p.rapidapi.com";
		String rapidApiKey = "31cb96e8f4mshd33dc2b31a0c33bp1e8573jsn29170b3d014b";
		String s = "Kick";
		String query = String.format("q=%s",URLEncoder.encode(s,charset));
		
		HttpResponse <JsonNode> response = Unirest.get(host + "?" + query)
				.header("x-rapidapi-host",rapidApiHost)
				.header("x-rapidapi-key",rapidApiKey)
				.asJson();
		System.out.println(response.getStatus());
		System.out.println(response.getHeaders().get("Content-Type"));
		
		System.out.println("JSON Detailed response:");
		
				
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(response.getBody().toString());
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);
		
		//parse json to get single element
		// refer for JSON parsing basics and examples : 
		// https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
		// refer this for nested json array parsing
		// https://stackoverflow.com/questions/50473763/gson-how-to-parse-nested-arrays
		
		JSONObject obj = new JSONObject(prettyJsonString);
		JSONArray arr = obj.getJSONArray("d"); // JSONObject class provides this getJSONArray() method
		                                       // to retrieve the JSON array in square brackets of JSON
		
		System.out.println("array length = " + arr.length());
		
		for (int i=0;i <= (arr.length()-1);i++) {
		try {
		JSONArray innerArray = arr.getJSONObject(i).getJSONArray("v");
			for (int j=0;j<=innerArray.length();j++) {
				System.out.println(innerArray.getJSONObject(j).getString("l"));
			} }
		catch (Exception e) {e.printStackTrace();continue;}
		
		}
		
		
	}

}
