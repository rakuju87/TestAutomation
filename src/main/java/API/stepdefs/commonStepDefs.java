
package API.stepdefs;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.Assert;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;
import org.w3c.dom.NodeList;

import static java.lang.System.*;

public class commonStepDefs {

    Response response;
    RequestSpecification request = RestAssured.given();
    String urlString;
    JSONObject requestJson;
    @Given("I want to execute service (.*) in URL (.*) with schema (.*)")
    public void givenIwantToexecuteSercive(String service, String urlString, String filePath) {
        try {
            this.urlString = urlString;
            out.println("We are going to execute service " + service);
            File nf = new File(filePath);
            InputStream is = new FileInputStream(nf);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            requestJson = new JSONObject(jsonTxt);
        }
        catch(Exception e)
        {out.println(e);}
    }

    @Then ("I remove element (.*)")
    public void removeElement(String keyRemove)
    {
        try {
            modifyFields(requestJson,keyRemove,"remove","");
            out.println(requestJson.toString());
        }catch(Exception e)
        {out.println(e);}
    }

    @Then ("I modify element (.*) with value (.*)")
    public void modifyElement(String keyRemove, String value)
    {
        try {
            modifyFields(requestJson,keyRemove,"modify",value);
            out.println(requestJson.toString());
        }catch(Exception e)
        {out.println(e);}
    }

    @When("I post a request")
    public void postRequest(){
        try {
            //add certficate
            request.trustStore("src/main/resources/serviceEngine.jks", "123456");
            // Add a header stating the Request body is a JSON
            request.log().all();
            request.header("Content-Type", "application/json");
            // Add the Json to the body of the request
            request.body(requestJson.toString());
            // Post the request and check the response
            response = request.post(urlString);
        }
        catch (Exception e)
        {}
    }

    @Then("the status code is (.*)")
    public void verify_status_code(int expStatusCode){
        int actStatusCode = response.getStatusCode();
        Assert.assertEquals(actStatusCode,expStatusCode);
    }

    @And("response status value is (.*)")
    public void verify_response_status(String ExpStatusValue){
        String ActSuccessCode = response.jsonPath().get("status").toString();
        Assert.assertEquals(ActSuccessCode,ExpStatusValue);
    }

   //remove or modify JSON request fields
    public void modifyFields(Object object, String keyRemove, String transformParameter, String value) throws JSONException {
        if (object instanceof JSONArray) {
            JSONArray array = (JSONArray) object;
            for (int i = 0; i < array.length(); ++i) modifyFields(array.get(i),keyRemove,transformParameter,value);
        } else if (object instanceof JSONObject) {
            JSONObject json = (JSONObject) object;
            JSONArray names = json.names();
            if (names == null) return;
            for (int i = 0; i < names.length(); ++i) {
                String key = names.getString(i);
                if (key.equals(keyRemove)) {
                    if (transformParameter.equals("remove"))
                        json.remove(key);
                    if (transformParameter.equals("modify"))
                        {
                            json.remove(key);
                            if(value.equals("true"))
                               json.put(key,true);
                            if(value.equals("false"))
                                json.put(key,false);
                            else
                                json.put(key,value);
                        }
                    return;
                } else {
                    modifyFields(json.get(key),keyRemove,transformParameter,value);
                }
            }
        }
    }
}
