package com.example.order.model.order;

import com.example.order.model.member.Member;
import com.example.order.model.product.Product;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderProductForm {
    @NotNull
    private Long product_id;
    private String name;
    private int stock;
    private long price;
    @Min(1) @Max(999)
    private int count;

    public static Orders toOrder(OrderProductForm orderProductForm, Member member, Product product) {
        Orders orders = new Orders();
        orders.setMember(member);
        orders.setProduct(product);
        orders.setCount(orderProductForm.getCount());
        orders.calcTotalPrice();
        return orders;
    }
}
