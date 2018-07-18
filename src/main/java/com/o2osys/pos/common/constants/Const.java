package com.o2osys.pos.common.constants;

/**
   @FileName  : ConstPos.java
   @Description : POS 연동상수 정의
   @author      : KMS
   @since       : 2017. 9. 4.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 4.     KMS            최초생성

 */
public interface Const {

    /**
     * [업체구분코드]
     * UNITAS : 1001
     * SPC : 1002
     * SKY포스 : 1003
     **/
    interface POS_TYPE_CODE {
        /** 유니타스 : 1001*/
        String UNITAS = "1001";
        /** SPC : 1002 */
        String SPC = "1002";
        /**  SKY포스 : 1003 */
        String SKY = "1003";
        /**  SKY포스 : 1004 */
        String EASY = "1004";
        /**  솔비포스 : 1005 */
        String SOLBI = "1005";
        /**  OK포스 : 1006 */
        String OKPOS = "1006";
    }

    /** 연동_상태 (0: 대기중, 1: 전송완료, 2: 전송실패, 3: 전송취소대기(대기중으로 자동변경))) */
    interface STATUS {
        /** 연동_상태 (0: 대기중) */
        final String WAIT = "0";
        /** 연동_상태 (1: 전송완료) */
        final String SUCCESS = "1";
        /** 연동_상태 (2: 전송실패) */
        final String FAIL = "2";
        /** 연동_상태 (3: 전송취소대기(대기중으로 자동변경)) */
        final String CANCEL = "3";
    }


    /*
    SKY포스 TEST : Ypm6Y2CVMW8R5eN92XzzAQ==
    유니타스 TEST : 9OD7jorfWz/fU42TIYqUiQ==
    SPC TEST : 4Kc7az6zsxDZJM7s/CM/QA==

    SKY포스 REAL : jL2obPEg+xbJzcJpIKfvhg==
    유니타스 REAL : ssJwZ3tRNMrx0pTfnLl32A==
    SPC REAL : OAM86HpfQ3sRBSQI9VWDoQ==
     */

    /** REST 서비스 상수 */
    interface REST {

        /** SKY POS 상수 */
        interface SKY {

            interface SERVICE {
                /** SKY POS 연동 API 서비스 코드 */
                interface CODE {

                    interface SD01 {
                        /** 만나주문 상태 전송(SD01_01)
                         * 만나주문의 배달 및 주문 상태 전송
                         * 고객사에서 필요 시 전송
                         **/
                        final String _01 = "SD01_01";
                    }

                    interface SD02 {
                        /** 만나주문 전송(SD02_01)
                         * 만나 서비스를 통해 등록된 주문 정보 전송
                         * 고객사에서 필요 시 전송
                         **/
                        final String _01 = "SD02_01";
                    }
                }

                /** SKY POS 연동 API 서비스 코드명 */
                interface NAME {

                    interface SD01 {
                        /** 만나주문 상태 전송(SD01_01)
                         * 만나주문의 배달 및 주문 상태 전송
                         * 고객사에서 필요 시 전송
                         **/
                        final String _01 = "만나주문 상태 전송";
                    }

                    interface SD02 {
                        /** 만나주문 전송(SD02_01)
                         * 만나 서비스를 통해 등록된 주문 정보 전송
                         * 고객사에서 필요 시 전송
                         **/
                        final String _01 = "만나주문 전송";
                    }
                }
            }


        }
    }

    /** POS연동 API 서비스 코드 */
    interface SERVICE {

        /** POS연동 API 서비스 코드 */
        interface CODE {
            interface CB01 {
                /** 만나가맹점 검색(CB01_01)
                 * 만나 가맹점 검색 목록 출력
                 * 별도 가맹점 매핑작업 시 필요없음
                 **/
                final String _01 = "CB01_01";
                /** 고객사 가맹점 정보 등록/삭제(CB01_02_V01)
                 * 고객사와 만나 서비스 간 가맹점 매핑정보 등록/삭제
                 * 별도 가맹점 매핑작업 시 필요없음
                 **/
                final String _02 = "CB01_02";
                /** 만나가맹점 정보조회(CB01_03)
                 * 매핑된 만나 가맹점 정보 조회
                 * 만나 서비스의 가맹점 캐쉬를 사용할 경우 정보 조회
                 **/
                final String _03 = "CB01_03";
            }

            interface CB02 {
                /** 만나주문 등록 요청(CB02_01)
                 * 만나 서비스로 배달 요청
                 * 배달주문 등록
                 **/
                final String _01 = "CB02_01";

                /** 배달요청 주문 전송 처리_구분 (1: 배달요청, 2: 주문수정, 3: 주문취소) */
                interface _01_CODE {
                    /** 처리_구분 (1: 배달요청) */
                    String REQUEST = "1";
                    /** 처리_구분 (2: 주문수정) */
                    String MODIFY = "2";
                    /** 처리_구분 (3: 주문취소) */
                    String CANCEL = "3";
                }

