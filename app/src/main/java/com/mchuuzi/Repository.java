package com.mchuuzi;

import android.util.Log;

import com.mchuuzi.models.OrderItem;
import com.mchuuzi.models.ProductsModel;
import com.mchuuzi.models.Vendors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository {
    //THe repository class provides dummy data mimicking a database or online source

    private List<ProductsModel> productsModelList = new ArrayList<>();
    public static List<OrderItem> orderItems = new ArrayList<>();
    private List<Vendors> vendorsList = new ArrayList<>();


    public static int itemCount() {
        int total = 0;

        for (int i = 0; i < Repository.orderItems.size(); i++) {
            OrderItem  orderItem =Repository.orderItems.get(i);
            total+=orderItem.getQuantity();
        }


        return total;

    }


    public static int total() {
        int total = 0;

        for (int i = 0; i < Repository.orderItems.size(); i++) {
            OrderItem  orderItem =Repository.orderItems.get(i);
            total+=orderItem.getQuantity()*orderItem.getPrice();
        }


        return total;

    }

    public static void clearCart(){
        orderItems.clear();
        Log.e("Repository", "Cart cleared");
    }




    public static synchronized void addOrderItem(ProductsModel product) {
        ///first see if item is added

        ///if added add only qauantity otherwise new
        OrderItem orderItem = new OrderItem();
        orderItem.setName(product.getName());
        if (Repository.orderItems.contains(orderItem)) {
            Log.e("contained", orderItem.toString());
            orderItem = Repository.orderItems.get(Repository.orderItems.indexOf(orderItem));

            Repository.orderItems.get(Repository.orderItems.indexOf(orderItem)).setQuantity(orderItem.getQuantity() + 1);
            return;
        }
        Log.e(" not contained", orderItem.toString());

        orderItem.setQuantity(1);
        orderItem.setPrice(product.getPrice());
        orderItem.setImage(product.getImage());


        orderItems.add(orderItem);


    }

    private void removeOrderItem(OrderItem orderItem) {

        orderItems.remove(orderItem);

    }




    private String Kales, Frenchbeans, Carrots, Cabbages, oranges, Onions, Spinach, banana, Sweet_peppers, apples, pineapples;


    public List<ProductsModel> getProductsModelList() {
        banana = "The banana is an anytime fruit of the year as a snack, into your cereals, pancakes, fruit salad & smoothies. Loaded with potassium, good for blood sugar control & digestive health";
        Kales = "Sukuma wiki\n" +
                "\n" +
                "Excellent source of Vit E, A, K & C. Can be steamed, used raw, in salads or juiced";
        Cabbages = "An all time favourite, firmly packed the green cabbage is loaded with vitamins and antioxidants. Perfect when steamed, fried or raw in salads";
        Carrots = "High in Vit C, K & Potassuim, our carrots are colourful, crunchy and sweet. Perfect to snack on, cook and juice";
        oranges = "Oranges have a significant amount of Vit C & potassium. Very low in acid and perfect for juices, salads and smoothies. Embu county currently has plenty of these in season. Prevents kidney stones and anemia\n";
        Onions = "Onions add crunch & pinch to sandwiches, salads and burgers. A cooking favourite for flavour. Has strong anti-inflammatory properties benefiting heart health and cancer fighting compounds";
        Spinach = "Farmed widely due to its impressive nutrient profile, spinach is rich in antioxidants and may reduce risk of chronic disease. Use raw, in smoothies and juices";

        Sweet_peppers = "Great source of Vit B6 & folate. Often referred to as sweet due flavourful taste. Good in salads to bring out the gorgeous colours";
        apples = "These are now coming from Nyeri, very sweet and great for both fruit and juice\n" +
                "\n" +
                "Local organic apples";
        pineapples = "Available all year from our Ugandan farmers, this bold fruit stands out in both sweet and savoury dishes. Very effective in fighting inflammation.";


        ProductsModel product1 = new ProductsModel("Collard-Greens", 50.00, R.drawable.kales, Kales);
        ProductsModel product2 = new ProductsModel("Bananas", 120.00, R.drawable.banana, banana);
        ProductsModel product3 = new ProductsModel("Cabbage", 90.00, R.drawable.cabbage, Cabbages);
        ProductsModel product4 = new ProductsModel("Carrots", 50.00, R.drawable.carrots, Carrots);
        ProductsModel product5 = new ProductsModel("Oranges", 100.00, R.drawable.oranges, oranges);
        ProductsModel product6 = new ProductsModel("Sweet-Pepper", 100.00, R.drawable.pepper, Sweet_peppers);
        ProductsModel product7 = new ProductsModel("Onions", 100.00, R.drawable.onions, Onions);
        ProductsModel product8 = new ProductsModel("Apples", 200.00, R.drawable.apples, apples);
        ProductsModel product9 = new ProductsModel("Pineapples", 100.00, R.drawable.pineapples, pineapples);
        ProductsModel product10 = new ProductsModel("Spinach", 100.00, R.drawable.spinach, Spinach);
        productsModelList.addAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10));


        return productsModelList;
    }

    public List<Vendors> getVendorsList() {
        Vendors vendor1 = new Vendors("Muriuki Grocers", "Westlands", R.drawable.grocery2);
        Vendors vendor2 = new Vendors("Kibaki Vendors", "Juja", R.drawable.grocery1);
        Vendors vendor3 = new Vendors("Njonjo Greens", "Thika", R.drawable.grocery3);
        Vendors vendor4 = new Vendors("Juja Pineapple Farm","Juja",R.drawable.pineapplefarm);
        Vendors vendor5 = new Vendors("Banana Dealers","Kisii",R.drawable.bananafarm);
        Vendors vendor6 = new Vendors("Garissa Oranges","Garissa",R.drawable.orangefarm);
        Vendors vendor7 = new Vendors("Hoho Peppers","Nyeri",R.drawable.pepperfarm);
        Vendors vendor8 = new Vendors("Kinangop Farmers","Nyeri",R.drawable.cabbages);



        vendorsList.addAll(Arrays.asList(vendor1, vendor2, vendor3,vendor4,vendor5,vendor6,vendor7,vendor8));

        return vendorsList;
    }


}
