#!/bin/bash

dpiCvtGo_pids=`ps -ef | grep "dpiCvtGo.jar" | grep -v grep | awk '{print $2}'`
for dpiCvtGo_pid in $dpiCvtGo_pids
do
    echo stop dpiCvtGo java process: $dpiCvtGo_pid
    kill -9 $dpiCvtGo_pid
done

nr_dpiCvtGo_pids=`ps -ef | grep "dpiCvtGo.jar" | grep -v grep | wc -l`
while [ $nr_dpiCvtGo_pids -gt 0 ]
do
    nr_dpiCvtGo_pids=`ps -ef | grep "dpiCvtGo.jar" | grep -v grep | wc -l`
done
echo now, dpiCvtGo process is not run!
