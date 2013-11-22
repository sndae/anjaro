#!/bin/sh
#de.anjaro.template.SocketInboundRobot
#export ANJARO_HOME=/opt/anjaro
export ANJARO_HOME=~/git/anjaro/anjaroParent/anjaroInstallation
VERSION=0.0.1-SNAPSHOT
ANJARO_CONFIG=-Danjaro.config.class=$1

JAVA_PARAMS=-Djava.library.path=$ANJARO_HOME/lib
JAVA_PARAMS="$JAVA_PARAMS -Ddaemon.file=$ANJARO_HOME/bin/anjaro.pid"
JAVA_PARAMS="$JAVA_PARAMS -Djava.util.logging.config.file=$ANJARO_HOME/conf/logging.properties"
JAVA_PARAMS="$JAVA_PARAMS -Danjaro.home=$ANJARO_HOME" 
DEBUG=-agentlib:jdwp=transport=dt_socket,address=8999,server=y,suspend=n
JAVA_PARAMS="$JAVA_PARAMS $ANJARO_CONFIG $DEBUG"

CLASSPATH=$ANJARO_HOME/conf
CLASSPATH=$CLASSPATH:$ANJARO_HOME/lib/anjaroAPI-$VERSION.jar
CLASSPATH=$CLASSPATH:$ANJARO_HOME/lib/anjaroRaspberry-$VERSION.jar
CLASSPATH=$CLASSPATH:$ANJARO_HOME/lib/anjaroControlCenter-$VERSION.jar
CLASSPATH=$CLASSPATH:$ANJARO_HOME/lib/bluecove-2.1.0-.jar:$ANJARO_HOME/lib/bluecove-gpl-2.1.0.jar

echo "java -cp $CLASSPATH $JAVA_PARAMS de.anjaro.bootstrap.AnjaroStarter"
launch_daemon()
{
  /bin/sh << EOF
java -cp $CLASSPATH $JAVA_PARAMS de.anjaro.bootstrap.AnjaroStarter <&- & 
     pid=\$!
     echo \${pid}
EOF
}

daemon_pid=`launch_daemon`
mypid=${daemon_pid%%Listening for transport dt_socket at address: 8999}
echo $mypid
if ps -p $mypid >/dev/null 2>&1
then
  # daemon is running.
  echo "Daemon is started..."
  echo $mypid > $ANJARO_HOME/bin/anjaro.pid
else
  echo "Daemon did not start."
fi

