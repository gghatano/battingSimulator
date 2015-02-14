#!/bin/bash

dir=$(dirname $0)

cat << _EOT_ > /tmp/createTable.txt
drop table baseballdata;
CREATE TABLE baseballData(team text, name text,
atbat int,
single int, double int, triple int, homerun int, 
bb int, swingout int);
.schema
.import teamData/giants.csv baseballData
select * from baseballdata;

_EOT_

cat /tmp/createTable.txt | 
sqlite3 --separator , --header "$dir"/baseballData.sqlite


