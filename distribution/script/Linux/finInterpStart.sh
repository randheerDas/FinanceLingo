#!/bin/sh
## Launch the Financial DSL interpreter

# specify the JVM paramters
MEMORY=1024m
JVM_MEM_OPT="-Xms$MEMORY -Xmx$MEMORY -XX:+HeapDumpOnOutOfMemoryError"
JVM_GC_OPT="-XX:+PrintGCDetails -XX:+PrintGCDateStamps" 

# Fetch the path to the lib directory
LIBDIR=`(cd ../../lib; pwd)`
#echo "LIBDIR IS $LIBDIR"

# Set the Classpath. Add the jars in the lib folder to the classpath
for f in $LIBDIR/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done

# Add the Classes folder
CLSDIR=`(cd ../../classes; pwd)`
CLASSPATH=$CLASSPATH:$CLSDIR

#echo "CLASSPATH IS $CLASSPATH"

if [ -z "$JAVA_HOME" ] ; then
  echo "Warning: JAVA_HOME environment variable is not set."
  echo "Please set JAVA_HOME environment variable to point to JDK 7 or above."
fi

if [ -f $JAVA_HOME/bin/java ]; then
  JAVAEXEC=$JAVA_HOME/bin/java
else
  JAVAEXEC=java
fi

#echo "JAVA EXECUTABLE IS $JAVAEXEC"
JAVA_OPTS="$JVM_MEM_OPT $JVM_GC_OPT"
main_class=app.FinancialApp

$JAVAEXEC -server $JAVA_OPTS -cp $CLASSPATH $main_class
