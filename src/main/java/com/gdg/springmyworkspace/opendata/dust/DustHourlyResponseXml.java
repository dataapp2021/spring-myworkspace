package com.gdg.springmyworkspace.opendata.dust;

import java.util.List;

import lombok.Data;

@Data // getter/setter
public class DustHourlyResponseXml {
	private Response response;

	@Data
	public class Response {
		private Body body;
//		private Object header;
	}

	@Data
	public class Body {
//		private int totalCount;
		private Items items;
//		private int pageNo;
//		private int numOfRows;		
	}

	@Data
	public class Items {
		private List<Item> item;
	}

	@Data
	public class Item {
		private String dataTime;
		private String itemCode;

		private String seoul;
		private String gyeonggi;
		private String incheon;
		private String gangwon;
		private String sejong;
		private String chungbuk;
		private String chungnam;
		private String daejeon;
		private String gyeongbuk;
		private String gyeongnam;
		private String daegu;
		private String ulsan;
		private String busan;
		private String jeonbuk;
		private String jeonnam;
		private String gwangju;
		private String jeju;
	}
}
