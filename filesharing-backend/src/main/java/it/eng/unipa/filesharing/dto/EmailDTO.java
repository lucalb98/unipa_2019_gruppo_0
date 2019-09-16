package it.eng.unipa.filesharing.dto;

public class EmailDTO {
    private String email;

    public EmailDTO(){
        this.email=this.getEmailDTO();
    }
    public EmailDTO(String email){
        this.email=email;
    }

    public  void setEmailDTO(String email) {
        this.email=email;
    }
    public String getEmailDTO(){
        return this.email;
    }
    public String toString(){
        return "email:"+this.email;
    }

}
