### Project
Warehouse management

### How it works
Backend in Spring communicates with MySql DB. The REST requests are sent from frontend made in Vue.JS.

### How to run
1. Run mvn command from main folder `mvn clean install`
2. Run spring from main folder with `mvn spring-boot:run`
3. Run npm command from frontend-warehouse `npm install`
4. Run Vue.JS from frontend-warehouse with `npm run serve`

### Things that could be improved
1. The scalability was not addressed:
    - what about big files
    - what if many users have the same orders (race issues)
    - the db schema is not made to scale - products have JSON String which contains defined set of items; better to use more of a relation db features (sort of nosql approach)
2. Frontend was designed to handle issues and was not checked for potential problems such as: 
3. API documentation is missing
4. Controllers should be split into controllers for Products and Articles
5. There is no graceful error handling implemented. Error in JSONs lead to drops of file or sometimes inconsistent changes
6. Merging of updates to DB is inefficient, can't change partial elements of JSON e.g. change amount of items. This requires creating new JSON.
7. Testing is not exhaustive and could be improved
8. Warehouse Controller class is convoluted and could be made more "Spring-like"

### Things that work quite well
1. Manage to have a rather quite alright error robustness 
2. It is easy to extend the application to work with more JSON files
3. JSON managing allows for a significant amount of freedom in file structure

