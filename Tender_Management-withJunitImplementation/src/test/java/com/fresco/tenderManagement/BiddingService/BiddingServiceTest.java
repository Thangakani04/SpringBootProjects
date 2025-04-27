package com.fresco.tenderManagement.BiddingService;

import com.fresco.tenderManagement.model.BiddingModel;
import com.fresco.tenderManagement.model.RoleModel;
import com.fresco.tenderManagement.model.UserModel;
import com.fresco.tenderManagement.repository.BiddingRepository;
import com.fresco.tenderManagement.service.BiddingService;
import com.fresco.tenderManagement.service.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class BiddingServiceTest {

    @Mock
    BiddingRepository biddingRepository;

    @Mock
    UserService userService;

    @InjectMocks
    BiddingService biddingService;

    // Stub getCurrentUserEmail() since its not mocked
    private String getCurrentUserEmail() {
        return "bidder@example.com";
    }

    @Test
    void postBiddingShouldPostBiddingSuccessfully() {
       
        BiddingModel bidmod = new BiddingModel();
        bidmod.setId(1);
        bidmod.setBiddingId(101);   //this part is data preparation
        bidmod.setBidAmount(10000.00);
        bidmod.setStatus("Bidding added");

        RoleModel bidderRole = new RoleModel();
        bidderRole.setRolename("BIDDER");

        UserModel user = new UserModel();
        user.setId(10);
        user.setEmail("bidder@example.com");
        user.setRole(bidderRole);

        // mock methods  - Mocking calls
        Mockito.when(userService.getUserByEmail("bidder@example.com")).thenReturn(user);
        Mockito.when(biddingRepository.save(Mockito.any(BiddingModel.class))).thenReturn(bidmod);

        // inject the email manually (since getCurrentUserEmail is not mockable)
        // refactor getCurrentUserEmail() in your service to make it injectable/testable
        BiddingService spyService = Mockito.spy(biddingService);  
        Mockito.doReturn("bidder@example.com").when(spyService).getCurrentUserEmail();

        
        ResponseEntity<Object> response = spyService.postBidding(bidmod);  //calling actual methods

        // Assertions
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody() instanceof BiddingModel);
        BiddingModel result = (BiddingModel) response.getBody();
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals(101, result.getBiddingId());
        Assertions.assertEquals(10000.00, result.getBidAmount());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getId() == 1);
    }
}
