conf_dir="conf"
if [ ! -d "$conf_dir" ]; then
  mkdir $conf_dir
fi
jps
pid=$(jps | grep "${project}".jar | awk '{split($1,arr," ");print arr[1]}')
if [ -n "${pid}" ]; then
  echo "kill -15 ${pid}"
  kill -15 "${pid}"
fi
cat /dev/null > ./"${project}".log
