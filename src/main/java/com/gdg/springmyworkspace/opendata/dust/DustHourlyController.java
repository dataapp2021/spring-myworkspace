package com.gdg.springmyworkspace.opendata.dust;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
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

	// @Cacheable 리턴 객체를 캐시함
	// cacheNames: 캐시할 객체의 명칭(임의로 정함)
	// key: 캐시할 객체의 key
	@Cacheable(cacheNames = "dust-hourly", key = "0")
	@RequestMapping(value = "/opendata/dust/hourly", method = RequestMethod.GET)
	public List<DustHourly> getListByDataType() {
		Order[] orders = { new Order(Sort.Direction.DESC, "dataTime"), new Order(Sort.Direction.ASC, "itemCode") };

		// �ֱ� 12�ð��� �����͸� ��ȸ(pm10, pm2.5)
		return repo.findAll(PageRequest.of(0, 24, Sort.by(orders))).toList();
	}
}
