package com.hcmus.app_computer_store_management.utils;

import com.hcmus.app_computer_store_management.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SampleDataGenerator {
    public static List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();

        products.add(createProduct("Laptop Dell XPS 13", "Laptop cao cấp với màn hình 13.4 inch OLED, độ phân giải 3.5K, vi xử lý Intel Core i7-1260P, RAM 16GB, SSD 512GB, thiết kế mỏng nhẹ, pin lên đến 12 giờ.", 32500000, 28000000, 10));
        products.add(createProduct("MacBook Air M2", "Laptop Apple MacBook Air với chip M2, màn hình Retina 13.6 inch, RAM 8GB, SSD 256GB, trọng lượng 1.24kg, phù hợp cho công việc văn phòng và thiết kế nhẹ.", 28000000, 25000000, 15));
        products.add(createProduct("PC Gaming ASUS ROG Strix", "Máy tính để bàn chơi game với CPU Intel Core i9-12900K, GPU NVIDIA RTX 3080, RAM 32GB, SSD 1TB, hệ thống tản nhiệt nước, đèn RGB tùy chỉnh.", 55000000, 48000000, 5));
        products.add(createProduct("Lenovo ThinkPad X1 Carbon Gen 10", "Laptop doanh nhân với màn hình 14 inch WQUXGA, CPU Intel Core i7-1270P, RAM 16GB, SSD 1TB, bảo mật vân tay, trọng lượng chỉ 1.12kg.", 38000000, 33000000, 8));
        products.add(createProduct("Lenovo ThinkPad E16 Ryzen 7", "Laptop doanh nhân với màn hình 16 inch cảm ứng, CPU AMD Ryzen 7, RAM 16GB, SSD 1TB, tích hợp trợ lý AI Copilot, bàn phím có đèn nền, bảo mật vân tay.", 22500000, 19500000, 12));
        products.add(createProduct("HP Envy x360 Intel Ultra 7", "Laptop 2 trong 1 với màn hình 15.6 inch OLED, CPU Intel Ultra 7, RAM 16GB, SSD 1TB, hỗ trợ bút cảm ứng, pin lâu, phù hợp cho thiết kế và công việc sáng tạo.", 19800000, 17000000, 7));
        products.add(createProduct("MSI Codex R2", "Máy tính để bàn chơi game với CPU Intel Core i5, GPU NVIDIA RTX 4060, RAM 16GB, SSD 512GB, hỗ trợ chơi game ở độ phân giải 1440p, dễ nâng cấp linh kiện.", 25000000, 22000000, 6));
        products.add(createProduct("Dell XPS Desktop 8960", "Máy tính để bàn cao cấp với CPU Intel Core i7, GPU NVIDIA RTX 4080 Super, RAM 32GB, SSD 1TB, hiệu năng mạnh mẽ cho cả công việc và chơi game 4K.", 45000000, 40000000, 4));
        products.add(createProduct("Asus ProArt PX13", "Laptop 2 trong 1 dành cho sáng tạo, CPU AMD Ryzen, GPU NVIDIA RTX 4050, màn hình OLED 13 inch, RAM 16GB, SSD 512GB, thiết kế bền bỉ đạt chuẩn MIL-STD 810H.", 33000000, 29000000, 5));
        products.add(createProduct("MacBook M4 Air", "Laptop mỏng nhẹ với chip M4, màn hình Retina 13.6 inch, RAM 8GB, SSD 256GB, webcam Center Stage, hỗ trợ màn hình ngoài, pin lên đến 18 giờ.", 23000000, 20000000, 10));
        products.add(createProduct("HP Omen 35L", "Máy tính để bàn chơi game với CPU AMD Ryzen 7 8700G, GPU RTX 4080 Super, RAM 32GB, SSD 1TB, đèn RGB tùy chỉnh, hiệu năng 4K, hoạt động êm ái.", 52000000, 46000000, 3));

        return products;
    }

    private static Product createProduct(String name, String desc, double sellingPrice, double importPrice, int stock) {
        Product p = new Product(0, name, sellingPrice, importPrice);
        p.setDescription(desc);
        p.setStock(stock);
        p.setType("Máy tính");
        return p;
    }
}