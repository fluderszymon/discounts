package com.szymonfluder.util;

import com.szymonfluder.model.Order;
import com.szymonfluder.model.PaymentMethod;

import java.util.List;
import java.util.Map;

public interface DataMapperInterface {
    Map<String, Order> ordersArrayListToMap(List<Order> orders);
    Map<String, PaymentMethod> paymentMethodsArrayListToMap(List<PaymentMethod> paymentMethods);
}