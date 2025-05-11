package com.szymonfluder.util.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szymonfluder.model.Order;
import com.szymonfluder.model.PaymentMethod;
import com.szymonfluder.util.DataProviderInterface;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataProvider implements DataProviderInterface {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ArrayList<Order> readJsonOrderFile(String filePath) {
        File ordersJsonFile = new File(filePath);
        try{
            return objectMapper.readValue(ordersJsonFile, new TypeReference<>() {});
        } catch (IOException exp){
            System.out.println(exp.getMessage());
        }
        return new ArrayList<>();
    }

    public ArrayList<PaymentMethod> readJsonPaymentMethodFile(String filePath) {
        File ordersJsonFile = new File(filePath);
        try{
            return objectMapper.readValue(ordersJsonFile, new TypeReference<>() {});
        } catch (IOException exp){
            System.out.println(exp.getMessage());
        }
        return new ArrayList<>();
    }
}

