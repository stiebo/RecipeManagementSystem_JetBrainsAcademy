package recipes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.business.Recipe;
import recipes.business.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long>{
    Recipe findRecipeById(Long id);
    List<Recipe> findRecipeByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findRecipeByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
