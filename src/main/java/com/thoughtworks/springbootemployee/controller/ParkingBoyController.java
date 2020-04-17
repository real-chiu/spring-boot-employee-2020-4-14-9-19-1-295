package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyController {
    @Autowired
    private final ParkingBoyService parkingBoyService;

    public ParkingBoyController(ParkingBoyService parkingBoyService) {
        this.parkingBoyService = parkingBoyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ParkingBoy>> getAllParkingBoy(@RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer pageSize) {
        List<ParkingBoy> allParkingBoy = parkingBoyService.getAllParkingBoy(page, pageSize);
        return new ResponseEntity<>(allParkingBoy, HttpStatus.OK);
    }
}
