package com.example.newsfeed.service;

import com.example.newsfeed.dto.FriendResponseDto;
import com.example.newsfeed.entity.Friend;
import com.example.newsfeed.entity.RequestStatus;
import com.example.newsfeed.entity.User;
import com.example.newsfeed.repository.FriendRepository;
import com.example.newsfeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public List<FriendResponseDto> getFriends(Long userId) {
        List<Friend> friends = friendRepository.findByUserUserId(userId);
        return friends.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public void addFriendRequest(Long userId, Long targetId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> target = userRepository.findById(targetId);

        if (user.isPresent() && target.isPresent()) {
            Friend friendRequest = new Friend(user.get(), target.get(), RequestStatus.PENDING);
            friendRepository.save(friendRequest);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteFriend(Long userId, Long targetId) {
        friendRepository.deleteByUserUserIdAndTargetUserId(userId, targetId);
    }

    public FriendResponseDto findFriend(Long userId, Long targetId) {
        Friend friend = friendRepository.findByUserUserIdAndTargetUserId(userId, targetId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        return convertToResponseDTO(friend);
    }

    public void acceptFriendRequest(Long userId, Long targetId) {
        Friend friend = friendRepository.findByUserUserIdAndTargetUserId(userId, targetId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        if (friend.getRequestStatus() == RequestStatus.PENDING) {
            friend.setRequestStatus(RequestStatus.ACCEPTED);
            friendRepository.save(friend);
        } else {
            throw new IllegalArgumentException("Invalid request status");
        }
    }

    public void rejectFriendRequest(Long userId, Long targetId) {
        Friend friend = friendRepository.findByUserUserIdAndTargetUserId(userId, targetId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        if (friend.getRequestStatus() == RequestStatus.PENDING) {
            friendRepository.delete(friend);
        } else {
            throw new IllegalArgumentException("Invalid request status");
        }
    }

    private FriendResponseDto convertToResponseDTO(Friend friend) {
        return new FriendResponseDto(friend.getFriendId(), friend.getUser().getUserId(),
                friend.getTarget().getUserId(), friend.getRequestStatus().name());
    }
}
