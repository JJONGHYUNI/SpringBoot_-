# 사용할 java 버전
FROM adoptopenjdk/openjdk11

# .gradle을 사용해 빌드를 실행하는 명령어
CMD ["./gradlew", "clean", "bootjar"]

# 컨테이너 내에  /tmp 디렉터리를 볼륨으로 설정
VOLUME /tmp

# JAR_FILE 위치 변수 선언
ARG JAR_FILE=build/libs/*.jar

# Jar 파일을 app.jar이라는 이름으로 컨테이너에 추가
COPY ${JAR_FILE} app.jar

# 컨테이너가 사용할 포트 지정
EXPOSE 8080

# 컨테이너가 실행될 때 기본적으로 실행될 명령어 설정
ENTRYPOINT ["java","-jar","/app.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]