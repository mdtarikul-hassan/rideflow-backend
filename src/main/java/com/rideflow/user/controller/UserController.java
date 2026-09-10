package com.rideflow.user.controller;

import com.rideflow.common.dto.ApiResponse;
import com.rideflow.common.web.ApiPaths;
import com.rideflow.user.dto.CompleteProfileRequest;
import com.rideflow.user.dto.UserResponse;
import com.rideflow.user.entity.User;
import com.rideflow.user.service.UserService;
import com.rideflow.user.userdetails.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.V1 + "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(@AuthenticationPrincipal UserPrincipal principal,
                                        @Valid @RequestBody CompleteProfileRequest request){
        User user = userService.getById(principal.getId());
        return ApiResponse.ok(UserResponse.from(user));
    }

    @PatchMapping("/me")
    public ApiResponse<UserResponse> completeprofile(@AuthenticationPrincipal UserPrincipal principal,
                                        @Valid @RequestBody CompleteProfileRequest request){
        User user = userService.completeProfile(principal.getId(), request.firstName(), request.firstName(), request.email());
        return ApiResponse.ok(UserResponse.from(user), "Profile updated");
    }

}
