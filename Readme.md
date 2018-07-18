# POS 연동 API Guide
--------------------------------------------
POS 연동 API는 크게 POS 업체로 부터 요청을 받고 응답해주는 **''CALLBACK 서비스'**'와 POS 연동 주문과 상태를 3초에 한번 스케쥴링하여 연동 업체에 전송하는 **'Scheduled 서비스'**로 구성 되어있다

개발 진행 내역은 아래의 링크를 통해 확인하면 된다

 1. [레드마인 #868 외부 POS와의 배달 주문 연동 일감](http://appsis.iptime.org:5555/redmine/issues/868)
 
 2. [연동API 외부연동 Google Docs](https://docs.google.com/spreadsheets/d/18xN5GkNm8VKo4Xi5JY89CXbeEU4O9B7nay9GbrXbmIs/edit#gid=1477141118)

## 개발 소스
개발 프로젝트 소스는 https://bitbucket.org/o2osys/api-server-pos-dvry/src/master/  에서 **Clone** 받으면 된다

## 운영환경
1. 운영 서버: 네이버서버1(220.230.116.167) , 네이버서버2(220.230.116.177)
2. 배포 방법: `/opt/apache-tomcat-8.5.15/webapps`에 `pos.war`로 배포
3. 로그 경로: `/mnt/logs/pos` Alias 인 `lpos` 입력하면 해당 로그 폴더로 바로 이동한다 

## 목차
[TOC]

<a id="POS 연동 API 파일 구조"></a>
## POS 연동 API 파일 구조
```
│  Readme.md - 가이드 문서
│      
├─guide
├─libs - 로컬 디펜던시 파일 보관폴더(별도의 폴더 설정이 불편해 프로젝트내에 보관 및 설정)
│                      
└─src
   ├─main
   │  ├─java
   │  │  └─com
   │  │      └─o2osys
   │  │          └─pos
   │  │              │  ApiApplication.java - 스프링부트 설정 모듈(config설정 및 Main설정)
   │  │              │  AppInitializer.java - 스프링부트 메인 실행파일
   │  │              │  
   │  │              ├─common - 공통
   │  │              │  ├─constants
   │  │              │  │      Const.java - POS 프로젝트에만 해당하는 상수 파일
   │  │              │  │      Define.java - API 공통 상수
   │  │              │  │      
   │  │              │  ├─exception
   │  │              │  │      AlreadyCompleteException.java - 이미 처리된 사항 예외
   │  │              │  │      AuthException.java - 인증 에러 예외
   │  │              │  │      AuthKeyException.java - 연동 키인증 예외
   │  │              │  │      AuthRestConnectException.java - 인증 REST 서비스 예외
   │  │              │  │      DBCheckException.java - DB 점검 시간 예외
   │  │              │  │      PosConException.java - 포스 연동 예외
   │  │              │  │      RequestException.java - 포스 연동 요청값 예외
   │  │              │  │      RestConnectException.java - 포스 연동 REST 서비스 예외
   │  │              │  │      
   │  │              │  ├─filter
   │  │              │  │      CORSFilter.java - CORS(Cross-Origin Resource Sharing) 설정파일
   │  │              │  │      
   │  │              │  └─location - 주소 API 연동 모듈
   │  │              │          
   │  │              ├─config - 설정
   │  │              │  │  ControllerErrorHandler.java - ExceptionHandler 파일
   │  │              │  │  MyBatisConfig.java - 마이바티스 설정 파일
   │  │              │  │  RestResponseErrorHandler.java - 응답 에러 Handler 파일
   │  │              │  │  SwaagerConfig.java - 스웨거 API 설정 파일
   │  │              │  │  YamlPropertySourceFactory.java - Yaml 설정 파일
   │  │              │  │  
   │  │              │  └─dataSource - JNDI dataSource 설정
   │  │              │          DataSourceDetails.java
   │  │              │          JndiConfiguration.java - JNDI 메인 설정 파일
   │  │              │          JndiProperties.java
   │  │              │          Master.java - 마스터 어노테이션 매퍼에 셋팅해서 사용하면됨
   │  │              │          MultiDataSourcePlatformConfigurator.java
   │  │              │          MultiDataSourcePlatformConfiguratorImpl.java
   │  │              │          Slave.java - 슬레이브 어노테이션 매퍼에 셋팅해서 사용하면됨
   │  │              │          
   │  │              ├─controller
   │  │              │      PosController.java - POS 주문연동 Callback 서비스 컨트롤러
   │  │              │      
   │  │              ├─entity
   │  │              │  ├─callback - POS 주문연동 Callback 서비스 객체
   │  │              │  │      GetOrdInfoPos.java - 만나주문 조회 객체
   │  │              │  │      GetStOrdInfo.java - 만나주문 목록 조회
   │  │              │  │      PutOrdInfoPos.java - 배달요청 주문 전송 처리 객체
   │  │              │  │      ShopInfo.java - 만나가맹점 정보조회
   │  │              │  │      ShopList.java - 검색가맹점 목록
   │  │              │  │      
   │  │              │  └─rest - POS REST 연동 객체
   │  │              │          AddGoods.java - 주문추가상품 목록
   │  │              │          ConInfo.java - 연동대상 목록
   │  │              │          ConStatusInfo.java - 상태변경 연동대상 목록
   │  │              │          Goods.java - 주문상품 목록
   │  │              │          
   │  │              ├─mapper
   │  │              │      ConMapper.java - 스케쥴러 연동 매퍼
   │  │              │      LogMapper.java - 로그 매퍼
   │  │              │      PosMapper.java - 콜백 서비스 매퍼
   │  │              │      
   │  │              ├─packet
   │  │              │  │  ResCommon.java - 응답 공통 객체
   │  │              │  │  
   │  │              │  ├─callback - POS 주문연동 Callback 응답 서비스 객체
   │  │              │  │      ReqPosMessage.java 
   │  │              │  │      ResPosBody.java
   │  │              │  │      ResPosHeader.java
   │  │              │  │      ResPosMessage.java
   │  │              │  │      ResPosShopList.java
   │  │              │  │      
   │  │              │  └─sky - SKY 포스 연동 객체
   │  │              │      └─rest - REST 연동 요청객체
   │  │              │              InAddGoodsData.java - 스카이포스 추가 상품 목록
   │  │              │              InGoodsData.java - 스카이포스 상품 목록
   │  │              │              ReqSkyHeader.java - 스카이포스 REST 요청 헤더
   │  │              │              ReqSkySD0101.java - SD01_01_V01 : 만나주문 상태 전송
   │  │              │              ReqSkySD0201.java - SD02_01_V01 : 만나주문 전송
   │  │              │              ResSkyCommon.java - 스카이포스 REST 배송요청(API_DVRY_INPUT_ORDER) 응답 객체
   │  │              │              ResSkyHeader.java - 스카이포스 REST 수신 헤더
   │  │              │              
   │  │              ├─scheduler
   │  │              │      ScheduledTasks.java - POS 연동(주문,주문상태) REST 전송 스케쥴러 3초 마다 수행 
   │  │              │      
   │  │              └─service
   │  │                  │  PosCallbackService.java - POS 주문연동 Callback 서비스 인터페이스
   │  │                  │  PosScheduledService.java - POS 연동 스케쥴러 서비스 인터페이스
   │  │                  │  
   │  │                  └─impl
   │  │                          PosCallbackMapperServiceImpl.java - POS 주문연동 Callback DB맵핑 처리 서비스
   │  │                          PosCallbackServiceImpl.java - POS 주문연동 Callback 서비스
   │  │                          PosScheduledMapperServiceImpl.java - POS 연동 스케쥴러 DB맵핑 처리 서비스
   │  │                          PosScheduledServiceImpl.java - POS 연동 스케쥴러 서비스
   │  │                          SkyRestServiceImpl.java - 스카이포스 REST 서비스
   │  │                          
   │  └─resources
   │      │  application.yaml - 스프링부트 메인 설정 파일
   │      │  default-log-path.xml - 로그 경로 설정 파일
   │      │  default-log-setting.xml - 로그 기본 설정 파일
   │      │  logback-spring.xml - 로그 설정 파일
   │      │  mybatis-config.xml - 마이바티스 설정 파일
   │      │  
   │      ├─config
   │      │      config-dev.yaml - 개발 설정 파일
   │      │      config-local.yaml - 로컬 설정 파일
   │      │      config-prod1.yaml - 운영1(220.230.116.167) 설정 파일
   │      │      config-prod2.yaml - 운영2(220.230.116.177) 설정 파일
   │      │      config.yaml- 기본 설정 파일
   │      │      
   │      └──mapper
   │             ConMapper.xml - 스케쥴러 매퍼
   │             LogMapper.xml - 로그 매퍼
   │             PosMapper.xml - 콜백 서비스 매퍼
   │            
   │              
   └─test
       └─java
          └─com
              └─o2osys
                  └─pos
                      EncryptionMD5Key.java - 인증 키 생성 모듈
```
 
<a id="인증 키 생성 방법"></a>
## 1. 인증 키 생성 방법

```java
//인증키 생성 모듈 경로
/src/test/java/com/o2osys/pos/EncryptionMD5Key.java
```
인증 키 생성은 `contents` 변수에 **'KEY'**로 변환할 값들을 입력하고 수행하면 `MD5` 형태로 생성이 된다
현재는 간단한 메인 메서드로만 개발되어 있다.

```java
public class EncryptionMD5Key {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String[] contents = {
                "MANNA_TEST_POS주문연동_Easy",
                "MANNA_REAL_POS주문연동_Easy"
        };
        String hashCode;

        for(String content : contents){
            byte[] buffers = encryptSha1(content);
            hashCode = Base64Utils.encodeToString(buffers);
            System.out.println(content+" KEY : "+ hashCode);
        }

    }
    
    /**
     * 암호화 모듈
     * @Method Name : encryptSha1
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encryptMd5(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("MD5"); //SHA-1, SHA-256
        byte[] result = mDigest.digest(input.getBytes());
        return result;
    }

}
```

## 2. POS CALLBACK 서비스
POS CALLBACK 서비스는 만나와 연동된 외부 POS의 요청을 처리 한다.
[인증 키 생성 모듈](#인증 키 생성 방법)에서 생성한 인증키는 **config.yaml ** 설정 파일에 셋팅 되어 있다

설정 파일의 경우 부모 설정 파일에 셋팅된 값을 부모 설정 파일의 설정 사항이 메인이 되고
자식 설정 파일에 똑같은 설정을 하면 덮어씌워 진다
부모 설정 파일에 운영 설정 사항을 셋팅 해놓았다

POS CALLBACK 서비스의 경우 **주소 검색 API**와 연동하여 주소를 가져오는데 **주소 검색 API** 연동부분은
**주소 검색 API 가이드**에서 별도로 다룰것이므로 생략한다

### 1. POS CALLBACK 파일 구성
```java
/** 메인 비지니스 로직 **/
PosController.java //POS CALLBACK 컨트롤러
PosCallbackService.java //POS CALLBACK 서비스 인터페이스
PosCallbackServiceImpl.java //POS CALLBACK 서비스 메인
PosCallbackMapperServiceImpl.java //POS CALLBACK 서비스 맵핑 DB처리

/** 마이바티스 맵퍼 **/
PosMapper.xml //POS CALLBACK 서비스 SQL 매퍼

/** 설정 파일 **/
config.yaml //부모 설정 파일 운영버전의 인증키를 설정해놓음
config-dev.yaml //개발 설정 파일
config-local.yaml //로컬 설정 파일
config-prod1.yaml //운영1(220.230.116.167) 설정 파일
config-prod2.yaml //운영2(220.230.116.177) 설정 파일
```
자세한 파일 구조는 [POS 연동 API 파일 구조](#POS 연동 API 파일 구조) 를 참조하기 바란다

<a id="POS CALLBACK Swaager Api Docs"></a>
### 2. POS CALLBACK Swaager Api Docs
POS CALLBACK 서비스는 아래의 **Swagger Api Docs**를 통해 테스트 해 볼 수 있다. 

- [로컬 POS Swagger Api Docs](http://localhost:10060/swagger-ui.html): http://localhost:8080/swagger-ui.html
- [개발 POS Swagger Api Docs](http://220.230.122.21:8080/pos/swagger-ui.html): http://220.230.122.21:8080/pos/swagger-ui.html
- [운영 POS Swagger Api Docs](http://slb-341506.ncloudslb.com:8080/pos/swagger-ui.html): http://slb-341506.ncloudslb.com:8080/pos/swagger-ui.html


### 3. POS CALLBACK 테스트 정보
**[Swagger Api Docs](#POS CALLBACK Swaager Api Docs)**에서  **pos-controller** 클릭 후 아래의 샘플 요청 데이터 값을 수정하고 복사하여
**Try it out**을 클릭하고 `reqMessage`에 붙여넣기 한 다음 아랫쪽에 **Execute**버튼을 클릭해서
테스트 해볼 수 있습니다.

1. 업체구분코드
    - 유니타스 : 1001
    - SPC : 1002
    - SKY포스 : 1003
    - EasyPOS : 1004
    - 솔비POS : 1005

2. 테스트 서버키
    - SKY포스 TEST : Ypm6Y2CVMW8R5eN92XzzAQ==
    - 유니타스 TEST : 9OD7jorfWz/fU42TIYqUiQ==
    - SPC TEST : 4Kc7az6zsxDZJM7s/CM/QA==
    - EasyPOS TEST : bYYcJjwgk5s98usYK3/DLQ==
    - 솔비POS TEST : Dba6/pAFiUlSKMnCgjSLAw==

3. 운영 서버키
    - SKY포스 REAL : jL2obPEg+xbJzcJpIKfvhg==
    - 유니타스 REAL : ssJwZ3tRNMrx0pTfnLl32A==
    - SPC REAL : OAM86HpfQ3sRBSQI9VWDoQ==
    - EasyPOS REAL : Q+6MIkJtMQKIB5Uymm8f5w==
    - 솔비POS REAL : U53SJCxuz6JpwUs0ntbqfA==

```java
//가맹점 검색 목록 출력(CB01_01_V01) 요청 샘플
{
    "header":{
        "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
        "TRACE_NO":"201709061700001234",
        "SERVICE_CODE":"CB01_01_V01",
        "POS_TYPE_CODE":"1003"
    }
    ,"body":{
        "in_FIND_TYPE":"1",
        "in_FIND_VAL":"개발가맹점"
    }
}


//만나가맹점 매핑정보 처리(CB01_02_V01) 요청 샘플
{
    "header":{
        "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
        "TRACE_NO":"201709061700001234",
        "SERVICE_CODE":"CB01_02_V01",
        "POS_TYPE_CODE":"1003"
    }
    ,"body":{
        "in_POS_SHOP_CODE":"S000001",
        "in_SHOP_CODE":"S000001",
    "in_ACT_TYPE":"1"
    }
}

//가맹점 검색 목록 출력(CB01_03_V01) 요청 샘플
{
    "header":{
        "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
        "TRACE_NO":"201709061700001234",
        "SERVICE_CODE":"CB01_03_V01",
        "POS_TYPE_CODE":"1003"
    }
    ,"body":{
        "in_POS_SHOP_CODE":"S000001"
    }
}


//배달요청 주문 전송 (배달요청, 주문수정)(CB02_01_V01) 요청 샘플 SKYPOS 테스트용
{
  "header": {
    "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
    "TRACE_NO":"201709221500001234",
    "SERVICE_CODE":"CB02_01_V01",
    "POS_TYPE_CODE":"1003"
  },
  "body": {
    "in_ACT_TYPE": "1",
    "in_POS_SHOP_CODE": "9999999",
    "in_POS_ORD_CODE": "1111111111",
    "in_POS_ORD_DATE": "20170922",
    "in_ORD_CU_TEL": "01011112222",
    "in_ORD_CU_TEL_2": "01011112222",
    "in_ORD_MEMO": "TEST",
    "in_READY_TIME": 0,
    "in_EA_ADDR_1": "",
    "in_EA_ADDR_2": "",
    "in_EA_ADDR_3": "",
    "in_EA_ADDR_JB": "서울특별시 노원구 월계1동 389-1 삼능스페이스향 804호",
    "in_EA_ADDR_ST": "",
    "in_EA_ADDR_ETC": "",
    "in_EA_LAT_Y": "",
    "in_EA_LNG_X": "",
    "in_ORD_AMT": 30000,
    "in_PAY_AMT": 30000,
    "in_CHARGE_TYPE": "1",
    "in_PAY_TYPE": "1",
    "in_GOODS_NAMES": "치킨"
  }
}

//배달요청 주문 전송 (배달요청, 주문수정)(CB02_01_V01) 요청 샘플 솔비포스 테스트용
{
  "header": {
    "KEY":"Dba6/pAFiUlSKMnCgjSLAw==",
    "TRACE_NO":"201709221500001234",
    "SERVICE_CODE":"CB02_01_V01",
    "POS_TYPE_CODE":"1005"
  },
  "body": {
    "in_ACT_TYPE": "1",
    "in_POS_SHOP_CODE": "9999999",
    "in_POS_ORD_CODE": "1111111111",
    "in_POS_ORD_DATE": "20170922",
    "in_ORD_CU_TEL": "01011112222",
    "in_ORD_CU_TEL_2": "01011112222",
    "in_ORD_MEMO": "TEST",
    "in_READY_TIME": 0,
    "in_EA_ADDR_1": "",
    "in_EA_ADDR_2": "",
    "in_EA_ADDR_3": "",
    "in_EA_ADDR_JB": "서울특별시 노원구 월계1동 389-1 삼능스페이스향 804호",
    "in_EA_ADDR_ST": "",
    "in_EA_ADDR_ETC": "",
    "in_EA_LAT_Y": "",
    "in_EA_LNG_X": "",
    "in_ORD_AMT": 30000,
    "in_PAY_AMT": 30000,
    "in_CHARGE_TYPE": "1",
    "in_PAY_TYPE": "1",
    "in_GOODS_NAMES": "치킨"
  }
}

//배달요청 주문 전송 (주문취소)(CB02_01_V01) 요청 샘플
{
  "header": {
    "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
    "TRACE_NO":"201709061700001234",
    "SERVICE_CODE":"CB02_01_V01",
    "POS_TYPE_CODE":"1003"
  },
  "body": {
    "in_ACT_TYPE": "3",
    "in_POS_SHOP_CODE": "9999999",
    "in_POS_ORD_CODE": "1111111111",
    "in_POS_ORD_DATE": "20170922",
    "in_CANCEL_MEMO": "테스트취소"
  }
}

//만나주문 조회(CB02_02_V01) 요청 샘플
{
  "header": {
    "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
    "TRACE_NO":"201709221500001234",
    "SERVICE_CODE":"CB02_02_V01",
    "POS_TYPE_CODE":"1003"
  },
  "body": {
    "in_POS_SHOP_CODE": "9999999",
    "in_POS_ORD_CODE": "1111111111",
    "in_POS_ORD_DATE": "20170922"
  }
}

//만나주문 조회(CB02_02_V02) 요청 샘플
{
  "header": {
    "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
    "TRACE_NO":"201709221500001234",
    "SERVICE_CODE":"CB02_02_V02",
    "POS_TYPE_CODE":"1003"
  },
  "body": {
    "in_POS_SHOP_CODE": "9999999",
    "in_POS_ORD_CODE": "1111111111",
    "in_POS_ORD_DATE": "20170922",
    "in_ORD_NO": ""
  }
}

//만나주문 목록 조회(CB02_03_V01) 요청 샘플
{
  "header": {
    "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
    "TRACE_NO":"201709221500001234",
    "SERVICE_CODE":"CB02_03_V01",
    "POS_TYPE_CODE":"1003"
  },
  "body": {
    "in_POS_SHOP_CODE": "9999999",
    "in_LAST_SYNC_DATE": ""
  }
}

//만나주문 목록 조회(CB02_03_V02) 요청 샘플
{
  "header": {
    "KEY":"Ypm6Y2CVMW8R5eN92XzzAQ==",
    "TRACE_NO":"201709221500001234",
    "SERVICE_CODE":"CB02_03_V02",
    "POS_TYPE_CODE":"1003"
  },
  "body": {
    "in_POS_SHOP_CODE": "9999999",
    "in_LAST_SYNC_DATE": ""
  }
}
```

### 2. POS Scheduled 서비스
POS Scheduled 서비스는 3초 마다 만나 주문 데이터를 **PULL**하여 연동 업체에 **REST 전송** 한다
현재는 _주문_ 과 _주문상태_를 전송하는 두가지 _Scheduled_가 수행되고 있다
Scheduled 서비스의 경우에는 **운영2(220.230.116.177)** 에서만 수행되고 있고 
**운영1(220.230.116.167)**에서는 수행되고 있지 않다

현재 Scheduling을 하기위한 **연동 정보(연동업체 API 키 포함)**는 모두 프로시저를 통해서 받아오며 
`PosScheduledServiceImpl.java`에서 **업체구분코드(POS_TYPE_CD)**로 분기하여 **REST 전송**을 하도록 처리 되어있다

#### 1. POS Scheduled 서비스 파일 구성
```java
/** 메인 비지니스 로직 **/
ScheduledTasks.java //Scheduled 수행 서비스
PosScheduledService.java //Scheduled 비지니스 서비스 인터페이스
PosScheduledServiceImpl.java //Scheduled 비지니스 서비스
PosScheduledMapperServiceImpl.java //Scheduled 비지니스 서비스 맵핑 DB처리

/** 업체별 REST 서비스 **/
SkyRestServiceImpl.java //SKY 포스 REST 서비스

/** 마이바티스 맵퍼 **/
ConMapper.xml //Scheduled 서비스 SQL 매퍼

/** 설정 파일 **/
config.yaml //부모 설정 파일 운영버전의 인증키를 설정해놓음
config-dev.yaml //개발 설정 파일
config-local.yaml //로컬 설정 파일
config-prod1.yaml //운영1(220.230.116.167) 설정 파일
config-prod2.yaml //운영2(220.230.116.177) 설정 파일
```
자세한 파일 구조는 [POS 연동 API 파일 구조](#POS 연동 API 파일 구조) 를 참조하기 바란다

#### 2. POS Scheduled 서비스 구성
연동된 POS 업체 _가맹점 코드_로  주문처리시 주문데이터가
POS 연동 테이블에 정보가 들어가고 **POS Scheduled 서비스**에서 Pulling 하여 연동된 POS 업체로 주문 정보를 
**REST 전송**한다
주문 상태 변경시에도 마찬가지 이다

##### 연동상태 처리
연동 상태 처리는 프로시저가 아니고 `ConMapper.xml`의 별도의 SQL로 처리한다
```xml
 <!--  만나주문 상태(SD01_01) 연동 상태 처리 (0: 대기중, 1: 전송완료, 2: 전송실패) -->
 <update id="updateConStsStatus" parameterType="map">
     UPDATE MN_A1_TODAY_CON_POS_STS
     <set>
         <if test='CON_STATUS != null'> CON_STATUS = #{CON_STATUS}, </if>
         CON_DATE = SYSDATE,
         MOD_DATE = SYSDATE
     </set>
     WHERE CON_SEQ_NO = #{CON_SEQ_NO}
 </update>
 
 <!-- 만나주문 전송(SD02_01) 리턴값 업데이트 -->
 <update id="updateSD0201" parameterType="map">
      UPDATE MN_A1_TODAY
      <set>
          POS_ORD_CODE = #{POS_ORD_CODE}, 
          POS_ORD_DATE = #{POS_ORD_DATE},
          MOD_DATE = SYSDATE
         </set>
      WHERE ORD_NO = #{ORD_NO}
 </update>
```

#### 2. POS Scheduled 테스트 정보
POS Scheduled 서비스는 아래의 **외부 주문 연동 API**의  **Swagger Api Docs**를 통해 테스트 해 볼 수 있다. 

**외부 주문 연동 API 의 Swagger Api Docs**

- [로컬 MCS Swagger Api Docs](http://localhost:10060/swagger-ui.html): http://localhost:8080/swagger-ui.html
- [개발 MCS Swagger Api Docs](http://220.230.122.21:8080/mcs/swagger-ui.html): http://220.230.122.21:8080/mcs/swagger-ui.html
- [운영 MCS Swagger Api Docs](http://slb-341506.ncloudslb.com:8080/mcs/swagger-ui.html): http://slb-341506.ncloudslb.com:8080/mcs/swagger-ui.html


##### 1. 테스트 주문 URL 가져오기
네이버 **운영1(220.230.116.167), 운영2(220.230.116.177)** 서버에 접속해서 아래의 Alias 명령어로
외부 주문 연동 API 로그 폴더로 이동한 뒤 URL을 가져오는 grep 명령어를 입력하면 주문 URL들을 가져올수있다
```dos
>> lmcs
>> egrep "] GET URL : " mcs.log
```

##### 2. 테스트 주문 처리 
**외부 주문 연동 API 의 Swagger Api Docs**로 접속해서 주문 테스트를 수행할 controller를 선택하고
**Try it out**을 클릭하고 `reqMessage`을 아래와 같이 **주문테스트할 URL**과 POS 연동 업체가 맵핑된 
`user_id`와 `st_code`를 입력하고 아랫쪽에 **Execute**버튼을 클릭해서 테스트 해볼 수 있습니다.

```java
{
  "message": "http://www.yogiyo.co.kr/s/r3AGV2Vhf4jk/"
}
```
