package net.simihost.deli.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.*;
import java.util.*;

/**
 *
 * Created by Rashed on 15/04/2019
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="orders")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 3344585642081549555L;

    @JsonUnwrapped
    @Embedded
    private OrderDetails orderDetails;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private boolean locked;
    @Column(name = "has_logged_out")
    private boolean hasLoggedOut;
    @Column(name = "login_try_count")
    private int loginTryCount;
    @Column(name = "disabled_date")
    private Date disabledDate;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "admin_roles", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Order() {}

    public Order(OrderDetails details) {
        this.orderDetails = new OrderDetails();
        this.orderDetails.setOrderId(details.getOrderId());
        this.orderDetails.setCustomerId(details.getCustomerId());
        this.orderDetails.setShippingAmount(details.getShippingAmount());
        this.orderDetails.setItems(details.getItems());
        this.orderDetails.setTotalPaid(details.getTotalPaid());
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isHasLoggedOut() {
        return hasLoggedOut;
    }

    public void setHasLoggedOut(boolean hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }

    public int getLoginTryCount() {
        return loginTryCount;
    }

    public void setLoginTryCount(int loginTryCount) {
        this.loginTryCount = loginTryCount;
    }

    public Date getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(Date disabledDate) {
        this.disabledDate = disabledDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRoles(Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return enabled == order.enabled &&
                locked == order.locked &&
                hasLoggedOut == order.hasLoggedOut &&
                loginTryCount == order.loginTryCount &&
                Objects.equals(orderDetails, order.orderDetails) &&
                Objects.equals(disabledDate, order.disabledDate) &&
                Objects.equals(roles, order.roles);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderDetails, enabled, locked, hasLoggedOut, loginTryCount, disabledDate, roles);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDetails=" + orderDetails +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", hasLoggedOut=" + hasLoggedOut +
                ", loginTryCount=" + loginTryCount +
                ", disabledDate=" + disabledDate +
                ", roles=" + roles +
                '}';
    }
}
