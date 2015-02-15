野球のシミュレータを作る
====

## 概要

野球盤システムで打撃結果のシミュレーションを行います

## 内容

* Simulation.java シミュレーションを行う

* PlayerData.java 選手成績をsqliteから呼ぶ

* GameSituation.java 試合状況と攻撃の内容

* teamData 2014年の成績データディレクトリ

* createBaseballDB.bash 成績データをsqlに突っ込む

GameSituationにplayerDataを与えてattackすると, gameSituationが変化する

## ToDo

java で makefile的なやつを使う
