<?php

class EmailAddrValidatorTest extends PHPUnit_Framework_TestCase {

    public function testEmailAddresses() {
        $this->verifyCorrect('myemailaddress@domain.ie');
        $this->verifyCorrect('my.email.address@domain.ie');
        $this->verifyCorrect('my.email.address@aáàcçeéèiíìoóòuúù.ie');
        $this->verifyCorrect('my{.em}a$il.a^ddress@domain.ie');
        // length = 254
        $this->verifyCorrect('myemailaddress@segment0.segment1.segment2.segment3.segment4.segment5.segment6.segment7.segment8.segment9.segment10.segment11.segment12.segment13.segment14.segment15.segment16.segment17.segment18.segment19.segment20.segment21.segment22.segment23.domain.ie');
        // no @
        $this->verifyIncorrect('myemailaddress.domain.ie', "Email must have exactly one \"@\"");
        // two @'s
        $this->verifyIncorrect('my@emailaddress@domain.ie', "Email must have exactly one \"@\"");
        // empty user segment
        $this->verifyIncorrect('@domain.ie', "Email must not start with \"@\"");
        // non-ASCII user segment
        $this->verifyIncorrect('my.ęmail.ąddress@domain.ie', "Email has illegal characters before \"@\"");
        // too long segment in hostname
        $this->verifyIncorrect('myemailaddress@aáàbcçdeéèfghiíìjklmnoóòprstquúùwxyzaáàbcçdeéèfghiíìjklmnoóòprstquúùwxyz.ie',
                "Email has illegal domain segment (after \"@\")");
        // user segment length = 65
        $this->verifyIncorrect('myemailaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddress@domain.ie',
                "Email has too long string before \"@\" (64 characters allowed)");
        // length = 255
        $this->verifyIncorrect('myemailaddress@segment1.segment2.segment3.segment4.segment5.segment6.segment7.segment8.segment9.segment10.segment11.segment12.segment13.segment14.segment15.segment16.segment17.segment18.segment19.segment20.segment21.segment22.segment23.segment24.domain.ie',
                "Email is too long (maximum is 254 characters)");
        // length in punycode = 254
        $this->verifyCorrect('myem-l@ségment0.ségment1.ségment2.ségment3.ségment4.ségment5.ségment6.ségment7.ségment8.ségment9.ségment10.ségment11.ségment12.ségment13.ségment14.ie');
        // length in punycode = 255
        $this->verifyIncorrect('myemail@ségment0.ségment1.ségment2.ségment3.ségment4.ségment5.ségment6.ségment7.ségment8.ségment9.ségment10.ségment11.ségment12.ségment13.ségment14.ie',
               "Email's punycode form is too long (maximum is 254 characters)");
    }

    private function verifyCorrect($email) {
        $model = new Nichandle_Details();
        $model->email = $email;
        $model->validate();
        $errorMessage = $model->getError('email');
        self::assertFalse(isset($errorMessage));
    }

    private function verifyIncorrect($email, $expectedMessage) {
        $model = new Nichandle_Details();
        $model->email = $email;
        $model->validate();
        $errorMessage = $model->getError('email');
        self::assertTrue(isset($errorMessage), "Email $email should be considered incorrect");
        self::assertEquals($errorMessage, $expectedMessage);
    }

}
