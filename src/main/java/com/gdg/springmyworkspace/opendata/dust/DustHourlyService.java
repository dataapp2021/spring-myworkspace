package com.gdg.springmyworkspace.opendata.dust;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DustHourlyService {

	// 스케줄을 실행하는 메서드
	// cron tab 시간 형식
	// 초 분 시 일 월 년
	// 0초 30분 매시 매일 매월 매년
//	@Scheduled(cron = "0 30 * * * *") // 매시 30분에 수집, 원래의 요구사항

	@SuppressWarnings("deprecation")
	// 고정 비율, ms(milli second 단위), 1000 == 1초
	@Scheduled(fixedRate = 1000 * 60 * 30) // 30분마다, 테스트용 스케줄, 프로그램이 시작될 때 한번은 바로 실행됨
	public void requestDustHourlyData() throws IOException {
		System.out.println(new Date().toLocaleString() + "--실행--");
		//
		getDustHourlyData("PM10"); // 미세
		getDustHourlyData("PM25"); // 초미세
	}

	// 데이터를 요청하는 메서드
	private void getDustHourlyData(String itemCode) throws IOException {
		String serviceKey = "ZXrForMW%2B7bGoyCLwU%2FoTqGRJz4mccLh917X2fFkUON44o4IiAodDEE%2BlGI1TTRh1U2FrZeLWWWtzkckwV7Mcg%3D%3D";

		// 데이터 요청 URL을 만들어야 함
		StringBuilder builder = new StringBuilder();
		builder.append("http://apis.data.go.kr/B552584/ArpltnStatsSvc"); // 서비스 주소
		builder.append("/getCtprvnMesureLIst"); // 상세 기능 주소
		builder.append("?itemCode=" + itemCode); // 아이템 코드(PM10, PM25)
		builder.append("&dataGubun=HOUR"); // 시간단위 조회(HOUR)
		builder.append("&pageNo=1"); // 현재부터 가까운 시간의 페이지만 조회(1페이지)
		builder.append("&numOfRows=24"); // 현재부터 24시간의 데이터 조회
		builder.append("&returnType=json"); // 응답 데이터형식으로 JSON으로 받음
		builder.append("&serviceKey=" + serviceKey);

		// 0. 요청 URL 확인
		System.out.println(builder.toString());

		// 1. URL 주소로 접속 및 데이터 읽기
		URL url = new URL(builder.toString()); // 문자열로부터 URL 객체 생성
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); // URL 주소에 접속을 함
		byte[] result = con.getInputStream().readAllBytes();

		// 2. byte[] -> String(JSON)으로 변환
		String data = new String(result);
		System.out.println(data);
	}
}
