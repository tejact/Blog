package com.tejatummalapalli.sparkblog.dao;

import com.tejatummalapalli.sparkblog.Exceptions.BlogNotFoundException;
import com.tejatummalapalli.sparkblog.model.BlogEntry;
import com.tejatummalapalli.sparkblog.model.Comment;

import java.util.List;

/**
 * Created by Teja on 10/18/2016.
 */

public interface BlogEntryDAO {
    public BlogEntry getBlogEntry(String blogSlug) throws BlogNotFoundException;
    public List<BlogEntry> getAllBlogs();
    public void addBlogEntry(BlogEntry blogEntry);
    public void addComment(BlogEntry blogEntry ,Comment comment) throws BlogNotFoundException;
    public void addComment(String slug ,String commentName , String commentBody) throws BlogNotFoundException;
    public List<Comment> getAllComments(BlogEntry blogEntry) throws BlogNotFoundException;
}
