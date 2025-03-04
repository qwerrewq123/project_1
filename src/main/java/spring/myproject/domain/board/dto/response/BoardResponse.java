package spring.myproject.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import spring.myproject.domain.board.Board;
import spring.myproject.s3.S3ImageDownloadService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponse {
    private String code;
    private String message;
    private String title;
    private String description;
    private List<byte[]> imageUrls;
    private String username;
    private byte[] userImageUrl;
    private LocalDateTime registerDate;

    public static BoardResponse of(List<BoardQuery> boardQueries,List<byte[]> imageUrls, byte[] userImageUrl ,String code, String message) {
        return BoardResponse.builder()
                .code(code)
                .message(message)
                .title(boardQueries.getFirst().getTitle())
                .description(boardQueries.getFirst().getDescription())
                .registerDate(boardQueries.getFirst().getRegisterDate())
                .imageUrls(imageUrls)
                .username(boardQueries.getFirst().getUsername())
                .userImageUrl(userImageUrl)
                .build();

    }
}
