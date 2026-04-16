package btvn.it210_ss9_ex1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    // API 1: Khách hàng bấm nút Thêm vào giỏ
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") String productId, HttpSession session) {
        // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
        // Ép kiểu (List<String>) vì getAttribute trả về Object
        List<String> cart = (List<String>) session.getAttribute("myCart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(productId);

        // Cất giỏ hàng lại vào session
        session.setAttribute("myCart", cart);

        // Chuyển hướng sang trang thanh toán
        return "redirect:/checkout";
    }

    // API 2: Hiển thị trang thanh toán
    @GetMapping("/checkout")
    public String viewCheckout(HttpSession session, Model model) {
        // Lấy giỏ hàng từ session của request MỚI
        List<String> cart = (List<String>) session.getAttribute("myCart");

        if (cart == null || cart.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng của bạn đang trống!");
        } else {
            model.addAttribute("message", "Bạn có " + cart.size() + " sản phẩm.");
        }

        return "checkout-page";
    }
}
