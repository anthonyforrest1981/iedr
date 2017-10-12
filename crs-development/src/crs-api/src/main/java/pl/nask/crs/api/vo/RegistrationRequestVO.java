package pl.nask.crs.api.vo;

import pl.nask.crs.commons.utils.Validator;

public class RegistrationRequestVO extends AbstractTicketRequestVO {

    private String charityCode;

    /* (non-Javadoc)
    * @see pl.nask.crs.api.vo.RegistrationRequest#getCharityCode()
    */
    public String getCharityCode() {
        return charityCode;
    }

    public void setCharityCode(String charityCode) {
        this.charityCode = charityCode;
    }

    /* (non-Javadoc)
     * @see pl.nask.crs.api.vo.RegistrationRequest#isCharity()
     */
    public boolean isCharity() {
        return !Validator.isEmpty(charityCode);
    }

    @Override
    public String getAuthCode() {
        /* Should never be called.
         * Added to avoid creating too many additional classes.
         * TransferDomain class uses an object of the TicketRequest superclass and the method is called there.
         * If more methods are ever added which don't correspond to TicketRequest interface, it should be separated.
         * (changes by Wojciech Wąs)
         */
        throw new RuntimeException("Trying to get an authcode from registration ticket");
    }

}
