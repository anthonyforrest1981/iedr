package pl.nask.crs.payment;

public class CreditCard {

    private final String cardNumber;
    private final String cardExpDate;
    private final String cardType;
    private final String cardHolderName;
    private final String cvnNumber;
    private final Integer cvnPresenceIndicator;

    public CreditCard(String cardNumber, String cardExpDate, String cardType, String cardHolderName, String cvnNumber,
            Integer cvnPresenceIndicator) {
        this.cardNumber = normalizedCardNumber(cardNumber);
        this.cardExpDate = cardExpDate;
        this.cardType = cardType;
        this.cardHolderName = cardHolderName;
        this.cvnNumber = cvnNumber;
        this.cvnPresenceIndicator = cvnPresenceIndicator;
    }

    private String normalizedCardNumber(String cardNumber) {
        return cardNumber.replaceAll("\\s", "");
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCvnNumber() {
        return cvnNumber;
    }

    public Integer getCvnPresenceIndicator() {
        return cvnPresenceIndicator;
    }

}
