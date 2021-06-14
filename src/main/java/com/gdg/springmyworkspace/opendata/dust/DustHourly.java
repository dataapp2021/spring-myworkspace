package com.gdg.springmyworkspace.opendata.dust;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@IdClass(DustHourlyId.class)
public class DustHourly {

	// dataTime + itemCode 두개를 합치면 유일값 및 대표값이 됨 -> PK, Id(Identifier)
	// DB -> 합성키(Composite Key): 여러개의 컬럼을 합쳐서 Primary Key로 사용함
	// JPA -> 합성Id(Composite Id): 여러개의 필드를 합쳐서 Id로 사용함

	// 데이터의 중복 저장 방지용으로 id를 지정함
	@Id
	private String dataTime; // 2021-06-14 14:00
	@Id
	private String itemCode; // PM10, PM2.5

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

	public DustHourly(DustHourlyResponse.Item item) {
		this.dataTime = item.getDataTime();
		this.itemCode = item.getItemCode();
		this.busan = item.getBusan();
		this.chungbuk = item.getChungbuk();
		this.chungnam = item.getChungnam();
		this.daegu = item.getDaegu();
		this.daejeon = item.getDaejeon();
		this.gangwon = item.getGangwon();
		this.gwangju = item.getGwangju();
		this.gyeongbuk = item.getGyeongbuk();
		this.gyeonggi = item.getGyeonggi();
		this.gyeongnam = item.getGyeongnam();
		this.incheon = item.getIncheon();
		this.jeju = item.getJeju();
		this.jeonbuk = item.getJeonbuk();
		this.jeonnam = item.getJeonnam();
		this.sejong = item.getSejong();
		this.seoul = item.getSeoul();
		this.ulsan = item.getUlsan();
	}
}
