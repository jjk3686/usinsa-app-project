package kr.co.tj.usinsaorderservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;	
	private Long productId; // 상품 id
	private Long qty; // 수량
	private Long unitPrice; // 개당가격
	private Long totalPrice; // 촣가격
	private Date createDate;
	private Date updateDate;
	private String productName;
	private List<OrderResponse> orderList;
	
	
	public static OrderDTO toOrderDTO(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		
		return OrderDTO.builder()
				.id(orderRequest.getId())
				.username(orderRequest.getUsername())
				.productName(orderRequest.getProductName())
				.productId(orderRequest.getProductId())
				.qty(orderRequest.getQty())
				.unitPrice(orderRequest.getUnitPrice())
				.build();
	}


	public OrderResponse toOrderResponse() {
		// TODO Auto-generated method stub
		return OrderResponse.builder()
				.id(id)
				.username(username)
				.productId(productId)
				.productName(productName)
				.qty(qty)
				.unitPrice(unitPrice)
				.totalPrice(totalPrice)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}


	public OrderEntity toOrderEntity() {
		// TODO Auto-generated method stub
		return OrderEntity.builder()
				.id(id)
				.username(username)
				.productId(productId)
				.productName(productName)
				.qty(qty)
				.unitPrice(unitPrice)
				.totalPrice(totalPrice)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}


	public static OrderDTO toOrderDTO(OrderEntity orderEntity) {
		// TODO Auto-generated method stub
		return OrderDTO.builder()
				.id(orderEntity.getId())
				.username(orderEntity.getUsername())
				.productId(orderEntity.getProductId())
				.productName(orderEntity.getProductName())
				.qty(orderEntity.getQty())
				.unitPrice(orderEntity.getUnitPrice())
				.totalPrice(orderEntity.getTotalPrice())
				.createDate(orderEntity.getCreateDate())
				.updateDate(orderEntity.getUpdateDate())
				.build();
	}





}
