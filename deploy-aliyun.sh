#!/bin/bash -e
project="$1"
java_opts="$2"
spring_args="$3"
jps
pid=$(jps | grep "${project}".jar | awk '{split($1,arr," ");print arr[1]}')
if [ -n "${pid}" ]; then
  echo "kill -15 ${pid}"
  kill -15 "${pid}"
fi
cat /dev/null > ./"${project}".log
echo "${java_opts}"
echo "${spring_args}"
echo "nohup java ${java_opts} -jar ./${project}.jar ${spring_args} > ${project}.log 2>&1 &"
sh -c "nohup java ${java_opts} -jar ./${project}.jar ${spring_args} > ${project}.log 2>&1 &"