#!/bin/bash

dir=$(dirname $0)

$dir/teamData/dataScrape.bash

cat << _EOT_ > /tmp/createTable.txt
drop table baseballdata;
CREATE TABLE baseballData(team text, name text,
atbat int,
single int, double int, triple int, homerun int, 
bb int, swingout int);
.import teamData/stats.dat baseballData
select * from baseballdata 
  limit 10;
_EOT_

cat /tmp/createTable.txt | 
sqlite3 --separator , --header "$dir"/baseballData.sqlite


