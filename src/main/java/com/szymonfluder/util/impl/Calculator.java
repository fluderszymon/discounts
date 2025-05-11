package com.szymonfluder.util.impl;

import com.szymonfluder.model.Order;
import com.szymonfluder.model.PaymentMethod;
import com.szymonfluder.util.DataMapperInterface;
import com.szymonfluder.util.DataProviderInterface;
import com.szymonfluder.util.CalculatorInterface;

import java.util.*;

public class Calculator implements CalculatorInterface {
    private Map<String, Order> cart = new LinkedHashMap<>();
    private Map<String, PaymentMethod> wallet = new HashMap<>();
    private final String POINTS_PROMOTION_KEY = "PUNKTY";
    private final double DISCOUNT_PROMOTION = 10;
    private final String ordersFilePath;
    private final String walletFilePath;

    public Calculator(String ordersFilePath, String walletFilePath) {
        this.ordersFilePath = ordersFilePath;
        this.walletFilePath = walletFilePath;
    }

    private void findBestPaymentMethods() {
        for (Map.Entry<String, Order> cartEntry : cart.entrySet()) {
            Order order = cartEntry.getValue();
            double orderValue = order.getValue();
            List<PaymentMethod> paymentMethodsWithDiscount = getPromoPaymentMethodsSortedByDiscount(order.getPromotions());
            Optional<PaymentMethod> matchedPaymentMethod = findMatchingPaymentMethod(paymentMethodsWithDiscount, orderValue);
            if (matchedPaymentMethod.isPresent()) {
                PaymentMethod paymentMethod = matchedPaymentMethod.get();
                double discount = paymentMethod.getDiscount();
                double priceWithDiscount = orderValue * (1 - (discount / 100));
                payForOrder(paymentMethod.getId(), priceWithDiscount);
            } else {
                PaymentMethod pointsPaymentMethod = wallet.get(POINTS_PROMOTION_KEY);
                double availablePoints = pointsPaymentMethod.getLimit();
                double paidWithPointsPercentage = (availablePoints / orderValue) * 100;
                boolean hasPromo = paidWithPointsPercentage >= DISCOUNT_PROMOTION;
                if (hasPromo) {
                    double discount = (orderValue * DISCOUNT_PROMOTION) / 100;
                    tryToPayWithPointsAndOtherPaymentMethod(discount, orderValue, availablePoints);
                } else {
                    tryToPayWithPointsAndOtherPaymentMethod(0, orderValue, availablePoints);
                }
            }
        }
    }

    private List<PaymentMethod> getPromoPaymentMethodsSortedByDiscount(List<String> promotionIds) {
        List<PaymentMethod> promotionsPaymentMethods = new ArrayList<>();
        for (String promotionId : promotionIds) {
            PaymentMethod promotion = wallet.get(promotionId);
            promotionsPaymentMethods.add(promotion);
        }
        promotionsPaymentMethods.sort(Comparator.comparing(PaymentMethod::getDiscount).reversed());
        promotionsPaymentMethods.addFirst(wallet.get(POINTS_PROMOTION_KEY));
        return promotionsPaymentMethods;
    }

    private List<PaymentMethod> getAllPaymentMethodsWithoutPointsSortedByDiscount() {
        List<PaymentMethod> paymentMethodsWithoutPoints = new ArrayList<>();
        wallet.forEach((key, value) -> {
            if (!value.getId().equals(POINTS_PROMOTION_KEY)) {
                paymentMethodsWithoutPoints.add(value);
            }
        });
        paymentMethodsWithoutPoints.sort(Comparator.comparing(PaymentMethod::getDiscount).reversed());
        return paymentMethodsWithoutPoints;
    }


    private void printResults() {
        wallet.forEach((paymentMethodId, paymentMethod) -> {
            double totalSpent = paymentMethod.getTotalSpent();
            String totalSpentMessage = String.format("%.2f", totalSpent).replace(",", ".");
            String message = paymentMethodId + " " + totalSpentMessage;
            System.out.println(message);
        });
    }

    private void tryToPayWithPointsAndOtherPaymentMethod(double discount, double price, double availablePoints) {
        double priceToPayInTotal = price - discount;
        double priceToPayInOtherPaymentMethod = priceToPayInTotal - availablePoints;
        List<PaymentMethod> paymentMethodsWithoutPoints = getAllPaymentMethodsWithoutPointsSortedByDiscount();
        Optional<PaymentMethod> matched = findMatchingPaymentMethod(paymentMethodsWithoutPoints, priceToPayInOtherPaymentMethod);
        if (matched.isPresent()) {
            payForOrder(matched.get().getId(), priceToPayInOtherPaymentMethod);
            payForOrder(POINTS_PROMOTION_KEY, availablePoints);
        }
    }

    private Optional<PaymentMethod> findMatchingPaymentMethod(List<PaymentMethod> paymentMethods, double price) {
        return paymentMethods
                .stream()
                .filter(item -> item.getLimit() >= price)
                .findFirst();
    }

    private void payForOrder(String paymentMethodId, double price) {
        PaymentMethod originalPaymentMethod = wallet.get(paymentMethodId);
        PaymentMethod updatedPaymentMethod = new PaymentMethod(originalPaymentMethod.getId(), originalPaymentMethod.getDiscount(), originalPaymentMethod.getLimit(), originalPaymentMethod.getTotalSpent());
        updatedPaymentMethod.setLimit(originalPaymentMethod.getLimit() - price);
        updatedPaymentMethod.setTotalSpent(originalPaymentMethod.getTotalSpent() + price);
        wallet.put(paymentMethodId, updatedPaymentMethod);
    }

    private void prepareData() {
        DataProviderInterface dataProvider = new DataProvider();
        List<Order> orders = dataProvider.readJsonOrderFile(ordersFilePath);
        orders.sort(Comparator.comparing(Order::getValue).reversed());
        List<PaymentMethod> paymentMethods = dataProvider.readJsonPaymentMethodFile(walletFilePath);
        DataMapperInterface dataMapper = new DataMapper();
        cart = dataMapper.ordersArrayListToMap(orders);
        wallet = dataMapper.paymentMethodsArrayListToMap(paymentMethods);
    }

    public void execute() {
        prepareData();
        findBestPaymentMethods();
        printResults();
    }
}
