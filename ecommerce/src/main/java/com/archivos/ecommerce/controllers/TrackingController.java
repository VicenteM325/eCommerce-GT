package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.tracking.TrackingRequestDto;
import com.archivos.ecommerce.dtos.tracking.TrackingResponseDto;
import com.archivos.ecommerce.services.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping
    public ResponseEntity<TrackingResponseDto> create(@RequestBody TrackingRequestDto dto){
        return ResponseEntity.ok(trackingService.createTracking(dto));
    }

    @PutMapping("/{trackingId}/status/{status}")
    public ResponseEntity<TrackingResponseDto> updateStatus(
            @PathVariable UUID trackingId,
            @PathVariable String status){
        return ResponseEntity.ok(trackingService.updateDeliveryStatus(trackingId, status));
    }
}
