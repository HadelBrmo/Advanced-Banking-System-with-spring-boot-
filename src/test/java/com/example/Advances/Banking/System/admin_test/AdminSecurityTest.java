//package com.example.Advances.Banking.System.admin_test;
//
//import com.example.Advances.Banking.System.subsystem.admin.dto.AccountRequest;
//import com.example.Advances.Banking.System.subsystem.admin.dto.AccountStateChangeRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AdminSecurityTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void adminCanCreateAccount() throws Exception {
//        AccountRequest req = new AccountRequest();
//        req.accountId = "A1";
//        req.ownerName = "Farah";
//        req.type = "SAVINGS";
//
//        mockMvc.perform(post("/api/admin/accounts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void userCannotCreateAccount() throws Exception {
//        AccountRequest req = new AccountRequest();
//        req.accountId = "A2";
//        req.ownerName = "Ali";
//        req.type = "CHECKING";
//
//        mockMvc.perform(post("/api/admin/accounts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void adminCanChangeAccountState() throws Exception {
//        AccountStateChangeRequest req = new AccountStateChangeRequest();
//        req.accountId = "A1";
//        req.newState = "FROZEN";
//
//        mockMvc.perform(post("/api/admin/accounts/state")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk());
//    }
//}