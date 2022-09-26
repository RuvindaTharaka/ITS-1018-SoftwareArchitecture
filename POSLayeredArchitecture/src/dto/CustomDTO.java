package dto;

public class CustomDTO {
    private String cusID;
    private String cusTitle;
    private String cusName;
    private String cusAddress;
    private String cusContact;

    public CustomDTO(String cusID, String cusTitle, String cusName, String cusAddress, String cusContact) {
        this.cusID = cusID;
        this.cusTitle = cusTitle;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusContact = cusContact;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusTitle() {
        return cusTitle;
    }

    public void setCusTitle(String cusTitle) {
        this.cusTitle = cusTitle;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusContact() {
        return cusContact;
    }

    public void setCusContact(String cusContact) {
        this.cusContact = cusContact;
    }
}
