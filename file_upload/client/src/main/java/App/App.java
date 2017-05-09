/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;


public class App {
     
    public static void main(String[] args) throws IOException {               
        File inFile = new File("C:\\Data\\LogTest.java");       
	FileInputStream fis = new FileInputStream(inFile);
	DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
	HttpPost httppost = new HttpPost("http://localhost:8080/rest_j2ee/rest/files/upload");
	MultipartEntity entity = new MultipartEntity();	
        entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
	httppost.setEntity(entity);
	HttpResponse response = httpclient.execute(httppost);			
	int statusCode = response.getStatusLine().getStatusCode();
	HttpEntity responseEntity = response.getEntity();
	String responseString = EntityUtils.toString(responseEntity, "UTF-8");			
	System.out.println("[" + statusCode + "] " + responseString);       
    }
}