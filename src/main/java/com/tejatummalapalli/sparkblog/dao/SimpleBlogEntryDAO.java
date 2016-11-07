package com.tejatummalapalli.sparkblog.dao;

import com.github.slugify.Slugify;
import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.Exceptions.BlogNotValidException;
import com.tejatummalapalli.sparkblog.Exceptions.CommentNotValidException;
import com.tejatummalapalli.sparkblog.model.Blog;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import com.tejatummalapalli.sparkblog.model.Comment;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SimpleBlogEntryDAO implements BlogEntryDAO{

    //In memory Database for blog entries.
    private List<BlogEntry> blogEntries;
    private Slugify slg = new Slugify();


    public SimpleBlogEntryDAO() {
        Blog blog = new Blog();
        blog.addDefaultBlogs();
        blogEntries = new Blog().getBlogEntries();
    }

    @Override
    public void addBlogEntry(BlogEntry blogEntry) {
        blogEntries.add(blogEntry);
    }

    public void setBlogEntries(List<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
    }

    public void addBlogEntry(String title, String body) throws BlogNotValidException {
        if(Objects.equals(title, "") | Objects.equals(body, "")) {
            throw new BlogNotValidException();
        }
        String slug = slg.slugify(title);
        BlogEntry newBlogEntry = new BlogEntry(title,new Date(),body,slug);
        blogEntries.add(newBlogEntry);
    }

    @Override
    public BlogEntry getBlogEntry(String blogSlug) throws BlogNotFoundException {
        BlogEntry blogEntryWithRequiredTitle = null;
        for(BlogEntry currentBlogEntry : blogEntries) {
            if(currentBlogEntry.getSlug().equals(blogSlug)) {
                blogEntryWithRequiredTitle = currentBlogEntry;
            }
        }
        //If blog corresponding to the user provided title is not available
        if(blogEntryWithRequiredTitle == null) {
            throw new BlogNotFoundException();
        }
        return blogEntryWithRequiredTitle;
    }

    public void addComment(String blogSlug,String commentName, String commentBody) throws BlogNotFoundException, CommentNotValidException {
        BlogEntry blogEntryWithRequiredTitle = null;
        for(BlogEntry currentBlogEntry : blogEntries) {
            if(currentBlogEntry.getSlug().equals(blogSlug)) {
                blogEntryWithRequiredTitle = currentBlogEntry;
            }
        }

        if(blogEntryWithRequiredTitle == null) {
            throw new BlogNotFoundException();
        }

        if(commentName == null || commentBody == null) {
            throw new CommentNotValidException();
        }

        if(commentName.equals("") || commentBody.equals("")) {
            throw new CommentNotValidException();
        }

        blogEntryWithRequiredTitle.getComments().add(new Comment(commentName,commentBody));
        //If blog corresponding to the user provided title is not available
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

    //Slug need to be changed whenever the body is changed...
    public void editBlogEntry(BlogEntry blogEntry , String newTitle , String newBody){
        blogEntry.setTitle(newTitle);
        blogEntry.setSlug(slg.slugify(newTitle));
        blogEntry.setBody(newBody);
    }

}
