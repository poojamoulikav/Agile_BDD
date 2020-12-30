package com.open.hotel.services;

import com.open.hotel.assertions.Assertions;
import com.open.hotel.config.Config;
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

    Assertions assertions = new Assertions();

    org.apache.log4j.Logger log = com.open.hotel.logger.LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), VariableManager.getInstance().getVariables().getVar("testCaseID").toString());

    public SoapServices() {
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
            new RuntimeException("not able to read xml");
        }
        return "";
    }

    public String xmlResponseAssertions(String response, String parentNode, String childNode, String expectedVal) {
        boolean isMatch = false;
        String nodeText = null;
        NodeList result =(NodeList) this.getValueFromResponse(response, "//" + parentNode +  "//" + childNode);
        for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            nodeText = node.getTextContent();
            if (nodeText.equals(expectedVal)) {
                isMatch = true;
                break;
            }
        }
        if(!isMatch) {
            log.info("FAIL:::"+ childNode + ": node name not available in xml");
            new RuntimeException(childNode + ": node name not available in xml");
        }
        return nodeText;
    }

    public String sendSoapPostRequest(String request, String customer) {
        String responseString = null;
        try {
            String region = Config.properties.getProperty("Environment");
            String endPintURL = Config.properties.getProperty("EndPointURL_" + region + "_" + customer);
            log.info("end point url - " + endPintURL);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(endPintURL);
            StringEntity entity = new StringEntity(request);
            httpPost.setEntity(entity);
            if (endPintURL != null){
                String header = Config.properties.getProperty("Header_" + region + "_" + customer);
                //logger.info("end point url - " + header);
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
            HttpEntity entity1 = response.getEntity();
            responseString = EntityUtils.toString(entity1);
            log.info("XML Response  - " + responseString);
            client.close();
        }catch(Exception var1) {
            log.info("response failed " + var1 + System.lineSeparator());
            throw new RuntimeException("not able to send request");
        }
        return responseString;
    }

}