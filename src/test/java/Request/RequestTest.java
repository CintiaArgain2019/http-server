import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RequestTest {
  private Request request = new Request.Builder()
                                       .method("GET")
                                       .uri("/uri/path")
                                       .version("1.1")
                                       .build();
  
  @Test 
  public void getsMethod() {
    assertEquals("GET", request.getMethod());
  }

  @Test 
  public void getsURI() {
    assertEquals("/uri/path", request.getURI());
  }

  @Test 
  public void getsHTTPVersion() {
    assertEquals("1.1", request.getHTTPVersion());
  }

  @Test 
  public void getsRequestLineWithCarriageReturnAndNewLine() {
    assertEquals("GET /uri/path HTTP/1.1\r\n", request.getRequestLine());
  }

  @Test 
  public void getsStringifiedRequestWithCarriageReturnsAndNewLines() {
    assertEquals("GET /uri/path HTTP/1.1\r\n" +
                 "\r\n", request.toString());
  }
  
}
