package com.tejatummalapalli.sparkblog.dao;

import com.github.slugify.Slugify;
import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleBlogEntryDAOTest {

    SimpleBlogEntryDAO  simpleBlogEntryDAO;
    Slugify slg;


    @Before
    public void setUp() throws Exception {
        simpleBlogEntryDAO = new SimpleBlogEntryDAO();
        slg = new Slugify();
    }

    @Test
    public void addingBlogEntryShouldAddItToMainBlogEntriesList() throws Exception{
        String blogTitle = "Welcome";
        String blogContent = "Spark is a java micro framework";
        String blogSlug = slg.slugify(blogTitle);
        BlogEntry blogEntry = new BlogEntry(blogTitle,new Date(),blogContent,blogSlug);

        simpleBlogEntryDAO.addBlogEntry(blogEntry);
        assertEquals(1,simpleBlogEntryDAO.getAllBlogs().size());
    }


    @Test
    public void getBlogEntryShouldReturnTheCorrectBlogEntry() throws Exception {
        String blogTitle = "Welcome";
        String blogContent = "Spark is a java micro framework";
        String blogSlug = slg.slugify(blogTitle);
        BlogEntry blogEntry = new BlogEntry(blogTitle,new Date(),blogContent,blogSlug);

        simpleBlogEntryDAO.addBlogEntry(blogEntry);

        assertEquals(blogEntry,simpleBlogEntryDAO.getBlogEntryForSlug(blogSlug));
    }


    @Test(expected = BlogNotFoundException.class)
    public void getBlogEntryShouldReturnCorrectException() throws Exception {
        simpleBlogEntryDAO.getBlogEntryForSlug("sample-blog-slug");
    }


    @Test
    public void addCommentShouldAdd() throws Exception {
        String blogTitle = "Welcome";
        String blogContent = "Spark is a java micro framework";
        String blogSlug = slg.slugify(blogTitle);
        BlogEntry blogEntry = new BlogEntry(blogTitle,new Date(),blogContent,blogSlug);
        List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
        blogEntries.add(blogEntry);
        simpleBlogEntryDAO.setBlogEntries(blogEntries);

        simpleBlogEntryDAO.addComment(blogSlug,"Test","Comment");

        assertEquals(1,simpleBlogEntryDAO.getAllComments(blogSlug).size());
    }

    @Test
    public void getAllCommentsShouldReturnAllComments() throws Exception {
        String blogTitle = "Welcome";
        String blogContent = "Spark is a java micro framework";
        String blogSlug = slg.slugify(blogTitle);
        BlogEntry blogEntry = new BlogEntry(blogTitle,new Date(),blogContent,blogSlug);
        List<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
        blogEntries.add(blogEntry);
        simpleBlogEntryDAO.setBlogEntries(blogEntries);

        simpleBlogEntryDAO.addComment(blogSlug,"Test","Comment");
        simpleBlogEntryDAO.addComment(blogSlug,"Test2","Comment2");

        assertEquals(2,simpleBlogEntryDAO.getAllComments(blogSlug).size());
    }

    @Test(expected = BlogNotFoundException.class)
    public void getAllCommentsShouldReturnBlogNotFoundException() throws Exception{
        String blogTitle = "Welcome";
        String blogContent = "Spark is a java micro framework";
        String blogSlug = slg.slugify(blogTitle);
        BlogEntry blogEntry = new BlogEntry(blogTitle,new Date(),blogContent,blogSlug);

        simpleBlogEntryDAO.getAllComments(blogSlug);
    }

    @After
    public void tearDown() throws Exception {


    }

}