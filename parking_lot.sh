#!/bin/sh

echo "###### Building the maven project #########"
mvn clean install
file_path=$PWD/service/target/ParkingLot.jar
echo $file_path
if [ $# -ge 1 ]
then
    java -cp $file_path com.gojek.parking.client.ParkingLotClient $1
else
    java -cp $file_path com.gojek.parking.client.ParkingLotClient
fi
