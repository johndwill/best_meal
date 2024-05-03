
package com.vividcloud.mealeator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.jr.annotationsupport.JacksonAnnotationExtension;
import com.fasterxml.jackson.jr.ob.JSON;
import com.vividcloud.mealeator.data.Category;
import com.vividcloud.mealeator.data.Meal;
import com.vividcloud.mealeator.data.Menu;

/**
 * This is the core logic of the process. It builds up combinations of meals and then
 * scores them by satisfaction. It will print out the meal with the highest satisfaction.
 *
 * @author jwilliams
 */
public class MealSelector {
   Menu menu;

   public MealSelector(String menuFile) {
      this(new File(menuFile));
   }

   public MealSelector(File menuFile) {
      try(Reader reader = new InputStreamReader(new FileInputStream(menuFile))) {
         JsonFactory factory = new JsonFactory();
         JsonParser parser = factory.createParser(reader);

         // Custom JSON parser with Annotation support
         JSON json = JSON.builder()
               .register(JacksonAnnotationExtension.std)
               .enable(JSON.Feature.PRETTY_PRINT_OUTPUT)
               .build();
         menu = json.beanFrom(Menu.class, parser);
         menu.optimize();
      }
      catch (JsonParseException je) {
         writeError("The file "+menuFile+" is incorrectly formatted. \n" + je.getMessage());
         System.exit(1);
      }
      catch (FileNotFoundException fe) {
         writeError("The menu file "+menuFile+" could not be found. Have you checked under your seat?");
         System.exit(1);
      }
      catch (Exception e) {
         writeError("Could not load "+menuFile+".\n"+e.getMessage());
         System.exit(1);
      }
   }

   public void pickMeal() throws IOException {
      Meal meal = calculateMeal();

      if (meal == null) {

         writeError("There is nothing available in your budget. Maybe try the dumpster out back?");
      }
      else {
         System.out.println(meal.toJson());
      }
   }

   private Meal calculateMeal() {

      Meal selectedMeal = null;

      // Build up a collection of possible meals.
      // Each meal has an index for each category of food.
      // We build up each possible combination and keep track of the meal
      // that is under budget and has the highest satisfaction.
      for (int app = 0; app < menu.getFoods(Category.APPETIZER).size(); app++) {
         Meal m2 = new Meal(menu);
         m2.setAppetizer(app);
         for (int drink = 0; drink < menu.getFoods(Category.DRINK).size(); drink++) {
            Meal m3 = new Meal(m2);
            m3.setDrink(drink);

            for (int mainCourse = 0; mainCourse < menu.getFoods(Category.MAIN_COURSE).size(); mainCourse++) {
               Meal m4 = new Meal(m3);
               m4.setMainCourse(mainCourse);

               for (int dessert = 0; dessert < menu.getFoods(Category.DESSERT).size(); dessert++) {
                  Meal m5 = new Meal(m4);
                  m5.setDessert(dessert);

                  if (validMeal(m5) && (selectedMeal == null || m5.getTotalSatisfaction() > selectedMeal.getTotalSatisfaction())) {
                     selectedMeal = m5;
                  }
               }
            }
         }
      }

      return selectedMeal;
   }

   private boolean validMeal(Meal meal) {
      return meal.getTotalCost() <= menu.getBudget();
   }

   private void writeError(String msg) {
      System.out.println("{ \"error\": \""+msg+"\" }");
   }
}
