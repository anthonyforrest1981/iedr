<?php include('test_config.php'); test_header('Transfers current invoice: pay offline'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=accounts_renewpay_currinv/transfers_paycurrent</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php
$domain1='example0129.ie';
$period='1';
$type='0';
$p = getPrice($period, $type);
$p1 =($p*5);
$price=number_format($p1,2); 
$vatrate = getVatRate($account_number);
$t = $price+($price*$vatrate);
$t1 =($t);
$total=number_format($t1,2); 
{
//This test script is selecting for a domain name for offline payment from the xfer invoice and also ensuring that the correct amount has been charged

                // set value, reload, check output

?>
<tr><td>pause</td><td>2500</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0128.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0137.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0216.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0215.ie</td><td></td></tr>
<tr><td>click</td><td>jqg_thisJqGrid_example0214.ie</td><td></td></tr>
<tr><td>clickAndWait</td><td>gridaction_off</td><td></td></tr>
<tr><td>clickAndWait</td><td>yt0</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>Your payment transaction has been successful</td><td></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/h4</td><td>Your payment transaction has been successful</td></tr>
<tr><td>verifyText</td><td>//div[@id='content']/div/h4[2]</td><td>NB - Promise to pay, not an actual payment</td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[3]</td><td>€ <?php echo $price; ?></td></tr>
<tr><td>assertText</td><td>//div[@id='content']/div/table/tbody/tr[3]/td[5]</td><td>€ <?php echo $total; ?></td></tr>

<tr><td>clickAndWait</td><td>//input[@value='Return']</td><td></td></tr>


<?php
                }
?>
<?php test_footer(); ?>
