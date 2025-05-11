package com.szymonfluder.util;

import com.szymonfluder.model.Order;
import com.szymonfluder.model.PaymentMethod;

import java.util.ArrayList;

public interface DataProviderInterface {
    ArrayList<Order> readJsonOrderFile(String filePath);
    ArrayList<PaymentMethod> readJsonPaymentMethodFile(String filePath);
}
