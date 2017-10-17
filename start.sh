nohup java -server -Xms1G -Xmx1G -Xmn512M \
-XX:MaxDirectMemorySize=512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m \
-XX:-UseBiasedLocking -XX:-UseCounterDecay -XX:AutoBoxCacheMax=10240 \
-XX:+UseParallelOldGC \
-XX:+PerfDisableSharedMem -XX:+AlwaysPreTouch  \
-XX:-OmitStackTraceInFastThrow -XX:MaxTenuringThreshold=2 -XX:+ExplicitGCInvokesConcurrent -XX:+ParallelRefProcEnabled \
-jar pigeon-demo-server-1.0.0.jar > start.log 2>&1 < /dev/null &
