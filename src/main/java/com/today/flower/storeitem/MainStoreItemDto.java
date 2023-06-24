package com.today.flower.storeitem;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainStoreItemDto {
	
	private Long id;
	
	private String itemNm;
	
	private String itemDetail;
	
	private String imgUrl;
	
	private Integer price;
	
	@QueryProjection
	public MainStoreItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price) {
		this.id = id;
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.imgUrl = imgUrl;
		this.price = price;
	}
}