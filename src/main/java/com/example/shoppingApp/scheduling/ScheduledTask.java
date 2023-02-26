package com.example.shoppingApp.scheduling;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.domain.message.ItemMessage;
import com.example.shoppingApp.domain.message.OrderMessage;
import com.example.shoppingApp.domain.message.UserMessage;
import com.example.shoppingApp.service.OrderService;
import com.example.shoppingApp.service.UserService;
import com.example.shoppingApp.util.SerializeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduledTask {

    private RabbitTemplate rabbitTemplate;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setRabbitTemplate(
            RabbitTemplate rabbitTemplate,
            UserService userService,
            OrderService orderService
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.userService = userService;
        this.orderService = orderService;
    }

//    @Scheduled(fixedRate = 600000) for testing
    @Scheduled(cron = "0 0 18 * * *")
    public void sendMessageEverySecond() {

        List<User> users = userService.getAll();
        for (User user : users) {
//            if(user.getId() != 3){
//                continue;
//            }
            List<Order> orders = orderService.getByUserId(user.getId());

            List<OrderMessage> orderMessageList =
            orders.stream().sorted(Comparator.comparing(Order::getDate_placed)).map((order)->{

                List<ItemMessage> itemMessageList = order.getOrderItems()
                        .stream()
                        .map(orderItem -> ItemMessage
                                .builder()
                                .itemName(orderItem.getProduct().getName())
                                .quantity(orderItem.getPurchased_quantity()).build())
                        .collect(Collectors.toList());

                return OrderMessage.builder()
                        .date(order.getDate_placed())
                        .totalPrice(order.getOrderItems().stream().mapToDouble(item -> item.getPurchased_price() * item.getPurchased_quantity()).sum())
                        .itemMessageList(itemMessageList).build();
            }).collect(Collectors.toList());

            String jsonMessage = SerializeUtil.serialize(UserMessage
                    .builder()
                    .userEmail(user.getEmail())
                    .userName(user.getUsername())
                    .orderMessageList(orderMessageList)
                    .build());

            rabbitTemplate.convertAndSend("emailExchange", "",
                    jsonMessage);

        }
    }
}
