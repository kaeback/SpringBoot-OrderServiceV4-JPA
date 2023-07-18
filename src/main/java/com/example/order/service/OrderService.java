package com.example.order.service;

import com.example.order.model.order.Orders;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 주문하기
    @Transactional
    public void saveOrder(Orders orders) {
        orderRepository.save(orders);
        // 상품 재고 수정
        orders.getProduct().adjustStock(-orders.getCount());
    }

    // 주문목록
    public List<Orders> findOrdersByMemberId(String member_id) {
        return orderRepository.findOrdersByMemberId(member_id);
    }

    // 주문상세정보
    public Orders findOrderById(Long order_id) {
        return orderRepository.findById(order_id).orElse(null);
    }

    // 주문삭제
    @Transactional
    public void removeOrder(Orders orders) {
        // 재고 수량 조정
        orders.getProduct().adjustStock(orders.getCount());
        // 주문 내역 삭제
        orderRepository.delete(orders);
    }

}
