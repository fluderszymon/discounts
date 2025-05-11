## Content of Project
- [Technologies](#Technologies)
- [Running application](#Running application)
- [Algorithm](#Algorithm)

---
## Technologies
<ul>
<li>Java 21</li>
<li>Maven - build tool</li>
<li>Jackson - for data serialization</li>
</ul>


---
## Running application

Application can be run from command line:
```
java -jar /home/…/app.jar /home/…/orders.json /home/…/paymentmethods.json
```

---
## Algorithm

Application enables user to find the best use of resources for given orders list.
- Data for app is provided in two .json files. Firstly orders.json
```
[
{"id": "ORDER1", "value": "100.00", "promotions": ["mZysk"]},
{"id": "ORDER2", "value": "200.00", "promotions": ["BosBankrut"]},
{"id": "ORDER3", "value": "150.00", "promotions": ["mZysk", "BosBankrut"]},
{"id": "ORDER4", "value": "50.00"}
]
```

Secondly paymentmethods.json
```
[
{"id": "PUNKTY", "discount": "15", "limit": "100.00"},
{"id": "mZysk", "discount": "10", "limit": "180.00"},
{"id": "BosBankrut", "discount": "5", "limit": "200.00"}
]
```

---
- In order to serialize data two classes were introduced:
```
public class Order{

    private String id;
    private double value;
    private ArrayList<String> promotions;

// constructor, getters, setters, toString()
}
```

```
public class PaymentMethod {

    private String id;
    private int discount;
    private double limit;
    private double totalSpent;

// constructor, getters, setters, toString()
}
```
PaymentMethod class has extra field for storing value of spent money.

- Then ObjectMapper class from Jackson library is used for "reading" both .json files and data is stored as ArrayList. Additionally, orders are sorted by value
in descending order.


- For easier use and better performance arraylists are mapped to maps. Cart is created by mapping arraylist of orders to LinkedHashMap<String, Order>, where key is orderId 
and value is Order. This operation doesn't change given order (desc). PaymentMethods are mapped to HashMap<String, PaymentMethod> producing wallet where key 
is paymentMethodId and value is PaymentMethod.


- Using for loop on map entries in cart orders are checked one by one. Algorithm finds promotions that can be used for an order, sorts them by discount (but promotes Points) and 
uses method if it has sufficient amount of money. If it can't find one method it tries paying partially by points and paying rest of it with one of the payment methods, checking
if 10% discount for points can be applied.

  
- Results are printed:
```
mZysk 165.00
BosBankrut 190.00
PUNKTY 100.00
```



