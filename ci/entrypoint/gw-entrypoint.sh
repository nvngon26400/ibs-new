#!/bin/sh

# Datadog APM トレーシング設定
export JAVA_OPTS="${JAVA_OPTS} \
  -javaagent:${DD_AGENT_HOME}/dd-java-agent.jar \
  -Ddd.trace.enabled=${DD_TRACE_ENABLED} \
  -Ddd.service=${DD_GW_SERVICE_NAME} \
  -Ddd.version=${DD_VERSION} \
  -Ddd.agent.host=${DD_AGENT_HOST} \
  -Ddd.service.mapping=${DD_SERVICE_MAPPING} \
  -Ddd.trace.otel.enabled=true \
  -Ddd.trace.db.client.split-by-instance=true \
  -Ddd.integration.jdbc.enabled=true \
  --enable-native-access=ALL-UNNAMED"

# JAVA_OPTSログ出力
echo "---------- [DEBUG] JAVA_OPTS: $JAVA_OPTS"

# メモリログ出力
RAW=$(cat /sys/fs/cgroup/memory.max 2>/dev/null || cat /sys/fs/cgroup/memory/memory.limit_in_bytes 2>/dev/null)
[ "$RAW" = "max" ] && RAW=0
echo "---------- [DEBUG] Memory Limit: $((${RAW:-0} / 1048576)) MB ---"

# アプリケーション起動
exec java $JAVA_OPTS -jar /app/helios-backend-gw.jar
