package com.example.order.repository;

import com.example.order.model.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Orders> findOrdersByMemberId(String member_id) {
        List<Orders> orders = entityManager.createQuery("select o from Orders o where o.member.member_id = :member_id", Orders.class)
                .setParameter("member_id", member_id)
                .getResultList();

        return orders;
    }
}
