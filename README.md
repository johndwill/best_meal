# MEAL PICKER

This app takes a 'menu.json' (or other menu json file) and calculates the most satisfying
meal based on a specified budget.

## Operation
Build the application like so:
```
mvn clean package
```
Run the application like this:
```
java -jar target/mealpicker-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Optional
By default, the application will look for a 'menu.json' file in the current directory.
You can override this by specifying the path to another menu file as a command line argument.

Example:
```
java -jar target/mealpicker-1.0-SNAPSHOT-jar-with-dependencies.jar giantMenu.json
```

# Logic
The menu is parsed into a list of Foods. Each food has a name, cost, satisfaction rating, and category.
The foods are then organized into 4 separate lists:
* Appetizer
* Drink
* Main Course
* Dessert

Each list is sorted by descending satisfaction value.

The meal calculation is done by walking through every combination of Appetizer, Drink, Main Course, Dessert and 
discarding the meal if its cost is higher than the budget. The meal selector tracks the combination
with the highest satisfaction and reports the result.

A meal tracks foods as indexes into the category lists making it easy to iterate through the lists. 