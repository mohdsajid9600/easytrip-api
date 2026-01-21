package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.service.CabService;
import com.sajidtech.easytrip.service.DriverService;
import com.sajidtech.easytrip.transformer.CabTransformer;
import com.sajidtech.easytrip.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cab")
public class CabController {

    @Autowired
    private CabService cabService;

    @PostMapping("/driver/{id}/register")
    public ResponseEntity<CabResponse> createCab(@RequestBody CabRequest cabRequest, @PathVariable("id") int driverId){
        CabResponse cabResponse =  cabService.createCab(cabRequest, driverId);
        return new ResponseEntity<>(cabResponse, HttpStatus.CREATED);
    }

    @PutMapping("/driver/{id}/update")
    public ResponseEntity<CabResponse> updateCabByDriver(@RequestBody CabRequest cabRequest,
                                                         @PathVariable("id") int driverId){
        CabResponse cabResponse = cabService.updateCabByDriver(cabRequest, driverId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cabResponse);
    }

    @GetMapping("/search/available")
    public ResponseEntity<List<CabResponse>> getAllAvailableCabs(){
        List<CabResponse> responses = cabService.getAllAvailableCabs();
        return ResponseEntity.ok(responses);
    }
}
