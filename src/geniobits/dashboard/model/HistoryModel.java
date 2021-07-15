/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geniobits.dashboard.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author DESTINY
 */
public class HistoryModel {
      private SimpleStringProperty id=new SimpleStringProperty();
    private SimpleStringProperty time= new SimpleStringProperty();
    private SimpleStringProperty task=new SimpleStringProperty();
   
    
   
    public String getId() {
        return id.get();
    }

    public void setId(String memberId) {
        this.id = new SimpleStringProperty(memberId);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String memberNum) {
        this.time = new SimpleStringProperty(memberNum);
    }

    public String getTask() {
        return task.get();
    }

    public void setTask(String fullName) {
        this.task = new SimpleStringProperty(fullName);
    }

   

   
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
