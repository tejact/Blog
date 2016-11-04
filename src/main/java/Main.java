import com.tejatummalapalli.sparkblog.dao.SimpleBlogEntryDAO;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Main {

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

        get("/login-page",(req,res) -> {
            return new ModelAndView(null,"login.hbs");
        },new HandlebarsTemplateEngine());

        //When clicked on login on main login form
        //It is simply re-directed to the main page.
        get("/login",(req,res) -> {
            //Get username and send as a cookie
            String userName = req.queryParams("user-name");
            res.cookie("user-name",userName);
            res.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        get("/",(req,res) -> {
            Map<String,Object> model = new HashMap<String,Object>();
            model.put("allBlogSpots",simpleBlogEntryDAO.getAllBlogs());
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        get("add-blog-page",(req,res) -> {
            return new ModelAndView(null,"add-blog.hbs");
        },new HandlebarsTemplateEngine());


        post("create-blog",(req,res) -> {
            String title = req.queryParams("blog-title");
            String body = req.queryParams("blog-body");
            simpleBlogEntryDAO.addBlogEntry(title,body);
            res.redirect("/");
            return null;
        },new HandlebarsTemplateEngine());

        get("/details/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<String,Object>();
            String slug = req.params(":slug");
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
           return new ModelAndView(model,"details.hbs");
        },new HandlebarsTemplateEngine());

        post("/add-comment/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<String,Object>();
            String commentName = req.queryParams("comment-name");
            String commentBody = req.queryParams("comment-body");
            String slug = req.params(":slug");
            simpleBlogEntryDAO.addComment(slug,commentName,commentBody);
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
            res.redirect("/details/"+slug);
            return null;
        },new HandlebarsTemplateEngine());

        get("/edit-blog/:slug",(req,res) -> {
            Map<String,Object> model = new HashMap<String,Object>();
            String slug = req.params(":slug");
            model.put("blogEntry",simpleBlogEntryDAO.getBlogEntry(slug));
            return new ModelAndView(model,"edit-blog.hbs");
        },new HandlebarsTemplateEngine());

        post("save-edits/:slug",(req,res)->{
            Map<String,Object> model = new HashMap<String,Object>();
            String slug = req.params(":slug");
            String newBlogTitle = req.queryParams("blog-title");
            String newBlogBody = req.queryParams("blog-body");

            BlogEntry blogEntry = simpleBlogEntryDAO.getBlogEntry(slug);
            simpleBlogEntryDAO.editBlogEntry(blogEntry,newBlogTitle,newBlogBody);

            String newSlug = blogEntry.getSlug();
            res.redirect("/details/"+newSlug);
            return null;
        },new HandlebarsTemplateEngine());


    }


}
