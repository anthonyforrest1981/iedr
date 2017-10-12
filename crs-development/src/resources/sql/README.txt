========================================================================================================================================================
										MySQL DB v5.6+ DATABASE SETUP
========================================================================================================================================================

Go to mysql prompt: mysql>

mysql> CREATE DATABASE crsdb;
mysql> USE DATABASE crsdb;
mysql> CREATE USER 'crsuser'@'%' IDENTIFIED BY 'crspassword';
mysql> CREATE USER 'crsuser'@'54.36.100.179' IDENTIFIED BY 'crspassword';					// Where this public IP is the cloud VM hosting MySQL Server.
mysql> CREATE USER 'crsuser'@'localhost' IDENTIFIED BY 'crspassword';
mysql> GRANT ALL PRIVILEGES ON crsdb.* TO 'crsuser'@'%' WITH GRANT OPTION;
mysql> GRANT ALL PRIVILEGES ON crsdb.* TO 'crsuser'@'54.36.100.179' WITH GRANT OPTION;		// Where this public IP is the cloud VM hosting MySQL Server.
mysql> GRANT ALL PRIVILEGES ON crsdb.* TO 'crsuser'@'localhost' WITH GRANT OPTION;

// The other users are created within the .sql scripts per release if needed.

========================================================================================================================================================
										MySQL DB v5.6+ DATA LOADING SEQUENCE (Starting from the top run in order)
========================================================================================================================================================
FILE:							 		DESC:													SAMPLE COMMAND (where DB name is 'crsdb'):

0000-schema-baseline.sql         		Latest Production Schema only - NO DATA!				sudo mysql crsdb < 0000-schema-baseline.sql
1000-triggers.proc               		Trigger to be set on row update							<<OPEN FILE TO SEE INSTRUCTIONS>>
2000-test-data-bulk.sql          		TEST DATA - hardcoded tests depend on it				sudo mysql crsdb < 2000-test-data-bulk.sql    
5000-schema-migrations.sql       		Schema changes applied before first release				sudo mysql crsdb < 5000-schema-migrations.sql

NEXT ... open up 'irish-collation-readme.txt' and apply those changes before going on ...

5???-releaseName-release.sql     		Release specific data / schema changes per release		sudo mysql crsdb < 5???-releaseName-release.sql   
5???-releaseName-test-data.sql   		Release specific data / schema changes for TEST env		sudo mysql crsdb < 5???-releaseName-test-data.sql

THEN ... for '5011-Impala-release.sql' .. if you get an error where not more than one CURRENT_TIMESTAMP can be used per column on table, you can try the
following ...

Replace all 'DEFAULT CURRENT_TIMESTAMP' with 'DEFAULT now()' from line 1848 onwards (as this is where the error was encountered). Then execute again. You
may have to restore the DB to the previous state prior to running the '5011-Impala-release.sql' as now some of the table column names have changed. To 
do this simply, drop the DB and execute 'sudo mysql crsdb < backedUpToDomainLockingTestData.sql' to bring the DB up to state prior to Impala pre-release 
and restart .sql execution from 5011-Impala** again.

CONTINUE ...

5???-releaseName-release.sql     		Release specific data / schema changes per release		sudo mysql crsdb < 5???-releaseName-release.sql   

FINALLY ...

6000-data-migrations.sql				Placeholder for now (not sure).							sudo mysql crsdb < 6000-data-migrations.sql
9999-cleanup.sql                 		Final steps before tests run.							sudo mysql crsdb < 9999-cleanup.sql
========================================================================================================================================================
