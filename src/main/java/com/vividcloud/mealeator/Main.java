package com.vividcloud.mealeator;

import java.io.IOException;

public class Main {
   public static void main(String[] args) throws IOException {

      // Allow a menu file to be passed in. We default to menu.json in the project's root directory
      String menuFile = "menu.json";
      if (args.length > 0) {
         menuFile = args[0];
      }

      // Select the meal
      new MealSelector(menuFile).pickMeal();
   }
}