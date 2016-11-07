package com.tejatummalapalli.sparkblog.dao;

import com.github.slugify.Slugify;
import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Teja on 10/21/2016.
 */
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

        assertEquals(blogEntry,simpleBlogEntryDAO.getBlogEntry(blogSlug));
    }


    @Test(expected = BlogNotFoundException.class)
    public void getBlogEntryShouldReturnCorrectException() throws Exception {
        String blogTitle = "Welcome";
        String blogContent = "Spark is a java micro framework";
        String blogSlug = slg.slugify(blogTitle);
        BlogEntry blogEntry = new BlogEntry(blogTitle,new Date(),blogContent,blogSlug);

        simpleBlogEntryDAO.getBlogEntry(blogSlug);
    }


    @Test
    public void getAllBlogs() throws Exception {

    }
    @Test
    public void addComment() throws Exception {

    }

    @Test
    public void getAllComments() throws Exception {

    }

    @After
    public void tearDown() throws Exception {


    }

}