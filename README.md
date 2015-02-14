Javaの思い出し::野球のシミュレータを作る
====

## 概要

野球盤システムで打撃結果のシミュレーションを行います


## 内容

* Player.java 選手成績を扱う

* GameSituation.java 試合状況と攻撃の内容

GameSituationにplayerを与えてattackすると, gameSituationが変化する

## ToDo

選手データ.csvをパースして, 選手データを読み込むスクリプト

readLineしてsplit(,)すればいいはず

Player(double average, 
    double probSingle, double probDouble, double probTriple, double probHomerun,
    double probSwingOut, double probOtherOut, 
    String batterName){




