package ExchangeApp;

public class User {

    public static User user;

    private String userShow;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private String userEmail;
    private String userPhone;
    private String userImage;
    private Double pD;
    private Double eth;
    private Double dog;
    private Double not;
    private Double ham;
    private Double fee;


    public User(String userShow, String userFirstName, String userLastName, String userPassword, String userEmail, String userPhone, String userImage, String pD, String eth, String dog, String not, String ham) {
        this.setUserShow(userShow);
        this.setUserFirstName(userFirstName);
        this.setUserLastName(userLastName);
        this.setUserPassword(userPassword);
        this.setUserEmail(userEmail);
        this.setUserPhone(userPhone);
        this.setUserImage(userImage);
        this.setpD(pD);
        this.setEth(eth);
        this.setDog(dog);
        this.setNot(not);
        this.setHam(ham);
        this.setFee(0.0);
    }

    public String getUserShow() {
        return userShow;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public void setUserShow(String userShow) {
        this.userShow = userShow;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Double getpD() {
        return pD;
    }

    public void setpD(String pD) {
        this.pD = Double.parseDouble(pD);
    }

    public Double getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = Double.parseDouble(eth);
    }

    public Double getDog() {
        return dog;
    }

    public void setDog(String dog) {
        this.dog = Double.parseDouble(dog);
    }

    public Double getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = Double.parseDouble(not);
    }

    public Double getHam() {
        return ham;
    }

    public void setHam(String ham) {
        this.ham = Double.parseDouble(ham);
    }
}
