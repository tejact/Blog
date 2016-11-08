package com.tejatummalapalli.sparkblog.dao;

import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.Exceptions.CommentNotValidException;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import com.tejatummalapalli.sparkblog.model.Comment;

import java.util.List;

/**
 * Created by Teja on 10/18/2016.
 */

public interface BlogEntryDAO {
    List<BlogEntry> getAllBlogs();
    void addBlogEntry(BlogEntry blogEntry);
    BlogEntry getBlogEntryForSlug(String blogSlug) throws BlogNotFoundException;
    void addComment(String slug ,String commentName , String commentBody) throws BlogNotFoundException, CommentNotValidException;
    List<Comment> getAllComments(String blogSlug) throws BlogNotFoundException;
}
