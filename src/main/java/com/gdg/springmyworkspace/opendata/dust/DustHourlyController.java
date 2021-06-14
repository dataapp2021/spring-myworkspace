package com.gdg.springmyworkspace.opendata.dust;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DustHourlyController {

	private DustHourlyRepository repo;

	public DustHourlyController(DustHourlyRepository repo) {
		this.repo = repo;
	}

	@RequestMapping(value = "/opendata/dust/hourly", method = RequestMethod.GET)
	public List<DustHourly> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "dataTime"), new Order(Sort.Direction.ASC, "itemCode") };

		// 최근 12시간의 데이터만 조회(pm10, pm2.5)
		return repo.findAll(PageRequest.of(0, 24, Sort.by(orders))).toList();
	}
}
