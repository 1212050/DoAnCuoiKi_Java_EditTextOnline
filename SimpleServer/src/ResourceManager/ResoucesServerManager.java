package ResourceManager;

import clientdao.ClientDataDAO;
import clientdao.DocumentDataDAO;
import clientdao.GroupInfoDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.TextEditorServer;
import pojo.ClientData;
import pojo.DocumentData;

public class ResoucesServerManager {

    private TextEditorServer textEditorServer;

    private final String DEFAULTPATH = "../ServerStorage";
    private final static String TAILFILE = ".txt";
    private File serverStorage;

    private static ResoucesServerManager uniqueInstance = new ResoucesServerManager();

    private ArrayList<ClientData> clientListData;
    private ArrayList<DocumentData> documentListData;

    private ArrayList<ClientInfo> clientListInfo;

    private ResoucesServerManager() {

        serverStorage = new File(DEFAULTPATH);
        if (!serverStorage.exists()) {
            serverStorage.mkdirs();
        }
        clientListData = new ArrayList<ClientData>();
        clientListData = ClientDataDAO.getClientDataFromDB();

        documentListData = new ArrayList<DocumentData>();
        documentListData = DocumentDataDAO.getDocumentDataFromDB();

        clientListInfo = new ArrayList<ClientInfo>();
    }

    public static ResoucesServerManager getInstance() {
        return uniqueInstance;
    }

    public static File getServerStorage() {
        return getInstance().serverStorage;
    }

    public static boolean isAvailableClient(ClientData data) {
        for (ClientData clientData : getInstance().clientListData) {
            if (clientData.compareData(data)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<DocumentData> getDocumentListData() {
        return getInstance().documentListData;
    }

    public static void addToDocumentListData(DocumentData documentData) {
        getInstance().documentListData.add(documentData);
        DocumentDataDAO.addDocumentDataToDB(documentData.getAuthor(),
                documentData.getTitle(), documentData.getCreatedDate());

        String newPath = getInstance().serverStorage.getAbsolutePath()
                + "/" + documentData.getAuthor() + "_" + documentData.getTitle() + TAILFILE;
        GroupInfoDAO.addToGroup(documentData.getTitle(), documentData.getAuthor());
        File newDoc = new File(newPath);
        try {
            newDoc.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ResoucesServerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addToDocumentListData(DocumentData documentData, String fromId) {
        getInstance().documentListData.add(documentData);
        DocumentDataDAO.addDocumentDataToDB(documentData.getAuthor(),
                documentData.getTitle(), documentData.getCreatedDate());

        String newPath = getInstance().serverStorage.getAbsolutePath()
                + "/" + documentData.getAuthor() + "_" + documentData.getTitle() + TAILFILE;
        String fromPath = getInstance().serverStorage.getAbsolutePath()
                + "/" + fromId + "_" + documentData.getTitle() + TAILFILE;
        GroupInfoDAO.addToGroup(documentData.getTitle(), documentData.getAuthor());
        File newDoc = new File(newPath);
        try {
            newDoc.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ResoucesServerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        copyContent(newPath, fromPath);
        ClientInfo client = getClientInfoFromId(documentData.getAuthor());
        String message = "REQUESTDOCUMENTINFO" + "_" + documentData.getTitle() + "&"
                + documentData.getCreatedDate();
        client.getWriter().println(message);
        client.getWriter().flush();
    }

    public static void addContentToGroupFile(String title) {
        ArrayList<String> listId = GroupInfoDAO.getGroup().get(title).getListId();
        String content = GroupInfoDAO.getGroup().get(title).getContent().toString();
        for (String id : listId) {
            String path = getInstance().serverStorage.getAbsolutePath() + "/"
                    + id + "_" + title + TAILFILE;
            createFileWithContent(path, content);

        }
    }

    private static void createFileWithContent(String path, String content) {
        String[] lines = content.split("\n");
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            for (String line : lines) {
                writer.println(line);
                writer.flush();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ResoucesServerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ClientData getClientData(String id) {
        for (ClientData clientData : getInstance().clientListData) {
            if (clientData.getId().equals(id)) {
                return clientData;
            }
        }
        return null;
    }

    public static void addClientDataToServer(String id, String pass) {
        getInstance().clientListData.add(new ClientData(id, pass));
        ClientDataDAO.addClientDataToDB(id, pass);
    }

    public static boolean isAvailableId(String id) {
        for (ClientData clientData : getInstance().clientListData) {
            if (clientData.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static TextEditorServer getTextEditorServer() {
        return getInstance().textEditorServer;
    }

    public static void setTextEditorServer(TextEditorServer textEditorServer) {
        getInstance().textEditorServer = textEditorServer;
    }

    public static void addToListClientInfo(ClientInfo clientInfo) {
        getInstance().clientListInfo.add(clientInfo);
    }

    public static void removeClientInfoFromList(String id) {
        ClientInfo clientInfo = ResoucesServerManager.getClientInfoFromId(id);
        getInstance().clientListInfo.remove(clientInfo);
        ResoucesServerManager.getClientData(id).isUsing = false;
    }

    public static ArrayList<ClientInfo> getListClientInfo() {
        return getInstance().clientListInfo;
    }

    public static ClientInfo getClientInfoFromId(String id) {
        for (ClientInfo client : getInstance().getListClientInfo()) {
            if (client.getId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    public static void sendMessageToGroupClient(String message, String fromId, String titleOfDoc) {
        // TODO Auto-generated method stub
        GroupInfo groupInfo = GroupInfoDAO.getGroup().get(titleOfDoc);
        for (String id : groupInfo.getListId()) {
            if (!id.equals(fromId)) {
                PrintWriter writer = getWriterOfId(id);
                if (writer != null) {
                    writer.println(message);
                    writer.flush();
                }
            }
        }
    }

    private static PrintWriter getWriterOfId(String id) {
        for (ClientInfo client : ResoucesServerManager.getListClientInfo()) {
            if (client.getId().equals(id)) {
                return client.getWriter();

            }
        }
        return null;
    }

    private static void copyContent(String newPath, String fromPath) {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {

            File file1 = new File(fromPath);
            File file2 = new File(newPath);

            inStream = new FileInputStream(file1);
            outStream = new FileOutputStream(file2); // for override file content
            //outStream = new FileOutputStream(file2,<strong>true</strong>); // for append file content

            byte[] buffer = new byte[1024];

            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
