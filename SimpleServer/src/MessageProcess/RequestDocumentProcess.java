package MessageProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import ResourceManager.ResoucesServerManager;
import clientdao.GroupInfoDAO;

public class RequestDocumentProcess implements MessageProcess {

    private final String DEFAULTBRIDGE = "_";
    private final String REQUESTDOCUMENT = "REQUESTDOCUMENT";
    private PrintWriter writer;
    
    public RequestDocumentProcess(PrintWriter writer)
    {
        this.writer = writer;
    }
    
    public String handle(String[] components) {
        // TODO Auto-generated method stub
        String id = components[1];
        String title = components[2];
        for (File file : ResoucesServerManager.getServerStorage().listFiles()) {

            if (file.getName().contains(id) && file.getName().contains(title)) {
                transferFileToClient(file, id, title);
            }

        }
        return null;
    }

    private void transferFileToClient(File file, String id, String title) {
        // TODO Auto-generated method stub
        BufferedReader reader;
        //String content = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                
                //content += line + "\n";
                String message = REQUESTDOCUMENT + DEFAULTBRIDGE 
                        + title + DEFAULTBRIDGE + line;
                
                writer.println(message);
                writer.flush();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        finally
//        {
//        System.out.println("Begin: " + content);
//        
//        
//        
//        if(GroupInfoDAO.getGroup().get(title).getContent().toString().isEmpty())
//        {
//           GroupInfoDAO.getGroup().get(title).getContent().append(content);
//        }
//        }
    }

}
