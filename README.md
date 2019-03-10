## Needed stacks
    + maven 3
    + java8
    + mysql
    + android studio

## Tech stacks
    + maven 3
    + java8
    + mysql
    + springboot
    + android

## To get started follow this checklist:
    + create schema micro-account, micro-config, micro-product, micro-order
    + import dump sql file from every module
    + start kafka :
        1. bin/zookeeper-server-start.sh config/zookeeper.properties
        2. bin/kafka-server-start.sh config/server.properties
    + create topic :
        1. bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic micro-order
    + create group :
        1. bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic micro-order --consumer-property group.id=micro-group-order
    + run redis-server
    + run  mvn clean install in all module :
    + run project: java -jar app-config-0.0.1-SNAPSHOT.jar
    + run project: java -jar app-discovery-0.0.1-SNAPSHOT.jar
    + run project: java -jar app-gateway-0.0.1-SNAPSHOT.jar
    + run project: java -jar app-account-0.0.1-SNAPSHOT.jar
    + run project: java -jar app-product-0.0.1-SNAPSHOT.jar
    + run project: java -jar app-order-0.0.1-SNAPSHOT.jar


SERVICE ACCOUNT
how to build    : mvn clean install -DskipTest
how to run      : java -jar api.jar -Dspring.profiles.active=production


CONFIGURE DB
install : brew install postgresql
start postgre : pg_ctl -D /usr/local/var/postgres -l logfile start
run cli : psql -U usernaem(ex:israjhaliri) -d postgres
import dummy from sql type text plain: psql -U username(ex:israjhaliri) -d namedb(ex:duo-gen) -a -f file.sql

regenerate postgre :
rm -rf /usr/local/var/postgres
initdb /usr/local/var/postgres -E utf8