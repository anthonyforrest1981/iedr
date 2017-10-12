package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import pl.nask.crs.commons.utils.Validator;

public class ExtendedPaymentRequest {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

    private final PaymentRequest paymentRequest;

    // Realex credentials
    private final String account;
    private final String merchantId;
    private final String password;

    // generated with #generateMDHash()
    private String md5Hash;
    private String timestamp;

    private String orderId;

    private TransactionType type;

    private String pasref;
    private String authcode;
    private String bank;
    private String country;

    public static ExtendedPaymentRequest authorisationInstance(String account, String merchantId, String password,
            PaymentRequest paymentRequest) {
        Validator.assertNotNull(paymentRequest, "payment request");
        String timestamp = getCurrentTimestamp();
        String orderId = generateOrderId(timestamp);
        return new ExtendedPaymentRequest(account, merchantId, password, TransactionType.TYPE_AUTH, paymentRequest,
                null, null, orderId, timestamp);
    }

    public static ExtendedPaymentRequest authorisedInstance(String account, String merchantId, String password,
            TransactionType transactionType, String authcode, String pasref, String orderId) {
        Validator.assertNotNull(transactionType, "transaction type");
        Validator.assertNotEmpty(authcode, "authcode");
        Validator.assertNotEmpty(pasref, "pasref");
        Validator.assertNotEmpty(orderId, "orderId");
        if (transactionType == TransactionType.TYPE_AUTH) {
            throw new IllegalArgumentException("Illegal transaction type for authorised instance: " + transactionType);
        }
        String timestamp = getCurrentTimestamp();
        return new ExtendedPaymentRequest(account, merchantId, password, transactionType, null, authcode, pasref,
                orderId, timestamp);
    }

    public static ExtendedPaymentRequest loggableInstance(ExtendedPaymentRequest request) {
        PaymentRequest loggablePaymentRequest = null;
        if (!request.isAuthorisedInstance()) {
            loggablePaymentRequest = PaymentRequest.createPaymentRequestWithLowestCurrencyUnit(request.getCurrency(),
                    request.getAmountInLowestCurrencyUnit(),
                    Validator.isEmpty(request.getCardNumber()) ? "" : "######",
                    Validator.isEmpty(request.getCardExpDate()) ? "" : "######",
                    Validator.isEmpty(request.getCardType()) ? "" : "######",
                    Validator.isEmpty(request.getCardHolderName()) ? "" : "######",
                    Validator.isEmpty(request.getCvnNumber()) ? "" : "######", request.getCvnPresenceIndicator());
        }
        return new ExtendedPaymentRequest(request.getAccount(), request.getMerchantId(), request.getPassword(),
                request.getType(), loggablePaymentRequest, request.getAuthcode(), request.getPasref(),
                request.getOrderId(), request.getTimestamp());
    }

    private ExtendedPaymentRequest(String account, String merchantId, String password, TransactionType transactionType,
            PaymentRequest paymentRequest, String authcode, String pasref, String orderId, String timestamp) {
        Validator.assertNotEmpty(merchantId, "merchant id");
        Validator.assertNotEmpty(password, "password");
        this.account = account;
        this.merchantId = merchantId;
        this.password = password;
        this.type = transactionType;
        this.authcode = authcode;
        this.pasref = pasref;
        this.orderId = orderId;
        this.paymentRequest = paymentRequest;
        this.timestamp = timestamp;
    }

    public String getCurrency() {
        return paymentRequest.getCurrency();
    }

    public Integer getAmountInLowestCurrencyUnit() {
        return paymentRequest.getAmountInLowestCurrencyUnit();
    }

    public BigDecimal getAmountInStandardCurrencyUnit() {
        return paymentRequest.getAmountInStandardCurrencyUnit();
    }

    public String getCardNumber() {
        return paymentRequest.getCardNumber();
    }

    public String getCardExpDate() {
        return paymentRequest.getCardExpDate();
    }

    public String getCardType() {
        return paymentRequest.getCardType();
    }

    public String getCardHolderName() {
        return paymentRequest.getCardHolderName();
    }

    public String getCvnNumber() {
        return paymentRequest.getCvnNumber();
    }

    public Integer getCvnPresenceIndicator() {
        return paymentRequest.getCvnPresenceIndicator();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAccount() {
        return account;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getPassword() {
        return password;
    }

    public String getMd5Hash() {
        generateMD5Hash();
        return md5Hash;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPasref() {
        return pasref;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getBank() {
        return bank;
    }

    public String getCountry() {
        return country;
    }

    public void setAuthorisationData(String authcode, String pasref, String bank, String country) {
        this.authcode = authcode;
        this.pasref = pasref;
        this.bank = bank;
        this.country = country;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    private void generateMD5Hash() {
        String tmp = this.timestamp + "." + this.merchantId + "." + this.orderId;

        if (this.getType().equals(TransactionType.TYPE_AUTH))
            tmp += ("." + this.paymentRequest.getAmountInLowestCurrencyUnit() + "." + this.paymentRequest.getCurrency()
                    + "." + this.paymentRequest.getCardNumber());
        else
            tmp += "...";

        this.md5Hash = getMD5(tmp);
        tmp = this.md5Hash + "." + this.password;
        this.md5Hash = getMD5(tmp);
    }

    private String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateOrderId(String timestamp) {
        Random r = new Random();
        return timestamp + "-C-" + r.nextInt(9999999);
    }

    private static String getCurrentTimestamp() {
        return FORMATTER.format(new Date());
    }

    private boolean isAuthorisedInstance() {
        return (paymentRequest == null);
    }

}
