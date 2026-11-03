import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
public class Main {
  public static void main(String[] args) throws Exception {
    var server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);
    server.createContext("/", exchange -> {
      String path = exchange.getRequestURI().getPath();
      int status = path.equals("/") || path.equals("/health") ? 200 : 404;
      String text = path.equals("/health") ? "ok\n" : path.equals("/") ? "Hola mundo desde Java\n" : "No encontrado\n";
      byte[] body = text.getBytes(StandardCharsets.UTF_8);
      exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
      exchange.sendResponseHeaders(status, body.length);
      try (var out = exchange.getResponseBody()) { out.write(body); }
    });
    server.start();
    System.out.println("Java escuchando en 8080");
  }
}
