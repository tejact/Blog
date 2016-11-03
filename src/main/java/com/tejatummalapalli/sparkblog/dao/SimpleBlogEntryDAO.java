package com.tejatummalapalli.sparkblog.dao;

import com.github.slugify.Slugify;
import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import com.tejatummalapalli.sparkblog.model.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleBlogEntryDAO implements BlogEntryDAO{

    //In memory Database for blog entries.
    private List<BlogEntry> blogEntries;
    Slugify slg = new Slugify();


    public SimpleBlogEntryDAO() {
        blogEntries = new ArrayList<BlogEntry>();
        String title1 = "Lambda's in java 8";
        String slug1 = slg.slugify(title1);
        List<Comment> comments1 = new ArrayList<Comment>();
        comments1.add(new Comment("Awesome","Thanks for this blog"));
        comments1.add(new Comment("Awesome Again","But no Thanks now"));
        comments1.add(new Comment("Holy Cow","You are good man"));

        BlogEntry sample1 = new BlogEntry(title1,new Date(),"New Feature",slug1);
        sample1.setComments(comments1);


        String title2 = "Method References in Java8";
        String slug2 = slg.slugify(title2);
        BlogEntry sample2 = new BlogEntry(title2,new Date(),"New Feature",slug2);

        String title3 = "Java9 is coming :)";
        String slug3 = slg.slugify(title3);
        BlogEntry sample3 = new BlogEntry(title3,new Date(),"New Feature",slug3);
        blogEntries.add(sample1);
        blogEntries.add(sample2);
        blogEntries.add(sample3);
    }

    @Override
    public void addBlogEntry(BlogEntry blogEntry) {
        blogEntries.add(blogEntry);
    }


    public void addBlogEntry(String title,String body) {
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

    public void addComment(String blogSlug,String commentName, String commentBody) throws BlogNotFoundException {
        BlogEntry blogEntryWithRequiredTitle = null;
        for(BlogEntry currentBlogEntry : blogEntries) {
            if(currentBlogEntry.getSlug().equals(blogSlug)) {
                blogEntryWithRequiredTitle = currentBlogEntry;
            }
        }
        blogEntryWithRequiredTitle.getComments().add(new Comment(commentName,commentBody));
        //If blog corresponding to the user provided title is not available
        if(blogEntryWithRequiredTitle == null) {
            throw new BlogNotFoundException();
        }
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
