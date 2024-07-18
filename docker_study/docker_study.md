## 도커 공부 내용

### 공부하는 이유
- 이번에 서버를 EC2로 마이그레이션 하면서 EC2의 기존 환경대로 이것저것 설치하는 데 시간이 꽤 걸렸다.
- **Docker**를 사용하면 이런 번거로운 작업없이 docker를 다운받고 build한 docker image 파일로 배포가 가능하다고 한다.
- 또, 마이그레이션 할 일이 있을 지는 모르겠지만 알아두면 개발 서버와 배포 서버의 분리 등등 이점이 많을 것 같았다.

### 1. 도커란
- 도커는 컨테이너 기반 가상화 도구.
- 가상화란 하드웨어를 효율적으로 활용하기 위해 가상의 머신을 만드는 기술이다.
- 컨테이너란 호스트 os를 그대로 사용하면서 프로세스를 격리해 독립된 환경을 만드는 기술이다.

**즉, 도커는 컨테이너라는 독립된 환경을 만들어서 하드웨어를 효율적으로 사용하고 환경에 구애받지 않고 편리하게
서비스를 배포할 수 있게 해준다.**

### 2. 도커의 장점
- 가상화 도구로, 가상 OS와 비교하자면 가상 OS는 OS를 함께 탑재하여 프로그램이 무거운
  도커는 OS 설치 없이 도커 엔진 위에서 독립된 환경만 제공해서 프로그램이 가볍고 성능이 기존 OS와 거의 동일하다.
- 서버 마이그레이션, 확장 등이 필요할 때 새로운 서버에 환경을 설치할 필요없이 도커를 다운받고 도커 이미지 파일만 구동해주면
  정상적으로 돌아가기 때문에 확장과 마이그레이션이 편하다.
- 독립된 환경을 제공하므로, 다른 환경 설정이 필요한 여러 서비스를 구동할 때 충돌이 일어나지 않는다.

등등 또 써보면서 알아보자.

### 3. 사용 방법
- 도커를 로컬과 서버 환경에 다운 받는다.
- Dockerfile을 root 폴더에 만들어 docker image build를 진행한다.
- dockerfile을 run 해준다.

기본적인 구동은 위와 같은 과정으로 이루어지는 것 같다.
현재 배포해야할 서버는 여러가지 설정이 더 필요할 것 같아서 공부하면서 알아보자.

### 3.1 Dockerfile 예시 및 설명
    FROM amazoncorretto:11-alpine-jdk # 필요한 자바 버전
    ARG JAR_FILE=build/libs/*.jar # 빌드한 자바 파일 위치 변수로 저장
    ARG ~~ # 추가 외부 주입할 변수 추가해도 됨.
    COPY ${JAR_FILE} app.jar # JAR_FILE 주소에 있는 파일을 DOCKER image로 복사
    ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"] # 컨테이너가 생성되고 최초로 수행할 명령어

### 3.2 명령어
    docker build # Dockerfile 정보를 이용해 docker image 생성
    docker run  # 도커 이미지를 실행해 도커 컨테이너를 만들고 수행
      -p 8080:8080 # 도커 내부 포트와 로컬 포트 연결 (내 컴퓨터에 8080포트로 들어오면 docker 8080 포트로 연결)
      -e PROFILES=local # 외부 주입할 변수 입력
    docker logs {container_name} # 컨테이너 log 보기
    docker exec -it {container_name} bash # 해당 컨테이너 bash로 접속
주로 사용하는건 이정도??

### 4. 도커 컴포즈
- 도커의 여러 컨테이너가 하나의 애플리케이션으로 동작할 때 (MySQL, Redis, SpringBoot 등등) 통합 관리 도구

### 4.1 사용법
    services: # 하나의 서비스 단위 (이 안에 있는 것들을 묶어서 실행)
      db:
        container-name: mydb # 이름 정하기
        images: mysql:latest # 직접 만든 image를 사용해도 되고 docker에 내장된 쿼리를 이와 같이 가져와도 됨
        volumes: db:/var/lib/mysql # volume은 db 사용 시 컨테이너가 재생성 되더라도 데이터가 남아있게 하기 위해 사용
        restart: unless-stopped # 부팅시 자동 컨테이너 시작, (no, on-failure, always 등의 옵션이 있음)
        environment: # 환경 설정
        - MYSQL_ROOT_PASSWORD=~~
        networks: #network 설정 (공유할 애플리케이션들끼리 공통된 이름)
      my-app:
        container-name: my-app
        restart: on-failure ( 정상적으로 종료되지 않은 경우에만 재시작 )
        build: # 위에서는 image로 여기는 dockerfile 이용해 build한 이미지 사용
          context: ./
          dockerfile: Dockerfile
        ports:
          - "8080:8080" # 로컬:도커 포트 설정
        environment:
          SPRING_DATASOURCE_URL: ~~ # 환경 주입
        depends_on: 의존성 ( 아래 작업 후 진행된다는 의미)
          - db
        networks:
          - test_network
      networks:
        test_network:
이와 같이 사용하는데 cmd로 한다면 매 번 이렇게 긴 명령을 내려야하는 반면 compose파일로 정리해두면 지속적으로 사용할 수 있다.
