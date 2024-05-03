/*
 * Copyright (c) Dematic GmbH 2024. All rights reserved. Confidential.
 */
package com.vividcloud.mealeator.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A food category.
 *
 * @author jwilliams
 */
public enum Category {
   @JsonProperty("Appetizer") APPETIZER,
   @JsonProperty("Drink") DRINK,
   @JsonProperty("Main Course") MAIN_COURSE,
   @JsonProperty("Dessert") DESSERT
}
