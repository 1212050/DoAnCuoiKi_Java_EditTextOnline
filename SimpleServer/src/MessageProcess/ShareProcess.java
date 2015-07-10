/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageProcess;

import ResourceManager.ResoucesServerManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import pojo.DocumentData;

/**
 *
 * @author Admin
 */
public class ShareProcess implements MessageProcess {

    private final String WRONGID = "Wrong ID";
    private final String SUCCESS = "SENDSUCCESS";

    public String handle(String[] components) {
        String fromID = components[1];
        String toID = components[2];
        String title = components[3];
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String createdDate = dateFormat.format(date);

        if (fromID.equals(toID)) {
            return WRONGID;
        }
        if (!ResoucesServerManager.isAvailableId(toID)) {
            return WRONGID;
        }
        //xu ly sau
        ResoucesServerManager.addToDocumentListData(new DocumentData(toID, title, createdDate),fromID);
        return SUCCESS;
    }

}
