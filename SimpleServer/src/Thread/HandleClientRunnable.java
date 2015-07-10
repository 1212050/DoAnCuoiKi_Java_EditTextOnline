package Thread;

import MessageProcess.ChangeProcess;
import MessageProcess.CreateDocumentProcess;
import MessageProcess.EndEditProcess;
import MessageProcess.ExitProcess;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import MessageProcess.LoginMessageProcess;
import MessageProcess.MessageProcess;
import MessageProcess.NoMessageProcess;
import MessageProcess.ReadDocumentProcess;
import MessageProcess.RemoveProcess;
import MessageProcess.RequestDocumentInfoProcess;
import MessageProcess.RequestDocumentProcess;
import MessageProcess.ShareProcess;
import MessageProcess.SignUpMessageProcess;
import MessageProcess.TypedMessageProcess;
import ResourceManager.ResoucesServerManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandleClientRunnable implements Runnable {

    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;

    private void closeSocket() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HandleClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public enum HEADER {

        LOGIN, SIGNUP, TYPED, REMOVE, EXIT,
        REQUESTDOCUMENT, REQUESTDOCUMENTINFO,
        CREATEDOCUMENT, READDOCUMENT, LOGOUT, SHARE, CHANGE, ENDEDIT
    };
    private final String DEFAULTSTRING = "_";

    public HandleClientRunnable(Socket socket) {

        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
		// TODO Auto-generated method stub

        String message;

        try {
            while ((message = reader.readLine()) != null) {
                handleMessage(message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleMessage(String message) {
        // TODO Auto-generated method stub
        String[] components = message.split(DEFAULTSTRING);
        MessageProcess messageProcess = new NoMessageProcess();
        HEADER header = HEADER.values()[Integer.parseInt(components[0])];

        switch (header) {
            case LOGIN:
                messageProcess = new LoginMessageProcess(writer);
                break;
            case SIGNUP:
                messageProcess = new SignUpMessageProcess();
                break;
            case TYPED:
                messageProcess = new TypedMessageProcess();
                break;
            case REMOVE:
                messageProcess = new RemoveProcess();
                break;
            case EXIT:
                if(components.length == 2)
                {
                    System.out.println(components[1] + ": out");
                    messageProcess = new ExitProcess();
                    messageProcess.handle(components);
                }
                closeSocket();               
                return;
            case REQUESTDOCUMENT:
                messageProcess = new RequestDocumentProcess(writer);
                break;
            case REQUESTDOCUMENTINFO:
                messageProcess = new RequestDocumentInfoProcess();
                break;
            case CREATEDOCUMENT:
                messageProcess = new CreateDocumentProcess();
                break;
            case READDOCUMENT:
                messageProcess = new ReadDocumentProcess(writer);
                break;
            case LOGOUT:
                System.out.println(components[1] + ": out");
                messageProcess = new ExitProcess();
                break;
            case SHARE:
                messageProcess = new ShareProcess();
                break;
            case CHANGE:
                messageProcess = new ChangeProcess();
                break;
            case ENDEDIT:
                messageProcess = new EndEditProcess();
                break;
        }
        String returnToClientMessage = messageProcess.handle(components);
        writer.println(returnToClientMessage);
        writer.flush();
    }

}
