package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import net.simihost.deli.entities.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by Rashed on 17/04/2019
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"version"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderDTO {

    private Long id;
    private String orderId;
    private String customerId;
    private String Status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    private String totalPaid;
    private Double shippingAmount;
    private String Items;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static OrderDTO convertToDTO(Order entity) {
        if(entity == null)
            return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());

        if(entity.getOrderDetails() != null) {
            dto.setOrderId(entity.getOrderDetails().getOrderId());
            dto.setCustomerId(entity.getOrderDetails().getCustomerId());
            dto.setShippingAmount(entity.getOrderDetails().getShippingAmount());
            dto.setStatus(entity.getStatus());
            dto.setCreatedAt(entity.getCreationTime());
            dto.setItems(entity.getOrderDetails().getItems());
            dto.setTotalPaid(entity.getOrderDetails().getTotalPaid());
        }

        return dto;
    }

    public static List<OrderDTO> convertToDTOs(List<Order> entities) {
        List<OrderDTO> dtos = new ArrayList<>();
        for(Order entity : entities)
            dtos.add(OrderDTO.convertToDTO(entity));
        return dtos;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", Status='" + Status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", totalPaid='" + totalPaid + '\'' +
                ", shippingAmount=" + shippingAmount +
                ", Items=" + Items +
                ", address='" + address + '\'' +
                '}';
    }
}
