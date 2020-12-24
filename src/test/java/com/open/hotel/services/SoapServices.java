package com.open.hotel.services;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.config.Config;
import com.open.hotel.logger.LoggerClass;
import com.open.hotel.threadVariables.VariableManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class SoapServices {

    org.apache.log4j.Logger log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), VariableManager.getInstance().getVariables().getVar("testCaseID").toString());

    Assertions assertions = new Assertions();

    public SoapServices() {
    }

    public String getResponseFromPostMethod(String jsonRequest, String customerName) {
        String responseString = null;
        try {
            String region = Config.properties.getProperty("Environment");
            String url = Config.properties.getProperty("EndPointURL_" + region + "_" + customerName);
            log.info("end point url - " + url);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost();
            StringEntity entity = new StringEntity(jsonRequest);
            httpPost.setEntity(entity);
            if (url != null){
                String header = Config.properties.getProperty("Header_" + region + "_" + customerName);
                String[] headerValues = header.split(";");
                for(int i = 0; i < headerValues.length; ++i) {
                    String[] values = headerValues[i].split(":");
                    httpPost.setHeader(values[0],values[1]);
                }
            }
            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code :" +  response.getStatusLine().getStatusCode());
            }
            log.info("Response Status - " + response.getStatusLine().getStatusCode());
            HttpEntity entity1 = response.getEntity();
            responseString = EntityUtils.toString(entity1);
            log.info("Response  - " + responseString);

            client.close();
        }catch(Exception var1) {
            new RuntimeException("Request Not Send");
            log.info("response failed " + var1 + System.lineSeparator());
        }
        return responseString;
    }

    public Object getValueFromResponse(String xmlStr, String xpathExpression) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new java.io.StringReader(xmlStr));
            //Document doc = builder.parse(new InputSource(new StringRader(xmlStr)));
            Document doc = builder.parse(inputSource);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            return xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
        } catch (Exception val5) {
            log.info("Exception - " + val5);
        }
        return "";
    }

    public void xmlResponseAssertions(String parentNode, String childNode, String expectedVal) {
        boolean isMatch = false;
        String nodeText = null;
        NodeList result =(NodeList) this.getValueFromResponse(VariableManager.getInstance().getVariables().getVar("responseString").toString(), "//" + parentNode +  "//" + childNode);
        for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            nodeText = node.getTextContent();
            if (nodeText.equals(expectedVal)) {
                isMatch = true;
                break;
            }
        }
        if(isMatch) {
            assertions.assertValues(childNode, expectedVal, nodeText);
            //log.info("Pass::: Tag of " + reportName + " in " + childName + " matches with value as '" + expectedVal + "'");
        }else {
            log.info("FAIL::: Tag of " + parentNode + " in " + childNode + " not matched with value as '" + expectedVal + "'");
        }
    }
}
