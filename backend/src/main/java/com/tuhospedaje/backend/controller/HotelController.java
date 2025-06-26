package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.entity.Hotel;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoteles")
public class HotelController {
    private IHotelService hotelService;

    @Autowired
    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public Hotel save(@RequestBody Hotel hotel) {
        return hotelService.save(hotel);
    }

    @PutMapping
    public void update(@RequestBody Hotel hotel) {
        hotelService.update(hotel);
    }

    @GetMapping
    public List<Hotel> findAll() {
        return hotelService.findAll();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        hotelService.delete(id);
        return ResponseEntity.ok("Se eliminó el hotel con id: " + id);
    }
}
