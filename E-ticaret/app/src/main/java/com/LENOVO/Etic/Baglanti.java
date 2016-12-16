package com.LENOVO.Etic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by LENOVO on 10.12.2016.
 */
public class Baglanti {
    public ArrayList getTitleFromXml(String adres) // XML yapısındaki başlıkları alacaz.

    {

        ArrayList list=new ArrayList(); // oluşturduğumuz list değişkenine XML dosyasındaki bütün başlıkları ekliyecez.

        try {

            URL url=new URL(adres); // parametre olarak aldığımız adresten URL tipinde bir nesne oluşturuyoruz.

//artık xml içindeki bilgileri anlamlandırmak ve parçalamak için gerekli işlemleri yapıyoruz.
            DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder=dFactory.newDocumentBuilder();

            Document document=dBuilder.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();

            NodeList nodeListCountry=document.getElementsByTagName(“trtgf”); // xml yapısındaki her bir item i bir düğüm olarak kullanıyoruz.
            for (int i = 0; i < nodeListCountry.getLength(); i++) {
                Node node=nodeListCountry.item(i);
                Element elementMain=(Element) node;

                NodeList nodeListText=elementMain.getElementsByTagName(“title”); // o düğümdeki title bilgisini alıyoruz.
                Element elementText=(Element) nodeListText.item(0);

                list.add(elementText.getChildNodes().item(0).getNodeValue()); //title bilgisin listemize ekliyoruz.
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list; }
}
