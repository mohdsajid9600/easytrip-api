package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;
import com.sajidtech.easytrip.service.CabService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @PostMapping("/driver/register")
    public ResponseEntity<ApiResponse<CabResponse>> createCab(@Valid @RequestBody CabRequest cabRequest, Principal principal){
        CabResponse cabResponse =  this.cabService.createCab(cabRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Cab registered", cabResponse));
    }

    // Update cab by driver
    @PutMapping("/driver/update")
    public ResponseEntity<ApiResponse<CabResponse>> updateCabByDriver(@Valid @RequestBody CabRequest cabRequest,
                                                         Principal principal){
        CabResponse cabResponse = this.cabService.updateCabByDriver(cabRequest, principal.getName());

        return ResponseEntity.ok(ApiResponse.success("Cab updated", cabResponse));
    }
    //get own cab by driver
    @GetMapping("/driver")
    public ResponseEntity<ApiResponse<CabResponse>> getMyCab(Principal principal){
        CabResponse cabResponse = this.cabService.getMuCab(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Fetched Your Cab", cabResponse));
    }
    // Get all available cabs
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<PageResponse<CabResponse>>> getAllAvailableCabs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CabResponse> responses = this.cabService.getAllAvailableCabs(page, size);
        return ResponseEntity.ok(ApiResponse.success("Available cabs", responses));
    }
}
