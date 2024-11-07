package recipes.business;

import org.springframework.stereotype.Component;
import recipes.presentation.RecipeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class MyMapper {

    public Recipe convertDTOtoRecipe(RecipeDTO recipeDTO, String author) {
        return new Recipe(null, recipeDTO.getName(), recipeDTO.getCategory(), LocalDateTime.now(),
                recipeDTO.getDescription(),recipeDTO.getIngredients(), recipeDTO.getDirections(), author);
    }

    public RecipeDTO convertRecipeToDTO(Recipe recipe) {
        return new RecipeDTO(recipe.getName(), recipe.getCategory(), recipe.getDate(), recipe.getDescription(),
                recipe.getIngredients(), recipe.getDirections());
    }

    public Recipe updateRecipeFromDTO(Long id, String author, RecipeDTO recipeDTO) {
        Recipe recipe = convertDTOtoRecipe(recipeDTO, author);
        recipe.setId(id);
        return recipe;
    }
}
