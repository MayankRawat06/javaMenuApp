package utility;


import dto.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;
public class DataSerializationHelper {
    private static final Logger LOGGER = Logger.getLogger(DataSerializationHelper.class.getName());
    private static final String DIRECTORY_PATH = "src/resources/";
    public static void serializeData(ArrayList<Product> data, String filename) {
        String filePath = DIRECTORY_PATH + filename;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            LOGGER.info("Data serialized successfully: " + filename);
        } catch (IOException e) {
            LOGGER.severe("Failed to serialize data: " + filename);
            e.printStackTrace();
        }
    }

    public static ArrayList<Product> deserializeData(String filename) {
        String filePath = DIRECTORY_PATH + filename;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object data = ois.readObject();
            ArrayList<Product> products = (ArrayList<Product>)data;
            LOGGER.info("Data deserialized successfully: " + filename);
            return products;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.severe("Failed to deserialize data: " + filename);
            return null;
        }
    }
}

