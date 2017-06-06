package com.hotel.service.bean.pay;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductInfo
 * Created by sharyuke on 15-5-27.
 */
public class ProductInfo {

    public ProductInfo(String order_no, float amount, List<DisPlay> display, Extras extras) {
        this.order_no = order_no;
        this.amount = amount;
        this.display = display;
        this.extras = extras;
    }

    private String order_no;
    private float amount;
    private List<DisPlay> display;
    private Extras extras;

    public String getOrder_no() {
        return order_no;
    }

    public float getAmount() {
        return amount;
    }

    public List<DisPlay> getDisplay() {
        return display;
    }

    public Extras getExtras() {
        return extras;
    }

    public static class DisPlay {

        public DisPlay(String name, List<String> contents) {
            this.name = name;
            this.contents = contents;
        }

        private String name;
        private List<String> contents = new ArrayList<>();

        public static class Builder {
            private String name;
            private List<String> contents = new ArrayList<>();

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder addContents(String contents) {
                this.contents.add(contents);
                return this;
            }

            public Builder addAllContents(List<String> contents) {
                this.contents.addAll(contents);
                return this;
            }

            public DisPlay build() {
                return new DisPlay(name, contents);
            }
        }
    }

    public static class Extras {

        private List<ProductList> productList;

        private Extras(List<ProductList> productList) {
            this.productList = productList;
        }

        public static class Builder {
            private List<ProductList> productList = new ArrayList<>();

            public Builder addProductList(ProductList productList) {
                this.productList.add(productList);
                return this;
            }

            public Builder addAllProductList(List<ProductList> productList) {
                this.productList.addAll(productList);
                return this;
            }

            public Extras build() {
                return new Extras(productList);
            }
        }


        public static class ProductList {

            private String productId;
            private String productName;
            private int count;
            private float price;

            public ProductList(String productId, String productName, int count, float price) {
                this.productId = productId;
                this.productName = productName;
                this.count = count;
                this.price = price;
            }

            public static class Builder {
                private String productId;
                private String productName;
                private int count;
                private float price;

                public Builder setProductId(String productId) {
                    this.productId = productId;
                    return this;
                }

                public Builder setProductName(String productName) {
                    this.productName = productName;
                    return this;
                }

                public Builder setCount(int count) {
                    this.count = count;
                    return this;
                }

                public Builder setPrice(float price) {
                    this.price = price;
                    return this;
                }

                public ProductList build() {
                    return new ProductList(productId, productName, count, price);
                }
            }
        }
    }


    public static class Builder {
        private String order_no;
        private float amount;
        private List<DisPlay> display = new ArrayList<>();
        private Extras extras;

        public Builder setOrder_no(String order_no) {
            this.order_no = order_no;
            return this;
        }

        public Builder setAmount(float amount) {
            this.amount = amount;
            return this;
        }

        public Builder addDisplay(DisPlay display) {
            this.display.add(display);
            return this;
        }

        public Builder addAllDisplay(List<DisPlay> display) {
            this.display.addAll(display);
            return this;
        }

        public Builder setExtras(Extras extras) {
            this.extras = extras;
            return this;
        }

        public ProductInfo build() {
            return new ProductInfo(order_no, amount, display, extras);
        }
    }
}
