### 플러스 주차 필수 구현 

## 1. Transactional에 대한 이해

* Transactional annotation 사용으로 메서드 내에서 하나의 로직이라도 실패할 경우 rollback
          
## 2. 인가에 대한 이해

* adminInterceptor URI 리스트를 추가하여 권한을 설정

## 3. N+1에 대한 이해

* Repository에서 fetch join을 이용하여 해결

## 4. DB 접근 최소화

* findByIdIn을 사용하여 Query를 한번에 불러옴

## 5. 동적 쿼리에 대한 이해

* 강의를 봐도 이해가 어려워 구현하지 못했습니다.

## 6. 필요한 부분만 갱신하기

* 테스트 코드를 작성해 봤지만 반복적인 에러 발생으로 구현하지 못했습니다.

## 7. 리팩토링

* swith문을 사용하여 가독성을 높였습니다. 예외 경우는 entity에 따로 메서드를 구현했습니다.

## 8. 테스트 코드

* passwordEncoding 만 구현했습니다.

