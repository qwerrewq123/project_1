package spring.myproject.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.myproject.domain.gathering.Gathering;
import spring.myproject.domain.gathering.repository.GatheringRepository;
import spring.myproject.domain.like.Like;
import spring.myproject.domain.like.repository.LikeRepository;
import spring.myproject.domain.user.User;
import spring.myproject.domain.user.repository.UserRepository;
import spring.myproject.domain.like.dto.response.DislikeResponse;
import spring.myproject.domain.like.dto.response.LikeResponse;
import spring.myproject.domain.gathering.exception.NotFoundGatheringException;
import spring.myproject.domain.like.exception.AlreadyLikeGathering;
import spring.myproject.domain.like.exception.NotFoundLikeException;
import spring.myproject.domain.user.exception.NotFoundUserException;
import spring.myproject.util.ConstClass;

import java.util.Optional;

import static spring.myproject.util.ConstClass.*;


@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;

    public LikeResponse like(Long gatheringId, String username) {


        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new NotFoundUserException("no exist User!!"));
            Long userId = user.getId();

            Optional<Like> optionalLike = likeRepository.findLike(userId, gatheringId);
            Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(()-> new NotFoundGatheringException("no exist Gathering!!"));
            if(optionalLike.isPresent()){
                throw new AlreadyLikeGathering("Already Like Gathering!!");
            }

            likeRepository.save(Like.builder()
                    .gathering(gathering)
                    .likedBy(user)
                    .build());
            return LikeResponse.builder()
                    .code(SUCCESS_CODE)
                    .message(SUCCESS_MESSAGE)
                    .build();
        }catch (NotFoundUserException e){
            return LikeResponse.builder()
                    .code(NOT_FOUND_USER_CODE)
                    .message(NOT_FOUND_USER_MESSAGE)
                    .build();
        }catch (NotFoundGatheringException e){
            return LikeResponse.builder()
                    .code(NOT_FOUND_GATHERING_CODE)
                    .message(NOT_FOUND_GATHERING_MESSAGE)
                    .build();
        }catch (AlreadyLikeGathering e){
            return LikeResponse.builder()
                    .code(ALREADY_LIKE_CODE)
                    .message(ALREADY_LIKE_MESSAGE)
                    .build();
        }
    }

    public DislikeResponse dislike(Long gatheringId, String username) {

        try {
            User user = userRepository.findByUsername(username).orElseThrow(()->new NotFoundUserException("no exist User!!"));
            Long userId = user.getId();
            Like like = likeRepository.findLike(userId, gatheringId).orElseThrow(()-> new NotFoundLikeException("no exist Like"));
            Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(()-> new NotFoundGatheringException("no exist Gathering!!"));
            likeRepository.delete(like);
            return DislikeResponse.builder()
                    .code(SUCCESS_CODE)
                    .message(SUCCESS_MESSAGE)
                    .build();
        }catch (NotFoundUserException e){
            return DislikeResponse.builder()
                    .code(NOT_FOUND_USER_CODE)
                    .message(NOT_FOUND_USER_MESSAGE)
                    .build();
        }catch (NotFoundGatheringException e){
            return DislikeResponse.builder()
                    .code(NOT_FOUND_GATHERING_CODE)
                    .message(NOT_FOUND_GATHERING_MESSAGE)
                    .build();
        }catch (NotFoundLikeException e){
            return DislikeResponse.builder()
                    .code(NOT_FOUND_LIKE_CODE)
                    .message(NOT_FOUND_LIKE_MESSAGE)
                    .build();
        }
    }
}
