package com.gdg.springmyworkspace.opendata.dust;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DustHourlyServiceXml {
	DustHourlyRepository repo;

	@Autowired
	public DustHourlyServiceXml(DustHourlyRepository repo) {
		this.repo = repo;
	}

	// �������� �����ϴ� �޼���
	// cron tab �ð� ����
	// �� �� �� �� �� ��
	// 0�� 30�� �Ž� ���� �ſ� �ų�
//	@Scheduled(cron = "0 30 * * * *") // �Ž� 30�п� ����, ������ �䱸����

	@SuppressWarnings("deprecation")
	// ���� ����, ms(milli second ����), 1000 == 1��
	@Scheduled(fixedRate = 1000 * 60 * 30) // 30�и���, �׽�Ʈ�� ������, ���α׷��� ���۵� �� �ѹ��� �ٷ� �����
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
		builder.append("&returnType=xml"); // ���� �������������� JSON���� ����
		builder.append("&serviceKey=" + serviceKey);

		// 0. ��û URL Ȯ��
		System.out.println(builder.toString());

		// 1. URL �ּҷ� ���� �� ������ �б�
		URL url = new URL(builder.toString()); // ���ڿ��κ��� URL ��ü ����
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); // URL �ּҿ� ������ ��
		byte[] result = con.getInputStream().readAllBytes(); // ����(body)�����͸� ����Ʈ ������ �о����

		// 2. byte[] -> String(XML)���� ��ȯ
		String data = new String(result);
//		System.out.println(data);

		// 3. String(XML) -> String(JSON)���� ��ȯ
		JSONObject jObject = XML.toJSONObject(data);
		System.out.println(jObject.toString());

		// 4. String(JSON) -> Object�� ��ȯ�� �ؾ���
		// ������ �ִ� ����(Class�� �� Object)���� ��ȯ�ؾ� ����� �� ����
		// fromJson(JSON���ڿ�, ��ȯ��Ÿ��)
//		DustHourlyResponseXml response = new Gson().fromJson(jObject.toString(), DustHourlyResponseXml.class);
//		System.out.println(response);
//
//		// 5. ���䰴ü�� Entity ��ü�� ��ȯ�Ͽ� ����
//		for (DustHourlyResponseXml.Item item : response.getResponse().getBody().getItems().getItem()) {
//			repo.save(new DustHourly(item));
//		}
	}
}
