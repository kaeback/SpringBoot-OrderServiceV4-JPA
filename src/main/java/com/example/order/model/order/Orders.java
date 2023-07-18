package com.example.order.model.order;

import com.example.order.model.member.Member;
import com.example.order.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Orders {
    @Id @GeneratedValue
    private Long order_id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int count;
    private long order_price;

    private LocalDateTime order_date;

    public void calcTotalPrice() {
        this.order_price = product.getPrice() * count;
    }
}
