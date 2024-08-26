
package com.eoutlet.Eoutlet.pojo;

import java.io.Serializable;


public class OrderSendOrder implements Serializable {

        private String userId;
        private String paymentMethod;
        private OrederPayment payment;
        private OrderShipping shipping;
        private OrederProducts products;
        private final static long serialVersionUID = -2870706691544911940L;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public OrederPayment getPayment() {
            return payment;
        }

        public void setPayment(OrederPayment payment) {
            this.payment = payment;
        }

        public OrderShipping getShipping() {
            return shipping;
        }

        public void setShipping(OrderShipping shipping) {
            this.shipping = shipping;
        }

        public OrederProducts getProducts() {
            return products;
        }

        public void setProducts(OrederProducts products) {
            this.products = products;
        }



}