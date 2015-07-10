/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceManager;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class GroupInfo {
    private ArrayList <String> listId;
    private StringBuilder content;
    public GroupInfo(String id)
    {
        listId = new ArrayList<String>();
        content = new StringBuilder();
        listId.add(id);
    }
    
    public GroupInfo() {
        listId = new ArrayList<String>();
    }
    
    public void addToListId(String id)
    {
        listId.add(id);
    }
    
    public boolean isExistedInListId(String id)
    {
        for(String info : listId)
        {
            if(info.equals(id))
                return true;
        }
        return false;
    }
    public ArrayList<String> getListId()
    {
        return listId;
    }
    
    public StringBuilder getContent()
    {
        return content;
    }
//    public void setContent(String content)
//    {
//        this.content = content;
//    }
}
