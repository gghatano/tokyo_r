#!/bin/bash

dir=$(dirname $0)

## データを楽しむプロ野球から, 打撃成績を持ってくる
# teamName=$1
# teamName="giants"

: > $dir/stats.dat

for line in `cat $dir/url_data.dat` 
do
  teamName=$(echo $line | cut -d"," -f1)
  echo $teamName
  url=$(echo $line | cut -d"," -f2)
  echo $url

  curl $url | 
  grep "text-align:center" | 
  sed 's/<[^>]*>/,/g' | 
  sed 's/,,*/,/g' |
  sed -e 's/　//' -e 's/\./0./g' -e 's/^,//' -e 's/,$//' |
  awk -F"," -v OFS="," -v teamName=$teamName '{print teamName, $2, $6, $8-$9-$10-$11,$9,$10,$11, $18+$19+$20, $21}'| 
  grep -v "-" >> $dir/stats.dat
done

cat $dir/stats.major.dat >> $dir/stats.dat

cat $dir/stats.dat

