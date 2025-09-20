package com.supercell.gaming_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    // Handle specific React routes - forward to index.html in build directory
    @GetMapping(value = {
            "/", "/home", "/login", "/register", "/games", "/games/**",
            "/profile", "/cart", "/checkout", "/wallet", "/admin", "/about", "/contact"
    })
    public String spa() {
        return "forward:/index.html"; // This will serve from /static/build/index.html
    }

    // Catch-all for any other routes that don't match API, static resources, or GIF assets
    @GetMapping(value = "/{path:^(?!api|static|GIF_Assets).*$}/**")
    public String fallback() {
        return "forward:/index.html";
    }
}