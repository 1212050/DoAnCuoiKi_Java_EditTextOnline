/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientdao;

import ResourceManager.ResoucesServerManager;
import dao.util.MySqlDataAccessHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.DocumentData;

/**
 *
 * @author Admin
 */
public class DocumentDataDAO {

    public static ArrayList<DocumentData> getDocumentDataFromDB() {
        ArrayList<DocumentData> documentListData = new ArrayList<DocumentData>();
        try {
            MySqlDataAccessHelper helper = new MySqlDataAccessHelper();
            helper.open("serverdata");
            String sql = "SELECT * FROM DOCUMENTDATA";
            ResultSet rs = helper.executeQuery(sql);
            while (rs.next()) {
                DocumentData documentData = new DocumentData();
                String id = rs.getString("author");
                documentData.setAuthor(id);
                String title = rs.getString("title");
                documentData.setTitle(title);
                documentData.setCreatedDate(rs.getString("createddate"));
                documentListData.add(documentData);
                GroupInfoDAO.addToGroup(title, id);
            }
            helper.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return documentListData;
    }

    public static void addDocumentDataToDB(String author, String title, String createdDate) {
        try {
            MySqlDataAccessHelper helper = new MySqlDataAccessHelper();
            helper.open("serverdata");
            String sql = "INSERT INTO `serverdata`.`documentdata` (`author`, `title`, `createddate`) "
                    + "VALUES ('" + author + "', '" + title + "', '" + createdDate + "');";

            helper.executeUpdate(sql);
            helper.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
