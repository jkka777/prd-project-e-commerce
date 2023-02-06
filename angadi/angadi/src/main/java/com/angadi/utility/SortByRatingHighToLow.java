package com.angadi.utility;

import com.angadi.model.Product;

import java.util.Comparator;

public class SortByRatingHighToLow implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        Double x = o1.getProductRatings();
        Double y = o2.getProductRatings();
        return (int) (y - x);
    }
}
