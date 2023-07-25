#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=SpringBoot-OrderServiceV4-JPA

# git clone 경로로 이동
cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

# git 에서 최신 버전을 받는다.
git pull

echo "> 프로젝트Build 시작"

# 빌드를 실행한다.
./gradlew build -x test

echo "> step1 디렉토리 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

# buil 파일을 복사한다.
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

# pgrep은 process id만 추출한다.
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동중인 애플리케이션: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill =15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -t $REPOSITORY/ | grep jar | tail -n 2)

echo "> JAR Name: $JAR_NAME"

nohup java -jar -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-real-db.yml,classpath:/application-real.yml -Dspring.profiles.active=real $REPOSITORY/$JAR_NAME 2>&1 &
