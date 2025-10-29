package com.example.demo.Notification;

import com.example.demo.Orders.Order;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(String title, String message) {
        Notification notification = new Notification(title, message);
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification createNotification(String title, String message, Order order) {
        Notification notification = new Notification(title, message, order);
        return notificationRepository.save(notification);
    }
}