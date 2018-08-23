import java.io.File;
import java.io.UnsupportedEncodingException;

public class DirectoryHandler implements Handler {
  private Response response;
  private Directory directory;

  private String subdirectoryUri;
  
  public DirectoryHandler(Directory directory) {
    this.directory = directory;
    this.subdirectoryUri = "";
  }

  public DirectoryHandler(Directory directory, String subdirectoryUri) {
    this.directory = directory;
    this.subdirectoryUri = subdirectoryUri; 
  }
  
  public Response generateResponse(Request request) {
    String method = request.getMethod();

    switch (method) {
      case "HEAD": 
        return buildHeadResponse();
      case "GET": 
        return buildGetResponse();
      default: 
        return new Response.Builder(HttpStatusCode.METHOD_NOT_ALLOWED)
                           .build();
    }
  }

  private Response buildHeadResponse() {
    String messageBody = createMessageBody();
    int contentLength = MessageHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(MessageHeader.CONTENT_TYPE, "text/html")
                       .build(); 
  }

  private Response buildGetResponse() {
    String messageBody = createMessageBody();
    int contentLength = MessageHeader.determineContentLength(messageBody);
    return new Response.Builder(HttpStatusCode.OK)
                       .setHeader(MessageHeader.CONTENT_LENGTH, contentLength)
                       .setHeader(MessageHeader.CONTENT_TYPE, "text/html")
                       .messageBody(messageBody)
                       .build(); 
  }

  private String createMessageBody() {
    String htmlResponse = "";
    String[] fileNames = this.directory.listContent();
    for (String fileName : fileNames) {
      htmlResponse += String.format(
        "<a href=\"%s/%s\">%s</a><br>",this.subdirectoryUri, fileName, fileName);
    } 

    return htmlResponse;
  }

}