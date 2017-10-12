# Checker build of IEDR CRS system

In order to run checker framework while building project use maven profile _checkerFramework_, e.g.

    $ mvn clean package -PcheckerFramework -DskipTests

or (if automated tests should also be run) remember to add db-mini profile

    $ mvn clean package -PcheckerFramework,db-mini

`checkerFramework` profile depends on external installation of checker framework, precisely that a system environment variable
`CHECKER_HOME` exists and points at checker installation directory. `install.sh` helps to set up your system in that
regard.

## How it operates

_checkerFramework_ profile uses two files from this directory to do it's bidding:

* `javac_maven` which is normally distributed with checker framework itself and should only pass arguments down to
`checker.jar` compiler. Because of maven problem with relative / absolute paths it had to be moved and modified
accordingly. Except for debugging purposes you should not touch this file.

* `argfile` arguments file passed to checker framework with settings for this project. If you wish to add new files
to checker list, modify checker options etc. this is your file.

## Standard mode of operation

Since annoting whole codebase at once is prohibitively expensive right classes are annotated one-by-one basis with
each new commit. Such annoted classes should be updated in only/skip Defs/Uses list in argfile. Right now basically
whole project is turned off.

## Debugging problems

In case maven compiling fails but gives you no error message beyond generic "plugin failed" you can make checker
framework record each and every invocation of itself so that it can later be run by-hand. In order to do so modify
`javac_maven` file (`argfile` won't work for it for some reason) and add there option

    -AoutputArgsToFile=/path/where/to/record/javac_call.sh

After that run maven once again - it will fail, but in the process will create the file `javac_call.sh`. You can now
run

    $ /bin/bash javac_call.sh

And have full javac output before you. If `javac_call.sh` file does not get created it might mean that maven does not
run `javac_maven`, run it with `-X` - it will cause whole executable path to be printed in the logs.
