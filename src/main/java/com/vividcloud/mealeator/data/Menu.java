
package com.vividcloud.mealeator.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A menu is a collection of all food that might be used to build a meal.
 *
 * @author jwilliams
 */
public class Menu {
   List<Food> foods = new ArrayList<>();

   @JsonIgnore
   Map<Category, List<Food>> courses = new HashMap<>();

   int budget;

   public Menu() {
   }

   public List<Food> getFoods() {
      return foods;
   }

   public void setFoods(List<Food> foods) {
      this.foods = foods;
   }

   public int getBudget() {
      return budget;
   }

   public void setBudget(int budget) {
      this.budget = budget;
   }

   public void optimize() {

      courses.put(Category.APPETIZER, byCategory(Category.APPETIZER));
      courses.put(Category.DRINK, byCategory(Category.DRINK));
      courses.put(Category.MAIN_COURSE, byCategory(Category.MAIN_COURSE));
      courses.put(Category.DESSERT, byCategory(Category.DESSERT));
   }

   public List<Food> getFoods(Category category) {
      return courses.get(category);
   }

   public Optional<Food> getFood(Category category, int index) {
      if (index < getFoods(category).size()) {
         return Optional.of(getFoods(category).get(index));
      }
      return Optional.empty();
   }

   protected List<Food> byCategory(Category category) {
      return foods.stream()
            .filter(f -> f.getCategory() == category)
            .sorted((o1, o2) -> o2.getSatisfaction() - o1.getSatisfaction())
            .collect(Collectors.toList());
   }
}
