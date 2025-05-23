package com.fresco.tenderManagement.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fresco.tenderManagement.model.BiddingModel;
import com.fresco.tenderManagement.model.UserModel;
import com.fresco.tenderManagement.repository.BiddingRepository;
import com.fresco.tenderManagement.repository.UserRepository;

@Service
public class BiddingService {

    @Autowired
    UserRepository urepo;

    @Autowired
    private BiddingRepository biddingRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> postBidding(BiddingModel biddingModel) {
        
            String email = getCurrentUserEmail();
            UserModel user = userService.getUserByEmail(email);

            if (!"BIDDER".equals(user.getRole().getRolename())) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }

            biddingModel.setBidderId(user.getId());
            biddingModel.setDateOfBidding(getCurrentDate());
            boolean validation = validBiddingname(biddingModel.getStatus());

            if(validation) {
            biddingRepository.save(biddingModel);
            }
            else {
            	throw new RuntimeException("Invalid status");
            }
            return new ResponseEntity<>(biddingModel, HttpStatus.CREATED);
        
    }

    public ResponseEntity<Object> getBidding(double bidAmount) {
        List<BiddingModel> results = biddingRepository.findByBidAmountGreaterThan(bidAmount);

        if (results.isEmpty()) {
            return new ResponseEntity<>("No data available", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateBidding(int id, BiddingModel model) {
        try {
            BiddingModel bidding = biddingRepository.findById(id).orElse(null);

            if (bidding == null) {
                return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
            }
            
            String email = getCurrentUserEmail();
            UserModel user = userService.getUserByEmail(email);

            if (!"APPROVER".equals(user.getRole().getRolename())) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            bidding.setStatus(model.getStatus());
            
            biddingRepository.save(bidding);        
           
            return new ResponseEntity<>(bidding, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
    private boolean validBiddingname(String name) {
    	return name !=null && !name.isEmpty();
    }

    public ResponseEntity<Object> deleteBidding(int id) {
        try {
            BiddingModel bidding = biddingRepository.findById(id).orElse(null);

            if (bidding == null) {
                return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
            }

            String email = getCurrentUserEmail();
            UserModel user = userService.getUserByEmail(email);

            if ("APPROVER".equals(user.getRole().getRolename()) || bidding.getBidderId() == user.getId()) {
                biddingRepository.delete(bidding);
                return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT); //204
            } else {
                return new ResponseEntity<>("You don’t have permission", HttpStatus.FORBIDDEN); //403
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
    
    public void deleteBiddingByid(int id) {
    	biddingRepository.deleteById(id);
    }

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
