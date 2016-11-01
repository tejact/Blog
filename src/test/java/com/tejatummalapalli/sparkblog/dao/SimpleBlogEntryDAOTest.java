package com.tejatummalapalli.sparkblog.dao;

import com.tejatummalapalli.sparkblog.Exceptions.*;
import com.tejatummalapalli.sparkblog.model.*;
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


    @Before
    public void setUp() throws Exception {
        simpleBlogEntryDAO = new SimpleBlogEntryDAO();
    }

    @Test
    public void addingBlogEntryShoudAddItToMainBlogEntriesList() throws Exception{

        BlogEntry blogEntry = new BlogEntry("Welcome",new Date(),"Hi, This is great");

        simpleBlogEntryDAO.addBlogEntry(blogEntry);

        assertEquals(1,simpleBlogEntryDAO.getAllBlogs().size());
    }


    @Test
    public void getBlogEntryShouldReturnTheCorrectBlogEntry() throws Exception {
        BlogEntry blogEntry = new BlogEntry("Welcome",new Date(),"Hi, This is great");

        simpleBlogEntryDAO.addBlogEntry(blogEntry);

        assertEquals(blogEntry,simpleBlogEntryDAO.getBlogEntry(blogEntry));
    }


    @Test(expected = BlogNotFoundException.class)
    public void getBlogEntryShouldReturnCorrectException() throws Exception {
        BlogEntry blogEntry = new BlogEntry("Welcome",new Date(),"Hi, This is great");

        simpleBlogEntryDAO.getBlogEntry(blogEntry);
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