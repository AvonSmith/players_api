package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FrameworkProperties {
    private InputStream inputStream;

    public String getProperty(String key){
        String result = "";
        try {
            Properties properties = new Properties();
            String propFileName = "framework.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                properties.load(inputStream);
            }else {
                throw new FileNotFoundException("The properties file has not been found");
            }

            result = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
