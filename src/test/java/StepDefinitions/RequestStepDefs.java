import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public class RequestStepDefs {
  private final static int PORT = Integer.parseInt(System.getProperty("PORT"));
  
  private World  world;

  public RequestStepDefs(World world) { 
    this.world = world;
  }

  @When("^a client makes a ([A-Z]+) request to (.+)$")
  public void a_client_makes_a_HEAD_request_to(String method, String uri) throws Throwable {
    this.world.requestUri = uri;
    String urlString = String.format("http://localhost:%d%s", PORT, uri);
    URL url = new URL(urlString);
    this.world.con = (HttpURLConnection) url.openConnection();
    this.world.con.setRequestMethod(method);
  }
  
}