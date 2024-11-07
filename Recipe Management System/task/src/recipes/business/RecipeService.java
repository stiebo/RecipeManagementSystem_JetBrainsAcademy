package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;
import recipes.presentation.RecipeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MyMapper myMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, MyMapper myMapper) {
        this.recipeRepository = recipeRepository;
        this.myMapper = myMapper;
    }

    public Long addRecipe(String author, RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.save(myMapper.convertDTOtoRecipe(recipeDTO, author));
        return recipe.getId();
    }

    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        if (!recipeRepository.existsById(id)) {
            return null;
        }
        String author = recipeRepository.findRecipeById(id).getAuthor();
        Recipe recipe = recipeRepository.save(myMapper.updateRecipeFromDTO(id, author, recipeDTO));
        return myMapper.convertRecipeToDTO(recipe);
    }

    public RecipeDTO getRecipe(Long id) {
        Recipe recipe = recipeRepository.findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        return myMapper.convertRecipeToDTO(recipe);
    }

    public RecipeDTO deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findRecipeById(id);
        if (recipe != null) {
            recipeRepository.deleteById(id);
            return myMapper.convertRecipeToDTO(recipe);
        }
        return null;
    }

    public List<RecipeDTO> searchRecipes(Map<String, String> parameter) {
        List<RecipeDTO> recipeDTOS = new ArrayList<>();
        List<Recipe> recipes;
        if (parameter.containsKey("category")) {
            recipes = recipeRepository.findRecipeByCategoryIgnoreCaseOrderByDateDesc(parameter.get("category"));
        } else if (parameter.containsKey("name")) {
            recipes = recipeRepository.findRecipeByNameContainingIgnoreCaseOrderByDateDesc(parameter.get("name"));
        } else {
            return recipeDTOS;
        }
        for (Recipe recipe : recipes) {
            recipeDTOS.add(myMapper.convertRecipeToDTO(recipe));
        }
        return recipeDTOS;
    }

    public boolean existsRecipe (Long id) {
        return recipeRepository.existsById(id);
    }

    public boolean isAuthor (String username, Long id) {
        if (recipeRepository.existsById(id)){
            return recipeRepository.getById(id).getAuthor().equals(username);
        }
        return false;
    }

}
