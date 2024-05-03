/*
 * Copyright (c) Dematic GmbH 2024. All rights reserved. Confidential.
 */
package com.vividcloud.mealeator.data;

/**
 * A single food item in a menu or meal.
 *
 * @author jwilliams
 */
public class Food {
   private String name;
   private int cost;
   private int satisfaction;
   private Category category;

   public Food() {}

   public Food(String name, int cost, int satisfaction, Category category) {
      this.name = name;
      this.cost = cost;
      this.satisfaction = satisfaction;
      this.category = category;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getCost() {
      return cost;
   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   public int getSatisfaction() {
      return satisfaction;
   }

   public void setSatisfaction(int satisfaction) {
      this.satisfaction = satisfaction;
   }

   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }
}
