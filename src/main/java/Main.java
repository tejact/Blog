import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
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

        staticFileLocation("/public");


        SimpleBlogEntryDAO simpleBlogEntryDAO = new SimpleBlogEntryDAO();
        System.out.println("Entered Main Method");
        get("/hello", (req, res) -> "Hello World");

        //Check if the cookie with name "user-name" is present and is "admin"
        before("/add-blog-page",(req,res) -> {
            String userName = req.cookie("user-name");
            if(userName == null || !userName.equals("admin")) {
                //Redirect to the login form.
                res.redirect("/login-page");
            }
        });

        //Check if the cookie with name "user-name" is present and is "admin"
        before("/edit-blog/*",(req,res) -> {
            String userName = req.cookie("user-name");
            if(userName == null || !userName.equals("admin")) {
                //Redirect to the login form.
                res.redirect("/login-page");
            }
        });

        get("/login-page",(req,res) ->
                new ModelAndView(null,"login.hbs"),new HandlebarsTemplateEngine());

        //When clicked on login on main login form
        //It is simply re-directed to the main page.
        get("/login",(req,res) -> {
            //Get username and send as a cookie
            String userName = req.queryParams("user-name");
            if(userName.equals("admin")) {
                setFlashMessage(req,"Login Successful!");
            } else {
                setFlashMessage(req,"Login un-successful!");
            }
            res.cookie("user-name",userName);
            res.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        get("/",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            model.put(FLASH_MESSAGE,getFlashMessage(req));
            model.put("allBlogSpots",simpleBlogEntryDAO.getAllBlogs());
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());


        get("add-blog-page", (req, res) -> {
            Map<String,String> model = new HashMap<>();
            model.put(FLASH_MESSAGE,getFlashMessage(req));
            return new ModelAndView(null, "add-blog.hbs");
        },new HandlebarsTemplateEngine());


        post("create-blog",(req,res) -> {
            String title = req.queryParams("blog-title");
            String body = req.queryParams("blog-body");
            simpleBlogEntryDAO.addBlogEntry(title,body);
            res.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        get("/details/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            String slug = req.params(":slug");
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
           return new ModelAndView(model,"details.hbs");
        },new HandlebarsTemplateEngine());

        /*TODO: Need to refactor this code. Compiler warning need to go away*/
        post("/add-comment/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            String commentName = req.queryParams("comment-name");
            String commentBody = req.queryParams("comment-body");
            String slug = req.params(":slug");
            simpleBlogEntryDAO.addComment(slug,commentName,commentBody);
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
            res.redirect("/details/"+slug);
            return null;
        },new HandlebarsTemplateEngine());

        get("/edit-blog/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<>();
            String slug = req.params(":slug");
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
            return new ModelAndView(model,"edit-blog.hbs");
        },new HandlebarsTemplateEngine());

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

        //Like use and throw
        final String message = req.session().attribute(FLASH_MESSAGE);
        if(message != null) {
            req.session().removeAttribute(FLASH_MESSAGE);
        }

        return message;
    }


}
