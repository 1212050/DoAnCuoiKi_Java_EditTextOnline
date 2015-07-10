/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageProcess;

import ResourceManager.ResoucesServerManager;
import pojo.DocumentData;

/**
 *
 * @author Admin
 */
public class CreateDocumentProcess implements MessageProcess{

    public String handle(String[] components) {
        String author = components[1];
        String title = components[2];
        String createdDate = components[3];
        
        ResoucesServerManager.addToDocumentListData(new DocumentData(author, title, createdDate));
        return null;
    }
    
}
