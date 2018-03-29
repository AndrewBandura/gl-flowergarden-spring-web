package com.flowergarden.jsonDao;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.util.Property;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Repository
public class JsonBouquetDaoImpl implements JsonBouquetDao{

    private static final Properties properties = (new Property()).getProperties();

    @Override
    public int add(Bouquet bouquet) {

        JAXBContext jc;
        try(Writer writer = new OutputStreamWriter(new FileOutputStream(new File(properties.getProperty("json_bouquet_test"))))) {
            jc = JAXBContext.newInstance(MarriedBouquet.class);

            Configuration config = new Configuration();
            MappedNamespaceConvention con = new MappedNamespaceConvention(config);

            XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(bouquet, xmlStreamWriter);

            return bouquet.getId();

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;

    }

    @Override
    public Bouquet read() {

        JAXBContext jc;
        try {

            String txt = new String(Files.readAllBytes(Paths.get(properties.getProperty("json_bouquet_test"))));
            jc = JAXBContext.newInstance(MarriedBouquet.class);

            JSONObject obj = new JSONObject(txt);
            Configuration config = new Configuration();
            MappedNamespaceConvention con = new MappedNamespaceConvention(config);
            XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(obj, con);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            MarriedBouquet bouquet = (MarriedBouquet) unmarshaller.unmarshal(xmlStreamReader);

            return bouquet;

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
