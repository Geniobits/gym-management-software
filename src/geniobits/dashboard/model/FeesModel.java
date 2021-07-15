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
public class FeesModel {
      private SimpleStringProperty id=new SimpleStringProperty();
    private SimpleStringProperty gymPlan= new SimpleStringProperty();
    private SimpleStringProperty months=new SimpleStringProperty();
    private SimpleStringProperty amount=new SimpleStringProperty();
   
    
   
    public String getId() {
        return id.get();
    }

    public void setId(String memberId) {
        this.id = new SimpleStringProperty(memberId);
    }

    public String getGymPlan() {
        return gymPlan.get();
    }

    public void setGymPlan(String memberNum) {
        this.gymPlan = new SimpleStringProperty(memberNum);
    }

    public String getMonths() {
        return months.get();
    }

    public void setMonths(String fullName) {
        this.months = new SimpleStringProperty(fullName);
    }

    public String getAmount() {
        return amount.get();
    }

    public void setAmount(String regDate) {
        this.amount = new SimpleStringProperty(regDate);
    }

   
}
