import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SProperties {

    private static Properties PROPS;

    public static String getString(String name) {
        return getInstance().getProperty(name);
    }

    public static String get(String name) {
        return getInstance().getProperty(name);
    }

    public static int getInt(String name) {
        return Integer.parseInt(getInstance().getProperty(name));
    }

    public static Properties getInstance() {
        if (PROPS == null) {
            PROPS = load(App.CONFIG_PATH);
        }
        return PROPS;
    }

    public static Properties load(String path) {
        try (InputStream input = new FileInputStream(path)) {
            PROPS = new Properties();
            PROPS.load(input);
            return PROPS;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
