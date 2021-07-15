package geniobits.dashboard.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MembersModel {
//same getter setter not auto generated
    

    private SimpleStringProperty memberId=new SimpleStringProperty();
    private SimpleStringProperty memberNum= new SimpleStringProperty();
    private SimpleStringProperty fullName=new SimpleStringProperty();
    private SimpleStringProperty regDate=new SimpleStringProperty();
    private SimpleStringProperty contactNum=new SimpleStringProperty();
    private SimpleStringProperty email=new SimpleStringProperty();

    
   
    public String getMemberId() {
        return memberId.get();
    }

    public void setMemberId(String memberId) {
        this.memberId = new SimpleStringProperty(memberId);
    }

    public String getMemberNum() {
        return memberNum.get();
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = new SimpleStringProperty(memberNum);
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
        this.fullName = new SimpleStringProperty(fullName);
    }

    public String getRegDate() {
        return regDate.get();
    }

    public void setRegDate(String regDate) {
        this.regDate = new SimpleStringProperty(regDate);
    }

    public String getContactNum() {
        return contactNum.get();
    }

    public void setContactNum(String contactNum) {
        this.contactNum = new SimpleStringProperty(contactNum);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

   
}