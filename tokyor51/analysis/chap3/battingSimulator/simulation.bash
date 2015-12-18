#!/bin/bash
# simulate the game for 100 times

dir=$(dirname $0)

[ -e $dir/simulationResult.txt.matsui ] && rm $dir/simulationResult.txt.matsui
[ -e $dir/tmp.matsui.txt ] && rm $dir/tmp.matsui.txt

## input batter name
#echo -n "選手名をフルネームで正確に入力(デフォルトはイチロー) : "
#read inputPlayerName
inputPlayerName=$1

[ "$inputPlayerName" = "" ] && inputPlayerName="イチロー"

#echo -n "試合数を入力(デフォルトは1試合) : "
#read gameNum
gameNum=1000

[ "$gameNum" = "" ] && gameNum=1

cat $dir/Simulation.java.template | 
sed "s/inputPlayerName/$inputPlayerName/" > $dir/Simulation.java
javac $dir/Simulation.java

simulationDate=$(date "+%Y%m%d%H%M%S")
echo "NUM,ATBAT,SCORE" > $dir/result/simulationResult."$inputPlayerName"."$gameNum".txt.$simulationDate

for num in $(seq 1 $gameNum)
do

  ## シミュレーションの結果
  java Simulation > $dir/tmp/game.result.txt

  ## 打席数
  ab=$(cat $dir/tmp/game.result.txt | 
       grep "Result" | grep -v "score" | wc -l )

  ## 点数
  score=$(cat $dir/tmp/game.result.txt | 
          grep "Simulation Result" | 
          awk '{print $NF}')

  echo "$num, $ab, $score" | tr -d " " >> $dir/result/simulationResult."$inputPlayerName"."$gameNum".txt.$simulationDate

  ## 進捗と結果
  echo "${num}/${gameNum} 試合目: ${ab}打席${score}得点" || tr -d " "
  
done

echo ""
echo "結果はresult/simulationResult.~~~.txtに入っています"
echo "最後の試合の進行状況は, ./tmp/game.result.txtに残っています"

