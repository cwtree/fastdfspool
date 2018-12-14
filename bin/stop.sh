#!/bin/bash

fastdfspool_pids=`ps -ef | grep "fastdfspool.jar" | grep -v grep | awk '{print $2}'`
for fastdfspool_pid in $fastdfspool_pids
do
    echo stop fastdfspool java process: $fastdfspool_pid
    kill -9 $fastdfspool_pid
done

nr_fastdfspool_pids=`ps -ef | grep "fastdfspool.jar" | grep -v grep | wc -l`
while [ $nr_fastdfspool_pids -gt 0 ]
do
    nr_fastdfspool_pids=`ps -ef | grep "fastdfspool.jar" | grep -v grep | wc -l`
done
echo now, fastdfspool process is not run!
