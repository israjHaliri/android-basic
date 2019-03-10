## Needed stacks
    + maven 3
    + java8
    + kotlin
    + postgresql
    + android studio

## Tech stacks
    + maven 3
    + java8
    + kotlin
    + postgresql
    + springboot
    + android

## To get started follow this checklist:

## Configure DB
    + install : brew install postgresql
    + start postgre : pg_ctl -D /usr/local/var/postgres -l logfile start
    + run cli : psql -U usernaem(ex:israjhaliri) -d postgres
    + import dummy from sql type text plain: psql -U username(ex:israjhaliri) -d namedb(ex:duo-gen) -a -f file.sql

## Run Service & APP
    + how to build service   : mvn clean install -DskipTest
    + how to run service    : java -jar api.jar -Dspring.profiles.active=production
    + how to run app      : run in android studio or install the apk

## Common CLI
    regenerate postgre :
    rm -rf /usr/local/var/postgres
    initdb /usr/local/var/postgres -E utf8
