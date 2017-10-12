package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.*;

import pl.nask.crs.payment.CreditCard;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreditCardVO {
    @XmlElement(required = true, nillable = false)
    private String cardNumber;
    @XmlElement(required = true, nillable = false)
    private String cardExpDate;
    @XmlElement(required = true, nillable = false)
    private String cardType;
    @XmlElement(required = true, nillable = false)
    private String cardHolderName;
    private String cvnNumber;
    private Integer cvnPresenceIndicator;

    public CreditCardVO() {}

    public CreditCard toCreditCard() {
        return new CreditCard(this.cardNumber, this.cardExpDate, this.cardType, this.cardHolderName, this.cvnNumber,
                this.cvnPresenceIndicator);
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

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCvnNumber(String cvnNumber) {
        this.cvnNumber = cvnNumber;
    }

    public void setCvnPresenceIndicator(Integer cvnPresenceIndicator) {
        this.cvnPresenceIndicator = cvnPresenceIndicator;
    }
}
