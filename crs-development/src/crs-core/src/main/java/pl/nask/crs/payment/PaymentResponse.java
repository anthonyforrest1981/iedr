package pl.nask.crs.payment;

public class PaymentResponse {
    private String result;
    private String message;
    private String authcode;
    private String pasref;
    private String bank;
    private String country;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getPasref() {
        return pasref;
    }

    public void setPasref(String pasref) {
        this.pasref = pasref;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("PaymentResponse:");
        builder.append("\n").append("  result:   ").append(result);
        builder.append("\n").append("  message:  ").append(message);
        builder.append("\n").append("  authcode: ").append(authcode);
        builder.append("\n").append("  pasref:   ").append(pasref);
        builder.append("\n").append("  bank:     ").append(bank);
        builder.append("\n").append("  country:  ").append(country);
        return builder.toString();
    }
}
