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
public class RequestDocumentInfoProcess implements MessageProcess{

    private final String DEFAULTBRIDGE = "_";
    private final String CONNECT = "&";
    private final String HEADER = "REQUESTDOCUMENTINFO";
    
    public String handle(String[] components) {
        String id = components[1];
        String message = HEADER ;
        for(DocumentData documentData : ResoucesServerManager.getDocumentListData())
        {
            if(documentData.getAuthor().equals(id))
                message += DEFAULTBRIDGE + documentData.getTitle() + CONNECT + 
                        documentData.getCreatedDate() ;
        }
        return message;
    }
    
}
