package com.example.order.repository;

import com.example.order.model.order.Orders;

import java.util.List;

public interface OrderCustomRepository {
    List<Orders> findOrdersByMemberId(String member_id);
}
