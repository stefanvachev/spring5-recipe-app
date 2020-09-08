package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//Unit test, no spring context used. Separate Integration Test later on can be used to verify the url mappings
//Verify the proper string is retunred "index" and verify that the right methods are called
public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    /**
     * This test shows how to test a controller as a unit test, without bringing the spring context
     * It keeps the test very light and quick loading. As most unit tests should be.
     */
    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/")) //This will mock a get request
                .andExpect(status().isOk()) //validate the httpResponse, using MockMvcResultMatcher.status
                .andExpect(view().name("index")); //validate the view name returned

    }


    @Test
    public void getIndexPage() {
        //given
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(0L);
        recipes.add(recipe);
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipes.add(recipe1);

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String pageName = indexController.getIndexPage(model);
        assertEquals("index", pageName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture()); //We need a matcher here for recipes, and anySet since we did not actually define the set that the mocked recipeService Returns.
        Set<Recipe> setInController = argumentCaptor.getValue(); //This returns the object captured by the ArgumentCaptor
        assertEquals(2, setInController.size());
    }
}