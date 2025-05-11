package com.szymonfluder.util.impl;

import com.szymonfluder.model.Order;
import com.szymonfluder.model.PaymentMethod;
import com.szymonfluder.util.DataMapperInterface;

import java.util.*;

public class DataMapper implements DataMapperInterface {
    public Map<String, Order> ordersArrayListToMap(List<Order> orders) {
        Map<String, Order> map = new LinkedHashMap<>();
        for (Order order : orders) {
            if (order.getPromotions() == null) {
                order.setPromotions(new ArrayList<>());
            }
            map.put(order.getId(), order);
        }
        return map;
    }

    public Map<String, PaymentMethod> paymentMethodsArrayListToMap(List<PaymentMethod> paymentMethods) {
        Map<String, PaymentMethod> map = new HashMap<>();
        for (PaymentMethod paymentMethod : paymentMethods) {
            map.put(paymentMethod.getId(), paymentMethod);
        }
        return map;
    }
}
