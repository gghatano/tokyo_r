野球のシミュレータを作る
====

## 概要

野球盤システムで打撃結果のシミュレーションを行います

## 内容

* simulation.bash シミュレーションを行うシェルスクリプト. 選手名を入力すると100試合行います。

* Simulation シミュレーションを行うクラス

* PlayerData 選手成績をsqliteから呼んで整理する

* GameSituation 試合状況と攻撃結果

* teamData/stats.dat 2015年の最終成績データ

* createBaseballDB.bash 実行すると、stats.dat内の成績データをDBに突っ込む

GameSituationにplayerDataを与えてattackすると, gameSituationが変化する...というのを9回が終わるまで行うだけです.

## ToDo

なにしよう
