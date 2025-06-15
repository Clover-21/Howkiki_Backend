# 🤖 하우키키
>  고객의 상황을 반영한 응대와 주문 결제 자동화를 제공하는 휴먼터치 AI 챗오더
<br>

### 📌 [캡스톤디자인 프로젝트 23팀] 하우키키 백엔드 레포지토리입니다.
<br>

## 🔧 기술 스택
### 📌 Backend  
![Java](https://img.shields.io/badge/java%20-007396?style=for-the-badge&logo=java&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) 
![JPA](https://img.shields.io/badge/JPA-000000?style=for-the-badge&logo=&logoColor=white) 

### 📌 Database 
![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white) 

### 📌 DevOps & Infrastructure  
![Nginx](https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) 
![AWS](https://img.shields.io/badge/aws-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)  

### 📌 Payment API
![PortOne](https://img.shields.io/badge/PortOne-FF804A?style=for-the-badge&logoColor=white)

### 📌 Version Control  
![GitHub](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white) 
![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white)  

###  🔎 Tech Details
- **Language:** Java 17  
- **Framework:** Spring Boot 3.4  
- **Build Tool:** Gradle  
- **Database:** MySQL, AWS S3  
- **Payment API:** PortOne 결제 API  
- **단방향 통신:** Server-Sent Events (SSE)
- **Deployment :** AWS EC2, AWS RDS, Nginx, GitHub Actions + AWS CodeDeploy 기반 CI/CD

<br>

## 📁 Source Code 설명
### 1️⃣ 프로젝트 구조
- 본 프로젝트는 기능별 도메인 구조(Domain-Driven Design)를 기반으로 구성되어 있으며, 각 도메인은 `menu`, `notification`, `order`, `pay`, `store`, `suggestion` 으로 나뉩니다.
- 각 도메인 내에는 controller, service, repository, dto, entity로 분리하여 구현하였습니다. 
  
``` bash
├───main
│   ├───generated
│   ├───java
│   │   └───clovar
│   │       └───howkiki
│   │           ├───domain
│   │           │   ├───menu # 메뉴 조회 및 등록 관련 기능
│   │           │   │   ├───controller
│   │           │   │   ├───dto
│   │           │   │   │   ├───requestDto
│   │           │   │   │   └───responseDto
│   │           │   │   ├───entity
│   │           │   │   ├───repository
│   │           │   │   └───service
│   │           │   ├───notification # 알림 전송 및 수신 관련 로직
│   │           │   │   ├───controller
│   │           │   │   ├───dto
│   │           │   │   ├───entity
│   │           │   │   ├───repository
│   │           │   │   └───service
│   │           │   ├───order # 주문 생성 및 관리 기능
│   │           │   │   ├───controller
│   │           │   │   ├───dto
│   │           │   │   │   ├───requestDto
│   │           │   │   │   └───responseDto
│   │           │   │   ├───entity
│   │           │   │   ├───repository
│   │           │   │   └───service
│   │           │   ├───pay # 결제 요청 및 검증 기능 (PortOne 연동)
│   │           │   │   ├───controller
│   │           │   │   ├───dto
│   │           │   │   ├───entity
│   │           │   │   ├───repository
│   │           │   │   └───service
│   │           │   ├───store # 매장 정보 관리 기능
│   │           │   │   ├───controller
│   │           │   │   ├───dto
│   │           │   │   │   ├───request
│   │           │   │   │   └───response
│   │           │   │   ├───entity
│   │           │   │   ├───repository
│   │           │   │   └───service
│   │           │   └───suggestion # 사용자 건의사항 생성 및 조회 기능
│   │           │       ├───controller
│   │           │       ├───dto
│   │           │       ├───entity
│   │           │       ├───repository
│   │           │       └───service
│   │           └───global
│   │               ├───config # 전역 설정 (CORS, S3, PortOne, Security 등)
│   │               ├───entity # 공통 엔티티 (BaseEntity)
│   │               ├───exception # 커스텀 예외 및 전역 예외 처리 핸들러
│   │               └───response # API 응답 포맷 통일 클래스 (ApiResponse)
│   └───resources
└───test
    └───java
        └───clovar
            └───howkiki
```
<br>

### 2️⃣ 각 디렉토리 설명
- `controller/`: REST API 요청을 처리하는 엔드포인트
- `service/`: 비즈니스 로직 처리
- `repository/`: JPA 기반 DB 접근 계층
- `dto/`: 계층 간 데이터 전달 객체 (요청, 응답 분리됨)
- `entity/`: 데이터베이스 테이블과 매핑되는 도메인 모델
- `global/config/`: 전역 설정 클래스 (Spring 설정, S3, PortOne 등)
- `global/exception/`: 커스텀 예외 정의 및 예외 처리 핸들러
- `global/response/`: API 응답 구조 통일을 위한 래퍼 클래스
<br>

### 3️⃣ 기능 API 명세서
각 도메인 별 자세한 기능 설명은 아래의 **API 명세서**를 참고해주세요. 
<br>
👉 [하우키키 - API 명세서](https://bronzed-rainforest-9f0.notion.site/API-213cb46d5831804aa6c3e20bad185160?source=copy_link)

<br>

## 🚀 How to install & Run
하우키키 백엔드 서버를 로컬에서 실행하기 위한 절차는 다음과 같습니다. 

### 1️⃣ 프로젝트 클론
``` bash
git clone https://github.com/Clover-21/Howkiki_Backend.git
cd Howkiki_Backend
```
<br>

### 2️⃣ MySQL 설정 
본 프로젝트는 MySQL 8.0 이상을 사용하며, 기본적으로 `howkiki_db`라는 이름의 데이터베이스를 사용합니다.
이미 로컬 connection이 있는 경우 이 과정은 넘어가셔도 됩니다.
> ⚠️ MySQL의 사용자 이름과 비밀번호는 각자의 로컬 설정에 따라 다르므로 본인의 환경에 맞게 작성해 주세요.

1. 🐬 MySQL Workbench 실행 후, 좌측 상단의 **+ 버튼(New Connection)** 클릭
2. **Connection Name**: `howkiki_local` (자유롭게 설정 가능)
3. **Username**: `root` 또는 본인 설정
4. **Password**: 저장해둔 비밀번호 입력
5. **Test Connection** 버튼 클릭 → 성공 확인 후 저장
6. cf) 아래 명령어로 데이터베이스를 수동으로 생성할 수 있습니다 (프로젝트 실행시 자동 생성되므로 옵션사항):
 ```sql
CREATE DATABASE IF NOT EXISTS howkiki_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
<br>

### 3️⃣ 설정 파일 생성 (application.yml)
- src/main/resource 위치에 application.yml 파일을 아래 형식으로 생성합니다.
- 데이터베이스, AWS S3 연동, 외부 결제 API 연동을 위한 정보를 설정합니다.
- 여기서 [ ] 부분에 실제 값 또는 키 값을 작성해야 합니다.
- ⚠️ **이 파일은 .gitignore에 포함되어야 하며, 절대 커밋하면 안됩니다.**

``` yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/howkiki_db?createDatabaseIfNotExist=true&characterEncoding=UTF-8&characterSetResults=UTF-8
    username: [YOUR_DB_USERNAME]
    password: [YOUR_DB_PASSWORD]
  jpa:
    hibernate:
      ddl-auto: update
    generate-ddl: true
    show-sql: true
  servlet:
    multipart:
      max-file-size: 10MB # 업로드할 수 있는 개별 파일 최대 크기. 기본 1MB
      max-request-size: 10MB  # multipart/form-data 요청의 최대 허용 크기. 기본 10MB

# AWS S3
cloud:
  aws:
    credentials:
      access-key: [AWS ACCESS KEY]
      secret-key: [AWS SECRET KEY]
    region:
      static: ap-northeast-2 # 버킷의 리전
    s3:
      bucket: howkiki-img-bucket  # 버킷 이름
    stack:
      auto: false

# PortOne
portone:
  api-key: [포트원 API KEY]
  api-secret: [포트원 API SECRET]
```
<br>

### 4️⃣ 애플리케이션 실행
``` bash
./gradlew bootRun
```
<br>

## 💿 Sample Data 
- 아래의 샘플 SQL을 통해 초기 `Store`, `Menu` 데이터를 삽입할 수 있습니다. <br>
(API를 통해 생성할 수도 있으나 테스트 편의를 위해 제공)
- 파일 위치: `src/main/resources/sample-data.sql`

#### 포함된 내용
- `stores` 테이블에 매장 1개 생성
- `menu` 테이블에 카테고리별 25개 메뉴 데이터 삽입
- 모든 데이터의 `created_at`, `modified_at`은 `NOW()`로 설정

#### 사용 방법
1. MySQL에서 `howkiki_db` 데이터베이스 선택
2. `sample-data.sql` 파일을 불러와 실행
   
<br>

## ⚙️ How to Test
- 하우키키 백엔드 서버의 주요 기능은 REST API 형태로 제공됩니다.  
- Postman을 통해 각 기능을 테스트할 수 있습니다.
<br>

### 1️⃣ Postman 환경 설정
1. [Postman](https://www.postman.com/downloads/)을 설치하거나 웹 버전 사용
2. 서버 실행 후 `http://localhost:8080`을 기준으로 요청
> 서버가 정상적으로 실행 중이어야 합니다 (`./gradlew bootRun` 또는 `jar` 실행)
<br>

### 2️⃣ API 명세서 참고
👉 [하우키키_API 명세서(Notion)](https://bronzed-rainforest-9f0.notion.site/API-213cb46d5831804aa6c3e20bad185160?source=copy_link)
- 명세서에는 API 설명, 요청 URL, 요청 바디, 응답 형태, 인증 방식 등이 포함되어 있습니다.
<br>

### 3️⃣ 테스트 예시 화면
- 주문 생성 예시
![하우키키_주문생성예시](https://github.com/user-attachments/assets/6c5613bf-7a90-47a9-846c-0244ba350a3c)

<br> 

## 💾 ERD
![하우키키_ERD)](https://github.com/user-attachments/assets/acd196e2-f447-490a-b264-70e7c01ca6f9)

<br>

## ✏️ SW 구조도
![하우키키_SW구조도](https://github.com/user-attachments/assets/de43cbfc-c65b-4474-8839-6574c2ba3b6b)

<br>

## 📚 Description of Used Open Source
본 프로젝트는 다음과 같은 오픈소스 라이브러리를 기반으로 개발되었습니다.

| 라이브러리 | 설명 | 사용 목적 |
|------------|------|------------|
| [Spring Boot](https://spring.io/projects/spring-boot) | Java 기반 애플리케이션 프레임워크 | 웹 서버 및 애플리케이션 구조 제공 |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | ORM(Object-Relational Mapping) 지원 | Java 객체 ↔ DB 테이블 매핑 자동화 |
| [Spring Security](https://spring.io/projects/spring-security) | 인증/인가 프레임워크 | 보안 설정 및 API 보호 |
| [Lombok](https://projectlombok.org/) | 반복 코드 생략을 위한 애너테이션 처리기 | Getter/Builder 자동 생성 |
| [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) | MySQL용 JDBC 드라이버 | DB 연결 |
| [AWS SDK for Java (S3)](https://github.com/aws/aws-sdk-java) | AWS 서비스 연동 | 이미지 파일 S3 업로드 및 버킷 관리 |
| [PortOne REST Client](https://github.com/iamport/iamport-rest-client-java) | 포트원 결제 연동 모듈 | 결제 API 호출 및 상태 검증 |
| [Hibernate](https://hibernate.org/) | JPA 구현체 | Entity 기반 DB 조작 기능 구현 |


