#Blog using Java Spark Micro framework
This web-app is a simple blog devloped with [Java Spark Microframework](http://http://sparkjava.com/). To run this app , download the zip,
extract and import it as a Graddle project in intelliJ/Eclipse

##Requirements
* The main (index) page to list blog entry titles with a title and date/time created. 
* Each blog entry title links to a detail page that displays the blog entry title, date, body, and comments, along with a comment form with that allows anonymous users to post comments. 
* Comments have a name and a body. 
* Include the ability to add or edit blog entries. 
* The edit page should be password-protected and will give the ability for an admin to add or edit blog entries. 

##Code features
* Handlebar's for templating. Template inheritence is use for avoiding duplication
* Flash messages's for validations. Session's are used to implement flash message functionality.
* jUnit for testing the DAO's
* Cookies for authentication.
* Right now list DS is used as an in-momory database. But it will change soon.
