package com.neighbourly.user.service;

import com.neighbourly.user.constants.SUBSCRIPTION_TYPE;
import com.neighbourly.user.dto.HeaderInfo;
import com.neighbourly.user.dto.Response;
import com.neighbourly.user.dto.SubscriptionDto;
import com.neighbourly.user.entity.Subscription;
import com.neighbourly.user.entity.User;
import com.neighbourly.user.mapper.SubscriptionMapper;
import com.neighbourly.user.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final RoleService roleService;

    private final SubscriptionMapper subscriptionMapper;


    public Response<List<SubscriptionDto>> getAllSubscriptions(HeaderInfo headers) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionDto> subscriptionDtos = subscriptionMapper.toDto(subscriptions);
        return Response.<List<SubscriptionDto>>builder()
                .uuid(headers.getUuid())
                .data(subscriptionDtos)
                .build();
    }

    public Response<SubscriptionDto> getSubscriptionById(Long subscriptionId, HeaderInfo headers) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));
        SubscriptionDto subscriptionDto = subscriptionMapper.toDto(subscription);
        return Response.<SubscriptionDto>builder()
                .uuid(headers.getUuid())
                .data(subscriptionDto)
                .build();
    }

    public Response<SubscriptionDto> createSubscription(SubscriptionDto subscriptionDto, HeaderInfo headers) {

        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        Subscription savedSubcription = subscriptionRepository.save(subscription);
        SubscriptionDto subscriptionCreated = subscriptionMapper.toDto(savedSubcription);
        return Response.<SubscriptionDto>builder()
                .uuid(headers.getUuid())
                .data(subscriptionCreated)
                .build();

    }

    public Response<SubscriptionDto> updateSubscription(Long subscriptionId, SubscriptionDto subscriptionDto, HeaderInfo headers) {

        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));

        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        SubscriptionDto updatedSubscriptionDto = subscriptionMapper.toDto(updatedSubscription);
        return Response.<SubscriptionDto>builder()
                .uuid(headers.getUuid())
                .data(updatedSubscriptionDto)
                .build();
    }


    public Response<Void> deleteSubscription(Long subscriptionId, HeaderInfo headers) {
        subscriptionRepository.deleteById(subscriptionId);
        return Response.<Void>builder()
                .uuid(headers.getUuid())
                .build();
    }

    @Transactional
    public void assignRoleToSubscription(Long subscriptionId, Long roleId, HeaderInfo headers) {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));

        roleService.assignRoleToSubscription(subscription, roleId, headers);
    }

    @Transactional
    public void updateSubscriptionRoles(Long subscriptionId, List<Long> roleIds, HeaderInfo headers) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));

        roleService.updateSubscriptionRoles(subscription, roleIds, headers);
    }

    public void assignSubscriptionToUser(User user, Long subscriptionId, HeaderInfo headers) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));
        if(user.getUserSubscriptions().stream().noneMatch(s -> s.getSubscription().getId().equals(subscriptionId))) {
            user.getUserSubscriptions().add(
                    com.neighbourly.user.entity.UserSubscription.builder()
                            .user(user)
                            .subscription(subscription)
                            .startDate(LocalDateTime.now())
                            .endDate(LocalDateTime.now().plusDays(subscription.getDuration()))
                            .status(com.neighbourly.user.constants.SUBSCRIPTON_STATUS.ACTIVE)
                            .build()
            );
        }


    }

    public void assignBasicSubscriptionToUser(User createdUserEntity, HeaderInfo headers) {
        Subscription basicSubscription = subscriptionRepository.findByType(SUBSCRIPTION_TYPE.BASIC)
                .orElseThrow(() -> new RuntimeException("Basic subscription not found"));
        assignSubscriptionToUser(createdUserEntity, basicSubscription.getId(), headers);
    }
}
