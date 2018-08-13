CREATE KEYSPACE logs
        WITH REPLICATION =
        { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

 CREATE COLUMNFAMILY logs.WSCallLog (
    Id uuid,
    servicename varchar,
    operation varchar,
    request varchar,
    response varchar,
    errorData varchar,
    requestUrl varchar,
    duration double,
    PRIMARY KEY (Id));

  select * from logs.WSCallLog;

 DROP TABLE IF EXISTS logs.WSCallLog;