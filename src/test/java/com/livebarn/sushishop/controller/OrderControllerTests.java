package com.livebarn.sushishop.controller;

import com.livebarn.sushishop.service.OrderService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {
    @MockBean
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldGet201CreatedForPlaceOrder() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sushi_name", "California Roll");

        mockMvc.perform(post("/api/orders")
                        .header("Content-Type", "application/json")
                        .content(jsonObject.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGet200OkForPauseOrder() throws Exception {
        mockMvc.perform(put("/api/orders/{order_id}/pause", 1)
                        .header("Content-Type", "application/json")
                        .content(""))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet200OkForResumeOrder() throws Exception {
        mockMvc.perform(put("/api/orders/{order_id}/resume", 1)
                        .header("Content-Type", "application/json")
                        .content(""))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet200OkForCancelOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/{order_id}", 1)
                        .header("Content-Type", "application/json")
                        .content(""))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet200OkForGetOrderStatus() throws Exception {
        mockMvc.perform(get("/api/orders/status")
                        .header("Content-Type", "application/json")
                        .content(""))
                .andExpect(status().isOk());
    }
}
