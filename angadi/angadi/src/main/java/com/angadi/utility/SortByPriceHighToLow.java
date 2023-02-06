package com.angadi.utility;

import com.angadi.model.Product;

import java.util.Comparator;

public class SortByPriceHighToLow implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {

        Integer x = (int) (long) o1.getProductPrice();
        Integer y = (int) (long) o2.getProductPrice();
        return y - x;

        /*return Long.compare(o2.getProductPrice(), o1.getProductPrice());*/
    }
}
