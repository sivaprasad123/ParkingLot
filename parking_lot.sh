#!/bin/sh

echo "###### Building the maven project #########"
mvn clean install
file_path=$PWD/service/target/ParkingLot.jar
file_path2=$PWD/commons/target/commons-1.0-SNAPSHOT.jar
echo $file_path
if [ $# -ge 1 ]
then
    java -cp $file_path:$file_path2 com.gojek.parking.client.ParkingLotClient $1
else
    java -cp $file_path:$file_path2 com.gojek.parking.client.ParkingLotClient
fi
