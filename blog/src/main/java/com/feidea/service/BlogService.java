package com.feidea.service;

import com.feidea.entity.Blog;
import com.feidea.vo.*;

import java.util.List;

public interface BlogService {

    List<Blog> getAllBlog();

    List<BlogQuery> getAllBlogQuery();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    ShowBlog getBlogById(Long id);

    void deleteBlog(Long id);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getRecommendedBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    Integer getBlogTotal();

    Integer getBlogViewTotal();

    Integer getBlogCommentTotal();

    Integer getBlogMessageTotal();

    int updateViews(Long id);

    int getCommentCountById(Long id);

    List<FirstPageBlog> getByTypeId(Long id);
}
