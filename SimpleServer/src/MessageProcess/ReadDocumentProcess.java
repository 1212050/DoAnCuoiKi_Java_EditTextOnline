/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageProcess;

import ResourceManager.ResoucesServerManager;
import clientdao.GroupInfoDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Admin
 */
public class ReadDocumentProcess implements MessageProcess {

    private final String DEFAULTBRIDGE = "_";
    private final String READDOCUMENT = "READDOCUMENT";
    private final String AND = "AND";
    private final String ENDREAD = "ENDREAD";
    private PrintWriter writer;

    public ReadDocumentProcess(PrintWriter writer) {
        this.writer = writer;
    }

    public String handle(String[] components) {
        // TODO Auto-generated method stub
        String id = components[1];
        String title = components[2];
        for (File file : ResoucesServerManager.getServerStorage().listFiles()) {

            if (file.getName().contains(id) && file.getName().contains(title)) {
                transferFileToClient(file, title);
            }

        }
        return "";
    }

    private void transferFileToClient(File file, String title) {
        // TODO Auto-generated method stub
        BufferedReader reader;
        String content = "";
        if (GroupInfoDAO.getGroup().get(title).getContent().toString().isEmpty()) {
            try {
                reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {

                    content += line + "\n";
                }
                reader.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                GroupInfoDAO.getGroup().get(title).getContent().append(content);
            }
        }
        sendContentToClient(title);
    }

    private void sendContentToClient(String title) {
        String[] content = GroupInfoDAO.getGroup().get(title).getContent().toString().split("\n");
        int length = content.length;
        String message;
        //System.out.println("String: " + GroupInfoDAO.getGroup().get(title).getContent().toString());
        for(int i = 0; i < length; ++i)
        {
            if(i == length - 1)
                message = READDOCUMENT + DEFAULTBRIDGE + content[i] + DEFAULTBRIDGE  + ENDREAD;
            else  
                message = READDOCUMENT + DEFAULTBRIDGE + content[i] + DEFAULTBRIDGE + AND;
            writer.println(message);
            writer.flush();
        }
    }
}
