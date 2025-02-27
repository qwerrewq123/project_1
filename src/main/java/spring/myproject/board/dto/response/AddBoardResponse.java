package spring.myproject.board.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddBoardResponse {

    private String code;
    private String message;
    public static AddBoardResponse of(String successCode, String successMessage) {
        return new AddBoardResponse(successCode,successMessage);
    }
}
