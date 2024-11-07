package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recipes.business.RecipeService;
import recipes.business.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeController {
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        if (userService.registerUser(userDTO)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<?> postRecipe(@AuthenticationPrincipal UserDetails userDetails,
                                        @Valid @RequestBody RecipeDTO recipeDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = recipeService.addRecipe(userDetails.getUsername(), recipeDTO);
        return ResponseEntity.ok().body(Map.of("id", id));
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<?> updateRecipe (@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @PathVariable("id") Long id,
                                           @Valid @RequestBody RecipeDTO recipeDTO) {
        if (!recipeService.existsRecipe(id)) {
            return ResponseEntity.notFound().build();
        }
        if (!recipeService.isAuthor(userDetails.getUsername(), id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable("id") Long id) {
        RecipeDTO recipeDTO = recipeService.getRecipe(id);
        if (recipeDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable("id") Long id) {
        if (!recipeService.existsRecipe(id)) {
            return ResponseEntity.notFound().build();
        }
        if (!recipeService.isAuthor(userDetails.getUsername(), id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        RecipeDTO recipeDTO = recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipe/search")
    public ResponseEntity<?> searchRecipes(@RequestParam Map<String, String> requestParams) {
        if (requestParams.size() != 1 ||
                !(requestParams.containsKey("category") || requestParams.containsKey("name"))) {
            return ResponseEntity.badRequest().build();
        }
        List<RecipeDTO> recipeDTOS = recipeService.searchRecipes(requestParams);
        return ResponseEntity.ok().body(recipeDTOS);
    }

}
