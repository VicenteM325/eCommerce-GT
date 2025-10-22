package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.tracking.TrackingRequestDto;
import com.archivos.ecommerce.dtos.tracking.TrackingResponseDto;
import com.archivos.ecommerce.services.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('COMMON', 'LOGISTICS')")
    public ResponseEntity<TrackingResponseDto> create(@RequestBody TrackingRequestDto dto){
        return ResponseEntity.ok(trackingService.createTracking(dto));
    }

    @PreAuthorize("hasAnyRole('LOGISTICS')")
    @PutMapping("/{trackingId}/status/{status}")
    public ResponseEntity<TrackingResponseDto> updateStatus(
            @PathVariable UUID trackingId,
            @PathVariable String status){
        return ResponseEntity.ok(trackingService.updateDeliveryStatus(trackingId, status));
    }
}
