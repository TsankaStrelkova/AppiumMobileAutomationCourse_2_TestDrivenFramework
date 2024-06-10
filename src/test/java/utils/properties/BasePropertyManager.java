package utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

abstract class BasePropertyManager {

    protected final Properties properties = new Properties();

    protected void loadProperties(String propsFileName) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(propsFileName);
            properties.load(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
