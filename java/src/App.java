
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

import org.jboss.com.sun.net.httpserver.HttpServer;
import org.jboss.com.sun.net.httpserver.HttpContext;
import ServerHttp.ServerHttp;
import Servisofts.SConsole;

public class App {
    static final int PORT = SProperties.getInt("PORT_UPLOAD");
    static final String ROOT_FILE = SProperties.get("ROOT_FILE");

    public static void main(String[] args) {
        SConsole.warning("Inciando java server para subir archibos alrepositorio...");
        HttpServer server;
        try {
            System.setProperty("org.eclipse.jetty.util.log.class",
                    "org.eclipse.jetty.util.log.StdErrLog");
            System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
            org.eclipse.jetty.util.log.Log.getProperties().setProperty("org.eclipse.jetty.util.log.announce",
                    "false");
            org.eclipse.jetty.util.log.Log.getRootLogger().setDebugEnabled(false);
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            HttpContext uploadContext = server.createContext("/");
            uploadContext.setHandler(Upload::handleRequest);
            server.start();
            SConsole.info("PORT:", PORT);
            SConsole.info("ROOT_FILE:", ROOT_FILE);
            SConsole.succes("Server inciado con exito!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}