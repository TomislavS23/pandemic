package dev.pandemic.utilities;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.nio.file.Paths;

public class JAXBUtils {
    private static final String FILEPATH = "src/main/resources/output";

    private JAXBUtils() {
    }

    public static void save(Object object, String filename) throws JAXBException {
        var fullFilePath = Paths.get(FILEPATH, filename).toUri();

        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(fullFilePath));
    }

    public static Object load(Class clazz, String filename) throws JAXBException {
        var fullFilePath = Paths.get(FILEPATH, filename).toUri();

        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new File(fullFilePath));
    }
}
