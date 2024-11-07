package recipes.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String category;

    private LocalDateTime date;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotEmpty
    private List<String> ingredients;

    @NotNull
    @NotEmpty
    private List<String> directions;
}
