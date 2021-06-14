package com.gdg.springmyworkspace.opendata.dust;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 매개변수 없는 기본 생성자
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
public class DustHourlyId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1743176488971107945L;
	private String dataTime;
	private String itemCode;
}
