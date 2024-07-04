package ExchangeApp;

public class User {

    private String userShow;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private String userEmail;
    private String userPhone;
    private String userImage;
    private double pD;
    private double eth;
    private double dog;
    private double not;
    private double ham;


    public User(String userShow, String userFirstName, String userLastName, String userPassword, String userEmail, String userPhone, String userImage, double pD, double eth, double dog, double not, double ham) {
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
    }

    public String getUserShow() {
        return userShow;
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

    public double getpD() {
        return pD;
    }

    public void setpD(double pD) {
        this.pD = pD;
    }

    public double getEth() {
        return eth;
    }

    public void setEth(double eth) {
        this.eth = eth;
    }

    public double getDog() {
        return dog;
    }

    public void setDog(double dog) {
        this.dog = dog;
    }

    public double getNot() {
        return not;
    }

    public void setNot(double not) {
        this.not = not;
    }

    public double getHam() {
        return ham;
    }

    public void setHam(double ham) {
        this.ham = ham;
    }
}
