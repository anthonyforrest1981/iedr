<?php

use PHPUnit_Framework_Assert as Assert;

function validatePaymentDate($arr) {
    $nic = $arr['Nic_Handle'];
    $rate = $arr['rate'];
    $user = $arr['user'];
    $cdate = $arr['Creation_Date'];
    $today = $arr['today'];
    $amt = $arr['Amount'];
    $payM = $arr['payM'];
    $vat = $arr['Vat_Amount'];
    $orig_ren = $arr['Orig_Ren_Dt'];
    $new_ren = $arr['D_Ren_Dt'];
    $settled = $arr['Settled'];
    $years = $arr['Years'];
    $o_type = $arr['Operation_Type'];
    $type = $arr['type'];
    $pay_type = $arr['pay_type'];

    $price = DBUtils::getProductPrice($rate);
    $vatrate = DBUtils::getVatRateForUser($user);
    $priceVat = $price * $vatrate;
    $total = ($price + $priceVat);
    settype($priceVat, "string");
    settype($total, "string");

    Assert::assertEquals($nic, $user, "NicHandle");
    Assert::assertEquals($amt, $price, "Price");
    Assert::assertEquals($vat, $priceVat, "Vat");
    Assert::assertEquals($cdate, $today, "Creation date");
    Assert::assertEquals($orig_ren, $new_ren, "Renew date");
    Assert::assertEquals($settled, 'NO', "Is settled");
    Assert::assertEquals($o_type, $type, "Type");
    Assert::assertEquals($pay_type, $payM, "Payment type");
}
