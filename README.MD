# Liquibase Property Bug
This repository showcases a bug in Liquibase 5.0.2 regarding property substitution with DBMS-specific filters.


## Description of the bug
When defining a property within a changelog, you can specify that it should only be applied when a specific DBMS is being used by using the *dbms* option. However, this mechanism will fail if all of the following conditions are met:

* Liquibase is directly invoked using the CommandScope class of liquibase-core-5.0.2.jar
* Additional properties are being set using the ChangeLogParameters class.
* PostgreSQL is being used as DBMS.

As an result, this will cause the wrong properties to be applied.

## Prerequisites for using this repository:
* Administrator access to a PostgreSQL instance.
  * I used version *16.13.1*.
* A JDK for Java 21.
  * I used *Temurin-21.0.7+6*.
* Maven
  * I used version *3.9.10*. 


## How to use this repository:
1. Clone this repository.
2. Change the values of the USERNAME, PASSWORD, and URL constants so that the Java code can connect to your PostgreSQL instance. You can use the currentSchema parameter within the URL to specify which schema should be used. The schema that you specify should be completely empty.
3. Compile and run the Main class while using the *update* argument.
4. Observe that the *what_dbms_am_i* table is filled with 1 row, of which the value of the *name* column equals 'Some other DBMS'.
5. Delete all tables from the schema.
6. Compile and run the Main class while using the *updateWithParameter* argument.
7. Observe that the *what_dbms_am_i* table is filled with 1 row, of which the value of the *name* column equals 'Db2 for IBM i', even though we are using PostgreSQL as our DBMS.
