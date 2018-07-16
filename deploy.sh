#!/bin/bash

current_dir=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
function println_info()
{
   echo "[INFO]========> $1"
}

if [ "$1" = "pro" ]; then
    properties_name=application-pro.properties
    log_name=logback-spring-pro.xml
    yml_name=application-pro.yml
elif [ "$1" = "dev" ]; then
    properties_name=application-dev.properties
    log_name=logback-spring.xml
    yml_name=application.yml
else
   println_info "输入参数有误，仅支持[dev、pro]"
   exit
fi
package1="mv src/main/resources/${log_name} src/main/resources/logback-spring.xml"
mvyml="mv src/main/resources/${yml_name} src/main/resources/application.yml"
package="mv src/main/resources/${properties_name} src/main/resources/application.properties"
echo "执行命令：$package1"

$(eval $package1)
echo "执行命令：$package"
$(eval $package)
$(eval $mvyml)

./mvnw clean package -Dmaven.test.skip=true
app_jar_name='storeservice-0.0.1-SNAPSHOT.jar'

app_launch_command="nohup java -Duser.timezone=GMT+08 -Xmx64M -Xms64M -jar $current_dir/target/${app_jar_name} > $current_dir/server.out 2>&1 &"

start(){
    echo "starting ....."
    if [ -n "$(get_pid)" ];then
        echo "appliaton has been started"
    else
        $(eval $app_launch_command)
    fi
}

stop(){
    echo "stopping ....."
    pid=$(get_pid)
    if [ -n "$pid" ];then
        kill -9 $pid
    else
        echo "appliaton has not been started"
    fi
}

restart(){
    stop
    start
}

get_pid() {
    ps -ef | grep ${app_jar_name} | grep -v grep | awk '{print $2}'
}
get_tail_pid() {
    ps -ef | grep 'tail' | grep -v grep | awk '{print $2}'
}
restart
#case "$1" in
#  start)
#    start
#    ;;
#  stop)
#    stop
#    ;;
#  status)
#    status -p "$mypidfile" $prog
#    ;;
#  restart)
#    restart
#    ;;
#  *)
#    echo $"Usage: $0 {start|stop|status|restart}"
#    exit 2
#esac