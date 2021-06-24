package com.gdg.springmyworkspace.opendata.dust;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class DustHourlyService {

	DustHourlyRepository repo;

	@Autowired
	public DustHourlyService(DustHourlyRepository repo) {
		this.repo = repo;
	}

	// �������� �����ϴ� �޼���
	// cron tab �ð� ����
	// �� �� �� �� �� ��
	// 0�� 30�� �Ž� ���� �ſ� �ų�
	@Scheduled(cron = "0 30 * * * *") // �Ž� 30�п� ����, ������ �䱸����
	// ���� ����, ms(milli second ����), 1000 == 1��
//	@Scheduled(fixedRate = 1000 * 60 * 30) // 30�и���, �׽�Ʈ�� ������, ���α׷��� ���۵� �� �ѹ��� �ٷ� �����
//	@Scheduled(fixedRate = 1000 * 30)

	// @CacheEvict: 메서드가 실행될 때 해당 캐시를 삭제함
	// allEntries = true, 해당객체 타입에 해당하는 캐시를 모두 삭제함
//	@CacheEvict(cacheNames = "dust-hourly", key = "0")
	@CacheEvict(cacheNames = "dust-hourly", allEntries = true)
	@SuppressWarnings("deprecation")
	public void requestDustHourlyData() throws IOException {
		System.out.println(new Date().toLocaleString() + "--����--");
		//
		getDustHourlyData("PM10"); // �̼�
		getDustHourlyData("PM25"); // �ʹ̼�
	}

	// �����͸� ��û�ϴ� �޼���
	private void getDustHourlyData(String itemCode) throws IOException {
		String serviceKey = "ZXrForMW%2B7bGoyCLwU%2FoTqGRJz4mccLh917X2fFkUON44o4IiAodDEE%2BlGI1TTRh1U2FrZeLWWWtzkckwV7Mcg%3D%3D";

		// ������ ��û URL�� ������ ��
		StringBuilder builder = new StringBuilder();
		builder.append("http://apis.data.go.kr/B552584/ArpltnStatsSvc"); // ���� �ּ�
		builder.append("/getCtprvnMesureLIst"); // �� ��� �ּ�
		builder.append("?itemCode=" + itemCode); // ������ �ڵ�(PM10, PM25)
		builder.append("&dataGubun=HOUR"); // �ð����� ��ȸ(HOUR)
		builder.append("&pageNo=1"); // ������� ����� �ð��� �������� ��ȸ(1������)
		builder.append("&numOfRows=24"); // ������� 24�ð��� ������ ��ȸ
		builder.append("&returnType=json"); // ���� �������������� JSON���� ����
		builder.append("&serviceKey=" + serviceKey);

		// 0. ��û URL Ȯ��
		System.out.println(builder.toString());

		// 1. URL �ּҷ� ���� �� ������ �б�
		URL url = new URL(builder.toString()); // ���ڿ��κ��� URL ��ü ����
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); // URL �ּҿ� ������ ��
		byte[] result = con.getInputStream().readAllBytes(); // ����(body)�����͸� ����Ʈ ������ �о����

		// 2. byte[] -> String(JSON), UTF-8���� ��ȯ
		String data = new String(result, "UTF-8");
		System.out.println(data);

		// 3. String(JSON) -> Object�� ��ȯ�� �ؾ���
		// ������ �ִ� ����(Class�� �� Object)���� ��ȯ�ؾ� ����� �� ����
		// fromJson(JSON���ڿ�, ��ȯ��Ÿ��)
		DustHourlyResponse response = new Gson().fromJson(data, DustHourlyResponse.class);
		System.out.println(response);

		// 4. ���䰴ü�� Entity ��ü�� ��ȯ�Ͽ� ����
		for (DustHourlyResponse.Item item : response.getResponse().getBody().getItems()) {
			repo.save(new DustHourly(item));
		}

//		// 1번째 페이지 데이터를 조회하여 캐시함
//		getListByDataType();
	}

//	@Cacheable(cacheNames = "dust-hourly", key = "0")
//	public List<DustHourly> getListByDataType() {
//		Order[] orders = { new Order(Sort.Direction.DESC, "dataTime"), new Order(Sort.Direction.ASC, "itemCode") };
//
//		// �ֱ� 12�ð��� �����͸� ��ȸ(pm10, pm2.5)
//		return repo.findAll(PageRequest.of(0, 24, Sort.by(orders))).toList();
//	}
}
