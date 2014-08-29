package firsthttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author DHDC DAVID COMMIT TEST
 */
public class FirstHtttpServer
{

    static int port = 8080;
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws Exception
    {
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            ip = args[0];
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/welcome", new RequestHandler());
        server.createContext("/headers", new RequestHeaders());
        server.createContext("/pages/", new RequestFile());
        server.createContext("/parameters", new RequestParameters());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server started, listening on port: " + port);
    }

    static class RequestHandler implements HttpHandler
    {

        @Override
        public void handle(HttpExchange he) throws IOException
        {
            String response = "welcome to my very first almost home made Web Server :-)";

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
            sb.append("<h2>welcome to my very first almost home made Web Server :-)</h2>\n");
            sb.append("</body>\n");
            sb.append("</html>\n");
            response = sb.toString();
            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }
        }

    }

    static class RequestHeaders implements HttpHandler
    {

        @Override
        public void handle(HttpExchange he) throws IOException
        {
            Headers headers = he.getRequestHeaders();
            String response = "";
            StringBuilder sb = new StringBuilder();
            

            sb.append("<!DOCTYPE html>");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>My fancy Web Site</title>");
            sb.append("<meta charset='UTF-8'>");
            sb.append("</head>");
            sb.append("<body>");
            sb.append("<table border='1'>");
            sb.append("<tr>");
            sb.append("<th>Header</th><th>Values</th>");
            sb.append("</tr>");
            for(Map.Entry<String,List<String>> entry : headers.entrySet()){
                sb.append("<tr>");
                sb.append("<td>");
                sb.append(entry.getKey());
                sb.append("</td>");
                sb.append("<td>");
                for (String value : entry.getValue()){
                    sb.append(value);
                    sb.append("<br>");
                }
                sb.append("</td>");
                sb.append("</tr>");
            }
            sb.append("</tr>");
            sb.append("</tr>");
            sb.append("</table>");
            sb.append("</body>");
            sb.append("</html>");
            response = sb.toString();

            Headers h = he.getResponseHeaders();
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }

        }
    }
    static class RequestFile implements HttpHandler
    {

        @Override
        public void handle(HttpExchange he) throws IOException
        {
            String contentFolder = "public/";
            String response = "";
            File file = new File(contentFolder+"index.html");
            byte [] bytesToSend = new byte[(int) file.length()];
            try{
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);
            } catch (IOException ie){
                ie.printStackTrace();
            }
            
            he.sendResponseHeaders(200, response.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(bytesToSend, 0, bytesToSend.length);
            }
        }

    }
    
    static class RequestParameters implements HttpHandler{
        
        @Override
        public void handle(HttpExchange he) throws IOException {
            String parameters = "";
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<head>\n");
            sb.append("<title>My fancy Web Site</title>\n");
            sb.append("<meta charset='UTF-8'>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");
           String method= he.getRequestMethod();
           String method2 = method.toUpperCase();
           if(method2 == "GET"){
               parameters = he.getRequestURI().getQuery();
           }
            sb.append("<h2>Method is: " + method + " </h2>\n");
            if(!parameters.isEmpty()){
            sb.append("<h2>Get parameters is: " + parameters + "<h2\n");
            }
            sb.append("</body>\n");
            sb.append("</html>\n");
           String response = sb.toString();
            Headers h = he.getResponseHeaders();
            
            h.add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length());
            try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                pw.print(response); //What happens if we use a println instead of print --> Explain
            }

            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }
}
