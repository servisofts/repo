import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jboss.com.sun.net.httpserver.HttpExchange;

import Servisofts.SConsole;

public class Upload {
    public static void handleRequest(HttpExchange t) throws IOException {
        try {
            List<FileItem> items = getFileItems(t);
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().add("Content-type", "text/plain");

            String url_request = t.getRequestURI().getPath();
            // SConsole.log("# items", items.size());
            for (FileItem fi : items) {
                String path_and_name = "/" + url_request + "/" + fi.getFieldName();
                // remplazamos las //+ duplicadas por /
                path_and_name = path_and_name.replaceAll("/{2,}", "/");
                // ( quitamos todo despues de la ultima / )
                String path = path_and_name.replaceAll("[^/]+(?=$/|$)", "");
                // (Quitamos todo antes de la ultima / y tambien la /)
                String name = path_and_name.replaceAll(".+(?=/|/$).", "");

                SConsole.log(name, ":",
                        // "\n\t", "path=", path,
                        // "\n\t", "name=", name,
                        "\t", "size=", fi.getSize(),
                        "\t", "type=", fi.getContentType() == null ? "cmd:" + fi.getString() : fi.getContentType(),
                        "\t", "path=", path_and_name,
                        "");

                if (!allowCommand(fi, path_and_name)) {
                    // Si no es un comando guardamos
                    crearDirectorios(path);
                    guardarFile(fi, path_and_name);

                }

            }
            responseText(t, 200, "exito");
        } catch (Exception e) {
            responseText(t, 400, e.getMessage());
            e.printStackTrace();
        }
        t.close();
    }

    private static boolean allowCommand(FileItem fi, String path_and_name) throws IOException {
        if (fi.getContentType() == null) {
            String cmd = fi.getString();
            switch (cmd) {
                case "rm":
                    deleteFile(fi, path_and_name);
                    return true;
            }
        }
        return false;
    }

    private static void deleteFile(FileItem fi, String path) throws IOException {
        File f = new File(App.ROOT_FILE + "/" + path);
        if (path.equals("/")) {
            SConsole.error("[", path, "]", "No se puede elimar /");
            return;
        }
        if (f.exists()) {
            f.delete();
            SConsole.error("[", path, "]", "Eliminado");
        }
    }

    private static void guardarFile(FileItem fi, String path) throws IOException {
        File f = new File(App.ROOT_FILE + "/" + path);
        boolean exist = f.exists();
        copyInputStreamToFile(fi.getInputStream(), f);
        if (exist) {
            SConsole.warning("[", path, "]", "Remplazado");
        } else {
            SConsole.succes("[", path, "]", "Creado");
        }
    }

    private static void crearDirectorios(String path) {
        File f = new File(App.ROOT_FILE + "/" + path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    private static void responseText(HttpExchange t, int code, String text) throws IOException {
        t.sendResponseHeaders(code, text.length());
        OutputStream os = t.getResponseBody();
        os.write(text.toString().getBytes());
        os.close();
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[8192];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
        }
    }

    // [getFileItems] Convertimos la peticion en una lista de FileItems
    private static List<FileItem> getFileItems(HttpExchange t) throws Exception {

        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload up = new ServletFileUpload(d);
        List<FileItem> items = up.parseRequest(new RequestContext() {

            @Override
            public String getCharacterEncoding() {
                return "UTF-8";
            }

            @Override
            public int getContentLength() {
                return Integer.parseInt(t.getRequestHeaders().getFirst("Content-Length"));
            }

            @Override
            public String getContentType() {
                return t.getRequestHeaders().getFirst("Content-type");
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return t.getRequestBody();
            }

        });
        return items;
    }

}
