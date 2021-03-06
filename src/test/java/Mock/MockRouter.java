import java.util.HashMap;

public class MockRouter extends Router {
  private Response expectedResponse;

  public static MockRouter createMockRouter(Response expectedResponse) throws NonexistentDirectoryException {
    Handler defaultHandler = new MockHandler(expectedResponse);
    HashMap<String, Handler> routes = new HashMap<String, Handler>();
    Directory directory = new MockDirectory();
    return new MockRouter(defaultHandler, routes, directory, expectedResponse);
  }

  public MockRouter(Handler defaultHandler, HashMap<String, Handler> routes, Directory directory, Response expectedResponse) {
    super(defaultHandler, routes, directory);
    this.expectedResponse = expectedResponse;
  }
  
  @Override 
  public Response getResponse(Request request) {
    return this.expectedResponse;
  }
  
}
