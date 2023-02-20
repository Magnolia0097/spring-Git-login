# spring-Security-Git-login


<h1>백엔드 실행법</h1>

![image](https://user-images.githubusercontent.com/110005222/220007687-af12cf48-194b-4de2-8535-1721dcdc696a.png)


와 같이 파워셀 실행

$./gradlew bootRun --args='--spring.profiles.active=dev'

명령어 입력후 실행 
<br>




<br>
<h1>프론트 엔드 실행법</h1>
vscode로 열어서
npm install
후 
npm start 를 이용하여 실행합니다
<br>





<br>
<h1>배포 예정</h1>
AWS EC2를 사용하여 금일 이내로 배포 예정입니다

배포 이후에는 새로운 레포지토리에 업로드 하겠습니다



<br>
<h1>사용기술</h1>

Java/Spring/JS/React/Rest API/JPA/MySQL



<br>
<h1>구현된 기능</h1>

- CRUD 
- 로그인 / 회원가입
- Oauth2.0 github 로그인 / 회원가입


<br>
<h1> 구현 예정 기능 </h2>
#프론트
- python 기반 알고리즘 프로그램 조작할 수 있는 페이지 UI/UX 설계
- React 에서 타입스크립트 적용
- 페이지 수가 많아질 경우, 부트스트랩을 사용 중지하고 CSS 라이브러리를 이용해서 정리 및 리메이크
- 사용자가 필요로하는 정보가 담긴 API를 사용하여 데이터 표시하기


#백엔드
- python 기반 알고리즘 프로그램과의 데이터베이스 공유를 위한 DB 설계 및 Entity 구현
- redirect_url이 허용된 도메인을 가지고 있는지 확인할 수있게 만들기 (보안문제 해결)
- Docker를 이용한 서비스 배포
