//Main.java
package com.testproject.liquibasepropertybug;

import liquibase.changelog.ChangeLogParameters;
import liquibase.command.CommandScope;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=public";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "postgres";

    public static final String CHANGELOG = "db.changelog.yaml";
    public static final String CHANGELOG_ARGUMENT_KEY = "changelogFile";
    public static final String URL_ARGUMENT_KEY = "url";
    public static final String USERNAME_ARGUMENT_KEY = "username";
    public static final String PASSWORD_ARGUMENT_KEY = "password";
    public static final String CHANGELOG_PARAMETERS_ARGUMENT_KEY = "changelogParameters";
    public static final String DESCRIPTION_PARAMETER_KEY = "description";
    public static final String DESCRIPTION_PARAMETER_VALUE = "An DBMS specialized in storing and retrieving data.";

    public static void main(String[] args) throws Exception {
        List<String> argsAsList = Arrays.asList(args);

        if (argsAsList.contains("update")) {
            runUpdate();
        } else if (argsAsList.contains("updateWithParameter")) {
            runUpdateWithParameter();
        }
    }

    //Property nameOfDbms will be resolved as 'Some other DBMS'.
    //This is what I expect when using PostgreSQL as my DBMS.
    public static void runUpdate() throws Exception {
        CommandScope command = new CommandScope("update");
        command.addArgumentValue(CHANGELOG_ARGUMENT_KEY, CHANGELOG);
        command.addArgumentValue(URL_ARGUMENT_KEY, URL);
        command.addArgumentValue(USERNAME_ARGUMENT_KEY, USERNAME);
        command.addArgumentValue(PASSWORD_ARGUMENT_KEY, PASSWORD);

        command.execute();
    }

    //Property nameOfDbms will be resolved as 'Db2 for IBM i'.
    //This is not what I would expect, since I am using PostgreSQL as DBMS.
    public static void runUpdateWithParameter() throws Exception {
        ChangeLogParameters parameters = new ChangeLogParameters();
        parameters.set(DESCRIPTION_PARAMETER_KEY, DESCRIPTION_PARAMETER_VALUE);

        CommandScope command = new CommandScope("update");
        command.addArgumentValue(CHANGELOG_ARGUMENT_KEY, CHANGELOG);
        command.addArgumentValue(URL_ARGUMENT_KEY, URL);
        command.addArgumentValue(USERNAME_ARGUMENT_KEY, USERNAME);
        command.addArgumentValue(PASSWORD_ARGUMENT_KEY, PASSWORD);
        command.addArgumentValue(CHANGELOG_PARAMETERS_ARGUMENT_KEY, parameters);

        command.execute();
    }

}
