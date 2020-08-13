# WeatherApp

## 앱 설명
Seoul, London, Chicago 순서로 6일간의 날씨를 표시하는 앱

## 화면
<img src="https://github.com/chuuuul/WeatherApp/blob/master/1.jpg" width="30%">
<img src="https://github.com/chuuuul/WeatherApp/blob/master/2.jpg" width="30%">

## 구현 목록

- 최초 앱 실행 시 네트워크 연결 체크
- 날씨 정보 API로 가져오기
- 날씨 정보 로컬에 캐싱
- 리스트에 Item Decoration으로 테두리 구현
- 리스트에 ViewType으로 날씨와 카테고리 분리
- 리스트에 날씨 약어에 따라 이미지 로드
- 기온 뒤에 ˚C 추가
- 오늘, 내일 날짜를 Today, Tomorrow로 바꾸기
- 뒤로가기 2번 클릭해서 종료
- 날씨 로딩중일때 ProgressBar 표시

## 사용 언어
 - Kotlin


## 사용 라이브러리

- JetPack( LiveData, ViewModel, Room, DataBiding )
- RxKotlin, RxAndroid
- Retrofit
- Okhttp
- Glide
- Koin
- Gson

## 날씨 API
 - https://www.metaweather.com/api/
 


