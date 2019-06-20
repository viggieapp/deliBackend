package net.simihost.deli.services.admin;

import net.simihost.deli.beans.OrderDTO;
import net.simihost.deli.db.OrderRepository;
import net.simihost.deli.db.RoleRepository;
import net.simihost.deli.entities.Order;
import net.simihost.deli.entities.OrderDetails;
import net.simihost.deli.entities.Role;
import net.simihost.deli.exceptions.AdminNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by Rashed on  29/04/2019
 *
 */
@Service
@Transactional
public class OrderService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<OrderDTO> getOrders() {
        return OrderDTO.convertToDTOs(findOrders());

    }

    public OrderDTO getOrder(Long orderId) {
        return OrderDTO.convertToDTO(findOrder(orderId));
    }

    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = mapToNewOrder(orderDTO);
        orderRepository.save(order);
        return OrderDTO.convertToDTO(order);
    }

    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order order = findOrder(orderId);
        return updateOrder(order, orderDTO);
    }

    public void deleteOrder(Long orderId) {
        Order order = findOrder(orderId);
        order.setStatus("Inactive");
        orderRepository.save(order);
    }

    private List<Order> findOrders() {
        return orderRepository.findByStatus("Active");
    }

    private Order findOrder(Long orderId) {
        Order order = orderRepository.findOneByIdAndStatus(orderId, "Active");
        if (order == null)
            throw new AdminNotFoundException();

        return order;
    }

    private OrderDTO updateOrder(Order order, OrderDTO request) {
        if (request.getOrderId() != null)
            order.getOrderDetails().setOrderId(request.getOrderId());
        if(request.getCustomerId() != null)
            order.getOrderDetails().setCustomerId(request.getCustomerId());
        if (request.getShippingAmount() != null)
            order.getOrderDetails().setShippingAmount(request.getShippingAmount());
        if (request.getStatus() != null)
            order.setStatus(request.getStatus());
        if (request.getCreatedAt() != null)
            order.setCreationTime(request.getCreatedAt());
        if (request.getItems() != null)
            order.getOrderDetails().setItems(request.getItems());
        if (request.getTotalPaid() != null)
            order.getOrderDetails().setTotalPaid(request.getTotalPaid());
        if (request.getAddress() != null)
            order.getOrderDetails().setAddress(request.getAddress());

        orderRepository.save(order);
        return OrderDTO.convertToDTO(order);
    }

    private Order mapToNewOrder(OrderDTO orderDTO) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderDTO.getOrderId());
        orderDetails.setCustomerId(orderDTO.getCustomerId());
        orderDetails.setShippingAmount(orderDTO.getShippingAmount());
        orderDetails.setItems(orderDTO.getItems());
        orderDetails.setTotalPaid(orderDTO.getTotalPaid());
        orderDetails.setAddress(orderDTO.getAddress());

        Order order = new Order(orderDetails);
        order.setEnabled(true);
        order.setLocked(false);
        order.setLoginTryCount(0);
        order.setStatus("Active");
        //Default admin role
        Role orderRole = roleRepository.findByRoleName("ROLE_ADMIN");
        order.addRoles(orderRole);

        return order;
    }
}
