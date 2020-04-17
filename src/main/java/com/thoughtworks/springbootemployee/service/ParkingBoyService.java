package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {
    @Autowired
    private final ParkingBoyRepository parkingBoyRepository;

    public ParkingBoyService(ParkingBoyRepository parkingBoyRepository) {
        this.parkingBoyRepository = parkingBoyRepository;
    }

    public List<ParkingBoy> getAllParkingBoy(Integer page, Integer pageSize) {
        Pageable pageable = null;
        if (page != null && pageSize != null) {
            pageable = PageRequest.of(page, pageSize);
            return (List<ParkingBoy>) parkingBoyRepository.findAll(pageable);
        }
        return parkingBoyRepository.findAll();
    }

    public ParkingBoy addParkingBoy(ParkingBoy parkingBoyToBeAdded) {
        return parkingBoyRepository.save(parkingBoyToBeAdded);
    }
}
