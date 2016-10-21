package com.tejatummalapalli.sparkblog.dao;

import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import com.tejatummalapalli.sparkblog.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teja on 10/18/2016.
 */
public class SimpleBlogEntryDAO implements BlogEntryDAO{

    //In memory Database for blog entries.
    private List<BlogEntry> blogEntries;

    public SimpleBlogEntryDAO() {
        blogEntries = new ArrayList<BlogEntry>();
    }

    @Override
    public void addBlogEntry(BlogEntry blogEntry) {
        blogEntries.add(blogEntry);
    }

    @Override
    public BlogEntry getBlogEntry(BlogEntry blogEntry) throws BlogNotFoundException {
        BlogEntry blogEntryWithRequiredTitle = null;
        for(BlogEntry currentBlogEntry : blogEntries) {
            if(currentBlogEntry.equals(blogEntry)) {
                blogEntryWithRequiredTitle = blogEntry;
            }
        }
        //If blog corresponding to the user provided title is not available
        if(blogEntryWithRequiredTitle == null) {
            throw new BlogNotFoundException();
        }
        return blogEntryWithRequiredTitle;
    }

    @Override
    public List<BlogEntry> getAllBlogs() {
        return blogEntries;
    }

    @Override
    public void addComment(BlogEntry blogEntry, Comment comment) throws BlogNotFoundException {
        BlogEntry blogEntryToAddComment = null;
        for(BlogEntry currentBlogEntry : blogEntries) {
            if(currentBlogEntry.equals(blogEntry)) {
                blogEntryToAddComment = currentBlogEntry;
            }
        }
        if(blogEntryToAddComment == null) {
            throw new BlogNotFoundException();
        }
        blogEntryToAddComment.getComments().add(comment);
    }

    @Override
    public List<Comment> getAllComments(BlogEntry blogEntry) throws BlogNotFoundException {
        BlogEntry blogEntryToGetAllComments = null;
        for(BlogEntry currentBlogEntry : blogEntries) {
            if(currentBlogEntry.equals(blogEntry)) {
                blogEntryToGetAllComments = currentBlogEntry;
            }
        }
        if(blogEntryToGetAllComments == null) {
            throw new BlogNotFoundException();
        }
        return blogEntryToGetAllComments.getComments();
    }
}
