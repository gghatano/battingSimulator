#!/bin/bash

dir=$(dirname $0)

# teamName=$1
teamName="giants"

echo "NAME,ATBAT,SINGLE,DOUBLE,TRIPLE,HOMERUN,BB,SO,OBP"
cat giants.html | 
grep "text-align:center" | 
sed 's/<[^>]*>/,/g' | 
sed 's/,,*/,/g' |
sed -e 's/　//' -e 's/\./0./g' -e 's/^,//' -e 's/,$//' |
awk -F"," -v OFS="," '{print $2, $6, $8-$9-$10-$11,$9,$10,$11, $18+$19+$20, $21,$24}' > giants.txt

