# Liquibase Property Bug
This repository showcases a bug in Liquibase 5.0.2 regarding property substitution with DBMS-specific filters.


## Description of the bug
Within Liquibase, it is possible to define properties within a changelog. By placing a specific token within a changeset that corresponds with a property within a changelog, the token can be replaced by the value of the property when the changeset is executed.

When specifying a property within a changelog, you can also specify that the property should only be set when the changelog is executed against a specific DBMS using the *dbms* option. The bug that I found causes this mechanism to fail, causing properties to be set when they should not be.

## Prerequisites for using this repository:
* Administrator access to a PostgreSQL instance.
  * I used version *16.13.1*.
* A JDK for Java 21.
  * I used *Temurin-21.0.7+6*.
* Maven
  * I used version *3.9.10*. 


## How to use this repository:
1. Clone this repository.
2. Change the values of the USERNAME, PASSWORD, and URL constants so that the Java code can connect to your PostgreSQL instance.
  * You can use the currentSchema parameter within the URL to specify which schema should be used.
  * The schema that you specify should be completely empty.
3. Compile and run the Main class while using the *update* argument.
4. Observe that the *what_dbms_am_i* table is filled with 1 row, of which the value of the *name* column equals 'Some other DBMS'.
5. Delete all tables from the schema.
6. Compile and run the Main class while using the *updateWithParameter* argument.
7. Observe that the *what_dbms_am_i* table is filled with 1 row, of which the value of the *name* column equals 'Db2 for IBM i', even though we are using PostgreSQL as our DBMS.
