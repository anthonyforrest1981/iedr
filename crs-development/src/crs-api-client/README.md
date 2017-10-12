# API CLIENT

# Tests

Tests are taken as is from IEDR repository, and unfortunatelly they do not work as a whole test suite.
But they are still valuable as source of usage patterns, and most individual tests do work, but probably
require manual resetting of the database in order to pass the second time (no cleanup or transaction
around test). All iedr tests are in test.

In order to have tests work:

0. Take a look at src/main/ClientClassConfig.php.template, update values and save as php file. Do
   the same for test/testSuite/db.php.template
1. Assuming you use database setup by maven - execute src/test/sqlData/load_data.sql on it

        $ mysql -u crsuser -h crsdb crsdb < load_data.sql

2. tests are structured in such a way testSuite must be places with the rest of code

        $ ln -s src/test/testSuite src/main

3. from /src/main execute either all tests (passing folder name) or one test (passing path to it)

        $ phpunit testSuite
        $ phpunit testSuite/DomainCreate_success_withGlue_Test.php

PHPUnit by default if passed a directory executes all files named \*Test.php, all other are omitted.

## Expected failures

Tests known to fail (numbers indicate failure index, omited are warning about no tests in file, in
total there are 17 failuers):
2) DomainCreate3::testDomainCreateDomAlreadyExist
3) DomainReg11::testDomainCreateMYR
5) DomainRegMYR_XOE551::testDomainCreateMYR
6) DomainNRPSuccess1::testDomainNRPSuccess1
7) DomainTransferSuccessNoDefaultSet::testDomainTransfer
9) AccountPayDepositRenewOnce::testAccountPayRenewOnce
10) AccountPayDepositIM1Year::testAccountPayIM1Year
11) AccountPayMulti::testDepositTopUpDbTest
12) AccountPayMulti::testAccountPay2
13) AccountPayFromDepositRenewOnce::testAccountPayRenewOnce
14) AccountPayDeposit1YearXNV498::testAccountPay1Year
15) AccountPayDeposit1Year::testAccountPay1Year
16) AccountPayCC1YearTest::testAccountPayCC1YearMultiDomains
17) AccountPayCC1YearXNV498::testAccountPayCC1YearMultiDomains
