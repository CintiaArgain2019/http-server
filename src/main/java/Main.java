import java.util.HashMap;
 
public class Main {
  private final static int DEFAULT_PORT_NUMBER = 8888;
  private final static String AUTH_ROUTE = "/api/authenticate";
  private static CLIParser parser;
  private static Directory directory;
 
  public static void main(String[] args) {
    try {
      parser = new CLIParser(args);

      directory = extractDirectory();
      Handler defaultHandler = new NotFoundHandler(directory);
 
      int port = parser.getPortNumberOrDefault(DEFAULT_PORT_NUMBER);
      Router router = setUpRouter(defaultHandler);
      Middleware middleware = new MiddlewareConfig().getMiddlewareChain();
      
      Server server = new Server(port, router, middleware);
      server.start();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
      System.err.println("You must provide an argument for each flag.");
    } catch (LoggerException e) {
      System.err.println(e.getMessage());
    } catch (MissingFlagException e) {
      System.err.println(e.getMessage());
      System.err.println("Valid store flags are: " + parser.printValidFlags());
    } catch (NonexistentDirectoryException e) {
      System.err.println(e.getMessage());
      System.err.println("Please enter a valid directory from which to serve content.");
    } catch (NumberFormatException e) {
      System.err.println("Port must be a number.");
    } catch (UnsupportedFlagException e) {
      System.err.println(e.getMessage());
      System.err.println("Valid flags are: " + parser.printValidFlags());
    }
  } 

  private static Directory extractDirectory() throws MissingFlagException, NonexistentDirectoryException {
    String portFlag = parser.getStoreFlag();
    switch(portFlag) {
      case "-dir" :
        String publicDirectoryPath = parser.getDirectory();
        directory = new Directory(publicDirectoryPath);
    } 

    return directory;
  }

  private static Router setUpRouter(Handler defaultHandler) {
    HashMap<String, Handler> routesMap = createRoutesMap();
    return new Router(defaultHandler, routesMap, directory);
  }
 
  private static HashMap<String, Handler> createRoutesMap() {
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    
    String rootPath = System.getProperty("user.dir");
    routes.put("/*", new ServerHandler(HttpMethod.SUPPORTED_METHODS));
    routes.put("/", new DirectoryHandler(directory));
    routes.put("/echo", new EchoHandler());
    routes.put(AUTH_ROUTE, new AuthHandler());
    routes.put("/api/form", new FormHandler(directory));
    routes.put("/api/people", new PeopleHandler(directory));
    routes.put("/api/query", new QueryHandler(new UrlDecoder()));
    return routes;
  }

}
