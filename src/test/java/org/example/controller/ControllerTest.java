package org.example.controller;

import org.example.object.Deal;
import org.example.object.Discount;
import org.example.object.Product;
import org.example.util.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({AdminController.class, CustomerController.class})
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    private Util util;
    @Test
    public void createProductTest() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setName("Product I");
        product.setPrice(100.00);

        this.mvc.perform(MockMvcRequestBuilders.post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(util.asJsonString(product))
        ).andExpect(status().isOk()).andExpect(content().string("Product 1 is created successfully"));
    }

    @Test
    public void deleteProductTest() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setName("Product I");
        product.setPrice(100.00);

        this.mvc.perform(MockMvcRequestBuilders.delete("/product/" + product.getId())
                ).andExpect(status().isOk())
                .andExpect(content().string("Product 1 is deleted successfully"));
    }

    @Test
    public void createDiscountTest() throws Exception {
        Discount discount = new Discount();
        discount.setId("DISC001");
        discount.setName("Discount 001");
        discount.setProductId("1");
        discount.setDiscountRate(5.00);


        this.mvc.perform(MockMvcRequestBuilders.post("/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(util.asJsonString(discount))
        ).andExpect(status().isOk())
         .andExpect(content().string("Discount DISC001 is created successfully"
        ));
    }

    @Test
    public void addProductTest() throws Exception {
        Deal deal = new Deal();
        deal.setProductId("1");
        deal.setAmt(1);
        deal.setId("2023010101");
        this.mvc.perform(MockMvcRequestBuilders.post("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(util.asJsonString(deal))
        ).andExpect(status().isOk()).andExpect(content().string("Product not found"));

    }


    @Test
    public void removeProductTest() throws Exception {
        Deal deal = new Deal();
        deal.setProductId("1");
        deal.setAmt(1);
        deal.setId("2023010102");
        this.mvc.perform(MockMvcRequestBuilders.delete("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(util.asJsonString(deal))
        ).andExpect(status().isOk()).andExpect(content().string("No cart for the customer"));
    }

    @Test
    public void printReceiptTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/receipt/" + 2)
                ).andExpect(status().isOk())
                .andExpect(content().string("No cart from customer 2"));

    }


}
