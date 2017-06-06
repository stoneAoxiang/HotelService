package com.hotel.service.bean.pay;

/**
 * add order
 * Created by sharyuke on 15-5-29.
 */
public class AddOrder {

    private Request requestValue;

    private AddOrder(Request requestValue) {
        this.requestValue = requestValue;
    }

    public static class Builder {
        private Request requestValue;

        public Builder setRequestValue(Request requestValue) {
            this.requestValue = requestValue;
            return this;
        }

        public AddOrder build() {
            return new AddOrder(requestValue);
        }
    }

    public static class Request {
        private String module;
        private String method;
        private String userid;
        private String community_id;
        private String product_id;
        private String product_count;
        private String shipping_method_name;
        private String shipping_time_name;
        private String receive_address;
        private String receive_name;
        private String receive_tel;
        private String receive_memo;

        @Override
        public String toString() {
            return "{\"module\":\"" + module + "\",\"method\":\"" + method + "\"," +
                    "\"userid\":\"" + userid + "\",\"community_id\":\"" + community_id + "\"," +
                    "\"product_id\":\"" + product_id + "\",\"product_count\":\"" + product_count + "\"," +
                    "\"shipping_method_name\":\"" + shipping_method_name + "\",\"shipping_time_name\":\"" + shipping_time_name + "\"," +
                    "\"receive_address\":\"" + receive_address + "\",\"receive_name\":\"" + receive_name + "\"," +
                    "\"receive_tel\":\"" + receive_tel + "\",\"receive_memo\":\"" + receive_memo + "\"}";
        }

        public Request(String module, String method, String userid, String community_id, String product_id, String product_count, String shipping_method_name, String shipping_time_name, String receive_address, String receive_name, String receive_tel, String receive_memo) {
            this.module = module;
            this.method = method;
            this.userid = userid;
            this.community_id = community_id;
            this.product_id = product_id;
            this.product_count = product_count;
            this.shipping_method_name = shipping_method_name;
            this.shipping_time_name = shipping_time_name;
            this.receive_address = receive_address;
            this.receive_name = receive_name;
            this.receive_tel = receive_tel;
            this.receive_memo = receive_memo;
        }

        public static class Builder {
            private String module;
            private String method;
            private String userid;
            private String community_id;
            private String product_id;
            private String product_count;
            private String shipping_method_name;
            private String shipping_time_name;
            private String receive_address;
            private String receive_name;
            private String receive_tel;
            private String receive_memo;

            public Builder setModule(String module) {
                this.module = module;
                return this;
            }

            public Builder setMethod(String method) {
                this.method = method;
                return this;
            }

            public Builder setUserid(String userid) {
                this.userid = userid;
                return this;
            }

            public Builder setCommunity_id(String community_id) {
                this.community_id = community_id;
                return this;
            }

            public Builder setProduct_id(String product_id) {
                this.product_id = product_id;
                return this;
            }

            public Builder setProduct_count(String product_count) {
                this.product_count = product_count;
                return this;
            }

            public Builder setShipping_method_name(String shipping_method_name) {
                this.shipping_method_name = shipping_method_name;
                return this;
            }

            public Builder setShipping_time_name(String shipping_time_name) {
                this.shipping_time_name = shipping_time_name;
                return this;
            }

            public Builder setReceive_address(String receive_address) {
                this.receive_address = receive_address;
                return this;
            }

            public Builder setReceive_name(String receive_name) {
                this.receive_name = receive_name;
                return this;
            }

            public Builder setReceive_tel(String receive_tel) {
                this.receive_tel = receive_tel;
                return this;
            }

            public Builder setReceive_memo(String receive_memo) {
                this.receive_memo = receive_memo;
                return this;
            }

            public Request build() {
                return new Request(module, method, userid, community_id, product_id, product_count, shipping_method_name, shipping_time_name, receive_address, receive_name, receive_tel, receive_memo);
            }
        }
    }

}
