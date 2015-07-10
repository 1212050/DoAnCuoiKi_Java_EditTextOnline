package ResourceManager;
import java.io.PrintWriter;


public class ClientInfo {
	private String id;
	private PrintWriter writer;
	
	public ClientInfo(String id, PrintWriter writer)
	{
		this.id = id;
		this.writer = writer;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
}
