package com.fresco.tenderManagement.BiddingService;

import com.fresco.tenderManagement.model.BiddingModel;
import com.fresco.tenderManagement.model.RoleModel;
import com.fresco.tenderManagement.model.UserModel;
import com.fresco.tenderManagement.repository.BiddingRepository;
import com.fresco.tenderManagement.service.BiddingService;
import com.fresco.tenderManagement.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    
    private static BiddingModel bidmod =null;
    @BeforeAll
    public static void init() {
    	System.out.println("Before all");
    	
    	bidmod = new BiddingModel();
        bidmod.setId(1);
        bidmod.setBiddingId(101);   //this part is data preparation
        bidmod.setBidAmount(10000.00);
        bidmod.setStatus("Valid");
    }
    
    @BeforeEach
    public void initEachTest() {
    	System.out.println("Before Each");
    	
    	bidmod = new BiddingModel();
        bidmod.setId(1);
        bidmod.setBiddingId(101);   //this part is data preparation
        bidmod.setBidAmount(10000.00);
        bidmod.setStatus("Valid");
    }

    @Test
    void postBiddingShouldPostBiddingSuccessfully() {
       
        

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
    
    
    @Test
    void postBiddingShouldThrowExceptionforinvalidPostBidding() {
       
    	bidmod.setStatus("");
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
        BiddingService bidser = Mockito.spy(biddingService);  
        Mockito.doReturn("bidder@example.com").when(bidser).getCurrentUserEmail();

        
        //ResponseEntity<Object> response = spyService.postBidding(bidmod);  //calling actual methods

      RuntimeException runtimeexception =   Assertions.assertThrows(RuntimeException.class, ()->{
        	bidser.postBidding(bidmod);  //calling actual methods
        });
      
      Assertions.assertEquals("Invalid status", runtimeexception.getMessage());  //we want to assert if this particular exception is thrown if the status is empty
      Mockito.verify(biddingRepository, never()).save(any(BiddingModel.class));
    
    }
    
    
    
    @Test
    public void testprivatemethod_validatename() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	Method varName=  biddingService.getClass().getDeclaredMethod("validBiddingname", String.class);
    	varName.setAccessible(true);
    	Boolean var =(Boolean) varName.invoke(biddingService, "Valid");
    	Assertions.assertTrue(var);
    	
    }
    
    @Test
    public void testprivatemethod_validatenameIfInvalid() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	Method varName=  biddingService.getClass().getDeclaredMethod("validBiddingname", String.class);
    	varName.setAccessible(true);
    	Boolean var =(Boolean) varName.invoke(biddingService, "");
    	Assertions.assertFalse(var);
    	
    }
    
    @Test
    public void deleteBiddingByidShouldDeleteBiddingSuccessfully() {   	
    	doNothing().when(biddingRepository).deleteById(1);
    	biddingService.deleteBiddingByid(1);	
    	Mockito.verify(biddingRepository, times(1)).deleteById(1);
    }
    
    @AfterAll
    public static void Destroy() {
    	
    	System.out.println("After all");
    	
    }
    
    @AfterEach
    public void cleanUp() {
    	
    	System.out.println("After each");
    	
    }
}
