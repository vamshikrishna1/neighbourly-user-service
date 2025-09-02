package com.neighbourly.user.web;

import com.neighbourly.user.dto.HeaderInfo;
import com.neighbourly.user.dto.Response;
import com.neighbourly.user.dto.SubscriptionDto;
import com.neighbourly.user.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @GetMapping
    public ResponseEntity<Response<List<SubscriptionDto>>> getAllSubscriptions(HeaderInfo headerInfo) {
        Response<List<SubscriptionDto>> response = subscriptionService.getAllSubscriptions(headerInfo);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Response<SubscriptionDto>> getSubscriptionById(Long subscriptionId, HeaderInfo headers) {
        Response<SubscriptionDto> response = subscriptionService.getSubscriptionById(subscriptionId, headers);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<SubscriptionDto>> createSubscription(@Validated @RequestBody SubscriptionDto subscriptionDto, HeaderInfo headers) {
        Response<SubscriptionDto> response = subscriptionService.createSubscription(subscriptionDto, headers);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{subscriptionId}")
    public ResponseEntity<Response<SubscriptionDto>> updateSubscription(Long subscriptionId, SubscriptionDto subscriptionDto, HeaderInfo headers) {
        Response<SubscriptionDto> response = subscriptionService.updateSubscription(subscriptionId, subscriptionDto, headers);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(Long subscriptionId, HeaderInfo headers) {
        subscriptionService.deleteSubscription(subscriptionId, headers);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{subscriptionId}/roles/{roleId}")
    public ResponseEntity<Void> assignRoleToSubscription(@PathVariable Long subscriptionId, @PathVariable Long roleId, HeaderInfo headers) {
        subscriptionService.assignRoleToSubscription(subscriptionId, roleId, headers);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{subscriptionId}/roles")
    public ResponseEntity<Void> updateSubscriptionRoles(@PathVariable Long subscriptionId, @RequestBody List<Long> roleIds, HeaderInfo headers) {
        subscriptionService.updateSubscriptionRoles(subscriptionId, roleIds, headers);
        return ResponseEntity.ok().build();
    }





}
