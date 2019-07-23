package net.simihost.deli.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 *
 * Created by Rashed on 15/04/2019
 *
 */
@Embeddable
public class OrderDetails {

    @Column(name = "orderId", nullable = false)
    private String orderId;
    @Column(name = "customerId", nullable = false)
    private String customerId;
    @Column(name = "totalPaid", nullable = true)
    private String totalPaid;
    @Column(name = "shippingAmount", nullable = true)
    private Double shippingAmount;
    @Column(name = "items", nullable = false)
    private String items;
    @Column(name = "created_at", nullable = false)
    private String created_at;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(totalPaid, that.totalPaid) &&
                Objects.equals(shippingAmount, that.shippingAmount) &&
                Objects.equals(items, that.items) &&
                Objects.equals(created_at, that.created_at);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, customerId, totalPaid, shippingAmount, items, created_at);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalPaid='" + totalPaid + '\'' +
                ", shippingAmount=" + shippingAmount +
                ", items='" + items + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}


