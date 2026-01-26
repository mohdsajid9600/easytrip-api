package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cab")
public class CabController {


    // Cab service dependency
    private final CabService cabService;

    public CabController(CabService cabService){
        this.cabService = cabService;
    }

    // Register cab for driver
    @PostMapping("/driver/{id}/register")
    public ResponseEntity<ApiResponse<CabResponse>> createCab(@RequestBody CabRequest cabRequest, @PathVariable("id") int driverId){
        CabResponse cabResponse =  this.cabService.createCab(cabRequest, driverId);
        return ResponseEntity.ok(ApiResponse.success("Cab registered", cabResponse));
    }

    // Update cab by driver
    @PutMapping("/driver/{id}/update")
    public ResponseEntity<ApiResponse<CabResponse>> updateCabByDriver(@RequestBody CabRequest cabRequest,
                                                         @PathVariable("id") int driverId){
        CabResponse cabResponse = this.cabService.updateCabByDriver(cabRequest, driverId);

        return ResponseEntity.ok(ApiResponse.success("Cab updated", cabResponse));
    }

    // Get all available cabs
    @GetMapping("/search/available")
    public ResponseEntity<ApiResponse<List<CabResponse>>> getAllAvailableCabs(){
        List<CabResponse> responses = this.cabService.getAllAvailableCabs();
        return ResponseEntity.ok(ApiResponse.success("Available cabs", responses));
    }
}
