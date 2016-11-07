import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.Exceptions.CommentNotValidException;
import com.tejatummalapalli.sparkblog.dao.SimpleBlogEntryDAO;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Main {

    public static final String FLASH_MESSAGE = "flash-message";

    public static void main(String[] args) {

        /*This directory contains static files to be served ( eg. CSS) */
        staticFileLocation("/public");

        SimpleBlogEntryDAO simpleBlogEntryDAO = new SimpleBlogEntryDAO();

        //Filter: Check if the cookie with name "user-name" is present and is "admin"
        before("/add-blog-page",(req,res) -> {
            String userName = req.cookie("user-name");
            if(userName == null || !userName.equals("admin")) {
                //Redirect to the login form.
                res.redirect("/login-page");
            }
        });

        //Filter: Check if the cookie with name "user-name" is present and is "admin"
        before("/edit-blog/*",(req,res) -> {
            String userName = req.cookie("user-name");
            if(userName == null || !userName.equals("admin")) {
                //Redirect to the login form.
                res.redirect("/login-page");
            }
        });

        /* home / Index page. List all the blog's along with title,date*/
        get("/",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            //The flas_message is set to NULL if it is not present
            model.put(FLASH_MESSAGE,getFlashMessage(req));
            model.put("allBlogSpots",simpleBlogEntryDAO.getAllBlogs());
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());


        /*Serve's the login page.*/
        get("/login-page",(req,res) ->
                new ModelAndView(null,"login.hbs"),new HandlebarsTemplateEngine());

        //When clicked on login on main login form
        //It is simply re-directed to the main page.
        get("/login",(req,res) -> {
            //Get username and send as a cookie
            String userName = req.queryParams("user-name");

            //To show flash messages to user, the msg is set.
            // This message is retrieved by index(/) page and shows it
            if(userName.equals("admin")) {
                setFlashMessage(req,"Login Successful!");
            } else {
                setFlashMessage(req,"Login un-successful!");
            }
            //Cookie is set in respose for the first time.
            //After that the before filter passes because the cookie is present.
            res.cookie("user-name",userName);
            //Redirect to index page. This page fetches the flash message that was
            //set above
            res.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());


        /*Servers the page that provided option to add blog*/
        get("add-blog-page", (req, res) -> {
            Map<String,String> model = new HashMap<>();
            model.put(FLASH_MESSAGE,getFlashMessage(req));
            return new ModelAndView(null, "add-blog.hbs");
        },new HandlebarsTemplateEngine());

        /*A , blog is create. Added using DAO and then get to the index.*/
        post("create-blog",(req,res) -> {
            String title = req.queryParams("blog-title");
            String body = req.queryParams("blog-body");
            simpleBlogEntryDAO.addBlogEntry(title,body);
            res.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        /*Details page is retrived based up on the slug*/
        get("/details/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            String slug = req.params(":slug");
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));

            String flashMessage = getFlashMessage(req);
            model.put("flash-message",flashMessage);

           return new ModelAndView(model,"details.hbs");
        },new HandlebarsTemplateEngine());

        /*Similar to PRG pattern. Comment is added and same details page is requested*/
        post("/add-comment/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            String commentName = req.queryParams("add-comment-name");
            String commentBody = req.queryParams("add-comment-body");
            String slug = req.params(":slug");
            try {
                simpleBlogEntryDAO.addComment(slug,commentName,commentBody);
            } catch (BlogNotFoundException e) {
                setFlashMessage(req,"Sorry, The blog is not found!");
                e.printStackTrace();
            } catch (CommentNotValidException e) {
                setFlashMessage(req,"Sorry, Comment name OR comment body should not be empty");
                e.printStackTrace();
            }
            res.redirect("/details/"+slug);
            return null;
        },new HandlebarsTemplateEngine());


        /*text boxed in edit-blog page are prepopulated with the existing
        * blog values*/
        get("/edit-blog/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            String slug = req.params(":slug");
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
            return new ModelAndView(model,"edit-blog.hbs");
        },new HandlebarsTemplateEngine());

        /*Modifed the exising blog using DAO.*/
        post("save-edits/:slug",(req,res)->{
            String slug = req.params(":slug");
            String newBlogTitle = req.queryParams("blog-title");
            String newBlogBody = req.queryParams("blog-body");

            BlogEntry blogEntry = simpleBlogEntryDAO.getBlogEntry(slug);
            simpleBlogEntryDAO.editBlogEntry(blogEntry,newBlogTitle,newBlogBody);

            String newSlug = blogEntry.getSlug();
            res.redirect("/details/"+newSlug);
            return null;
        },new HandlebarsTemplateEngine());

        /*When an invalid page is requested*/
        exception(BlogNotFoundException.class,(exception,req,res)-> {
            HandlebarsTemplateEngine handleBar = new HandlebarsTemplateEngine();
            res.status(404);
            ModelAndView modelAndView = new ModelAndView(null, "error-display.hbs");
            String html = handleBar.render(modelAndView);
            res.body(html);
        });


    }

    private static void setFlashMessage(Request req, String flashMessage) {
        req.session().attribute(FLASH_MESSAGE,flashMessage);
    }

    private static String getFlashMessage(Request req) {
        if( req.session(false) == null ){
            return null;
        }

        boolean contains = req.session().attributes().contains(FLASH_MESSAGE);
        if(!contains) {
            return null;
        }

        //Like use and throw. The message is cleared form the session and then returned
        //This avoid the flash message to be visible after refresh
        final String message = req.session().attribute(FLASH_MESSAGE);
        if(message != null) {
            req.session().removeAttribute(FLASH_MESSAGE);
        }
        return message;
    }


}