                /** 만나주문 조회(CB02_02)
                 * 배달 요청된 주문 정보 조회
                 * 고객사 클라이언트에서 필요 시 조회
                 **/
                final String _02 = "CB02_02";

                /** 만나주문 목록 조회(CB02_03)
                 *  배달 요청된 주문 정보 조회
                 *  고객사 클라이언트에서 필요 시 조회
                 */
                final String _03 = "CB02_03";


            }

        }

        /** POS연동 API 서비스 코드명 */
        interface NAME {
            interface CB01 {
                /** 만나가맹점 검색(CB01_01)
                 *  만나 가맹점 검색 목록 출력
                 *  별도 가맹점 매핑작업 시 필요없음
                 **/
                final String _01 = "만나가맹점 검색";
                /** 고객사 가맹점 정보 등록/삭제(CB01_02)
                 *  고객사와 만나 서비스 간 가맹점 매핑정보 등록/삭제
                 *  별도 가맹점 매핑작업 시 필요없음
                 **/
                final String _02 = "고객사 가맹점 정보 등록/삭제";
                /** 만나가맹점 정보조회(CB01_03)
                 *  매핑된 만나 가맹점 정보 조회
                 *  만나 서비스의 가맹점 캐쉬를 사용할 경우 정보 조회
                 **/
                final String _03 = "만나가맹점 정보조회";
            }

            interface CB02 {
                /** 만나주문 등록 요청(CB02_01)
                 *  만나 서비스로 배달 요청
                 *  배달주문 등록
                 **/
                final String _01 = "만나주문 등록 요청";

                /** 배달요청 주문 전송 처리_구분 (1: 배달요청, 2: 주문수정, 3: 주문취소) */
                interface _01_NAME {
                    /** 처리_구분 (1: 배달요청) */
                    String REQUEST = "배달요청";
                    /** 처리_구분 (2: 주문수정) */
                    String MODIFY = "주문수정";
                    /** 처리_구분 (3: 주문취소) */
                    String CANCEL = "주문취소";
                }

                /** 만나주문 조회(CB02_02)
                 *  배달 요청된 주문 정보 조회
                 *  고객사 클라이언트에서 필요 시 조회
                 **/
                final String _02 = "만나주문 조회";

                /** 만나주문 목록 조회(CB02_03)
                 *  배달 요청된 주문 정보 조회
                 *  고객사 클라이언트에서 필요 시 조회
                 */
                final String _03 = "만나주문 목록 조회";

            }

        }


    }

    /** 응답 코드 및 메세지 */
    interface RES {

        /** 취소 구분에 따른 취소 코드 리턴 */
        interface CANCEL {

            interface CODE {

                /** 주문취소 요청 시 전체 주문취소 */
                final String ALL_ORDER_CANCEL = "0000";
                /** 주문취소 요청 시 주문만 취소 (배달정산은 취소가 안된 경우) */
                final String JUST_ORDER_CANCEL = "0001";

            }

            interface MSG {

                /** 주문취소 요청 시 전체 주문취소 */
                final String ALL_ORDER_CANCEL = "주문취소 요청 시 전체 주문취소";
                /** 주문취소 요청 시 주문만 취소 (배달정산은 취소가 안된 경우) */
                final String JUST_ORDER_CANCEL = "주문취소 요청 시 주문만 취소 (배달정산은 취소가 안된 경우)";

            }

        }

        interface CODE {

            /** 정상(0000) */
            final String OK = "0000";
            /** Request 파라미터 오류(0001) */
            final String REQ_ERROR="0001";
            /** 처리거절(0002) */
            final String FAIL = "0002";
            /** 기 처리상태(0003) */
            final String ALREADY_COMPLETE = "0003";
            /** 키값인증 오류(0004) */
            final String AUTH_ERROR="0004";
            /** 주문상태 불명(0005) */
            final String BAD_ORDER="0005";
            /** 접속 실패(0006) */
            final String NOT_CONNECT="0006";
            /** 시스템 오류(정의되지 않은 오류)(9999) */
            final String SYSTEM_ERROR="9999";
        }

        interface MSG {

            /** 정상(처리결과) - 0000 */
            final String OK = "정상 처리";
            /** Request 파라미터 오류(필수누락 등) - 0001 */
            final String REQ_ERROR="Request 파라미터 오류(필수누락 등)";
            /** 처리거절(처리 불가능 상황) - 0002 */
            final String FAIL = "처리거절(처리 불가능 상황)";
            /** 기 처리상태(이미 처리된 상태) - 0003 */
            final String ALREADY_COMPLETE = "기 처리상태(이미 처리된 상태)";
            /** 키값인증 오류 - 0004 */
            final String AUTH_ERROR="키값인증 오류";
            /** 주문상태 불명 - 0005 */
            final String BAD_ORDER="주문상태 불명";
            /** 접속 실패(0006) */
            final String NOT_CONNECT="접속 실패(연결 시간 초과등)";
            /** 시스템 오류(정의되지 않은 오류) - 9999 */
            final String SYSTEM_ERROR="시스템 오류(정의되지 않은 오류)";
        }
    }

}
