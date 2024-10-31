package config;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    public static final Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("Petstore.properties"));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load SwagLabs.properties");
        }
    }
}
