cd `dirname $0`

export ACTION=$1
export BUSINESS_DATE=`date '+%Y%m%d'`
export JOB_ID="docker_compose"
export CONTAINER_NAME=${2:-all}

export LOCAL_LOG_PATH="/var/app/log/docker"
export DOCKER_COMPOSE_FILE="build.yml"

#処理日の取得
TODAY_S=`date '+%Y/%m/%d'`

mkdir -p "$LOCAL_LOG_PATH" > /dev/null 2>&1

if [ $? -ne 0 ]; then
    echo "エラー: ログディレクトリ $LOCAL_LOG_PATH を作成できません。権限を確認してください。"
    exit 1
fi

function echo_info_message() {
    printf "%s %s[%-5s][%s][%s]\n" $TODAY_S `date '+%H:%M:%S'` "INFO" ${JOB_ID} "${ACTION}" >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2> /dev/null
}

function echo_error_message() {
    printf "%s %s[%-5s][%s][%s]\n" $TODAY_S `date '+%H:%M:%S'` "ERROR" ${JOB_ID} "${ACTION}" >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2> /dev/null
}

#ACTIONをチェック
if [[ $ACTION != "start" ]] && [[ $ACTION != "stop" ]] && [[ $ACTION != "up" ]] && [[ $ACTION != "down" ]] && [[ $ACTION != "force-up" ]] && [[ $ACTION != "force-remove" ]] ; then 
    echo_error_message "Invalid action: $ACTION. Use start, stop, up, down, force-remove, or force-up."
    exit 1
fi

if [ $ACTION == "down" ] && [ $CONTAINER_NAME != "all" ]; then 
    echo_error_message "When action is 'down' , container_name can not be selected."
    exit 1
fi

if [ $CONTAINER_NAME == "all" ] ; then
    CONTAINER_RUN=""
    CONTAINER_LOG="All containers"
elif [ $CONTAINER_NAME == "frontend" ] ; then
    CONTAINER_RUN="helios-frontend"
    CONTAINER_LOG="FrontEnd Container (helios-frontend)"
elif [ $CONTAINER_NAME == "backend" ] ; then
    CONTAINER_RUN="helios-backend-gw helios-backend-ap"
    CONTAINER_LOG="BackEnd Container (helios-backend-gw helios-backend-ap)"
else
    CONTAINER_RUN=$CONTAINER_NAME
    CONTAINER_LOG="Container $CONTAINER_NAME"
fi

if [ $ACTION == "start" ] ; then 
#Docker Containerを起動
    echo_info_message "$CONTAINER_LOG starting." 
    docker compose -f $DOCKER_COMPOSE_FILE start $CONTAINER_RUN >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
    if [[ $? -eq 0 ]] ; then
        echo_info_message "$CONTAINER_LOG Started successfully." 
    else
        echo_error_message "$CONTAINER_LOG Failed to start."
        exit 1
    fi
elif [ $ACTION == "stop" ] ; then 
#Docker Containerを停止
    echo_info_message "$CONTAINER_LOG stopping." 
    docker compose -f $DOCKER_COMPOSE_FILE stop $CONTAINER_RUN >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
    if [[ $? -eq 0 ]] ; then
        echo_info_message "$CONTAINER_LOG stopped successfully." 
    else
        echo_error_message "$CONTAINER_LOG Failed to stop."
        exit 1
    fi
elif [ $ACTION == "up" ] ; then 
    #Docker Container create and start
    echo_info_message "$CONTAINER_LOG upping." 
    docker compose -f $DOCKER_COMPOSE_FILE up -d $CONTAINER_RUN >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
    if [[ $? -eq 0 ]] ; then
        echo_info_message "$CONTAINER_LOG upped successfully." 
    else
        echo_error_message "$CONTAINER_LOG Failed to up."
        exit 1
    fi
elif [ $ACTION == "force-remove" ] ; then 
    #Docker Image And Container force remove
    echo_info_message "$CONTAINER_LOG forcing remove."
    docker compose -f $DOCKER_COMPOSE_FILE rm -f -s -v $CONTAINER_RUN >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;

    if [ -n "$CONTAINER_RUN" ]; then
        IMAGE_NAME=$(docker compose -f $DOCKER_COMPOSE_FILE config --images $CONTAINER_RUN)
        if [ -n "$IMAGE_NAME" ]; then
            docker rmi -f $IMAGE_NAME >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
        fi
    else
        for IMAGE_NAME in $(docker compose -f $DOCKER_COMPOSE_FILE config --images); do
            docker rmi -f $IMAGE_NAME >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
        done
    fi
    if [[ $? -eq 0 ]] ; then
        echo_info_message "$CONTAINER_LOG forcing removed successfully." 
        docker image prune -f >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1
    else
        echo_error_message "$CONTAINER_LOG Failed to forcing remove."
        exit 1
    fi
elif [ $ACTION == "force-up" ] ; then 
    #Docker Image And Container force create and start
    echo_info_message "$CONTAINER_LOG forcing recreate."
    docker compose -f $DOCKER_COMPOSE_FILE rm -f -s -v $CONTAINER_RUN >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;

    if [ -n "$CONTAINER_RUN" ]; then
        IMAGE_NAME=$(docker compose -f $DOCKER_COMPOSE_FILE config --images $CONTAINER_RUN)
        if [ -n "$IMAGE_NAME" ]; then
            docker rmi -f $IMAGE_NAME >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
        fi
    else
        for IMAGE_NAME in $(docker compose -f $DOCKER_COMPOSE_FILE config --images); do
            docker rmi -f $IMAGE_NAME >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
        done
    fi

    docker compose -f $DOCKER_COMPOSE_FILE up -d --force-recreate --no-deps $CONTAINER_RUN >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
    if [[ $? -eq 0 ]] ; then
        echo_info_message "$CONTAINER_LOG forcing recreated successfully." 
        docker image prune -f >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1
    else
        echo_error_message "$CONTAINER_LOG Failed to forcing recreate."
        exit 1
    fi
else 
    #Docker Container stop and remove
    echo_info_message "$CONTAINER_LOG downing." 
    docker compose -f $DOCKER_COMPOSE_FILE down >> "${LOCAL_LOG_PATH}/${JOB_ID}_${BUSINESS_DATE}.log" 2>&1;
    if [[ $? -eq 0 ]] ; then
        echo_info_message "$CONTAINER_LOG downed successfully." 
    else
        echo_error_message "$CONTAINER_LOG Failed to down."
        exit 1
    fi
fi
