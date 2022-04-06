package com.feidea.service.impl;

import com.alibaba.fastjson.JSON;
import com.feidea.Exception.NotFoundException;
import com.feidea.dao.BlogMapper;
import com.feidea.entity.Blog;
import com.feidea.service.BlogService;
import com.feidea.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    //redis依赖
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Override
    public List<BlogQuery> getAllBlogQuery() {
        return blogMapper.getAllBlogQuery();
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogMapper.saveBlog(blog);
    }

    @Override
    public int updateBlog(ShowBlog showBlog) {
        showBlog.setUpdateTime(new Date());
        return blogMapper.updateBlog(showBlog);
    }

    @Override
    public ShowBlog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public void deleteBlog(Long id) {
       blogMapper.deleteBlog(id);
    }

    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        String firstPageBlog = (String) redisTemplate.opsForValue().get("FirstPageBlog");

        if (!StringUtils.isEmpty(firstPageBlog)) {
            List<FirstPageBlog> firstPageBlogs = JSON.parseArray(firstPageBlog, FirstPageBlog.class);
            return firstPageBlogs;
        } else {
            List<FirstPageBlog> firstPageBlogList = blogMapper.getAllFirstPageBlog();
            String json = JSON.toJSONString(firstPageBlogList);
            redisTemplate.opsForValue().set("FirstPageBlog", json, 1, TimeUnit.DAYS);
            return firstPageBlogList;
        }
    }

    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        String recommendBlog = (String) redisTemplate.opsForValue().get("RecommendBlog");

        if (!StringUtils.isEmpty(recommendBlog)) {
            List<RecommendBlog> recommendBlogs = JSON.parseArray(recommendBlog, RecommendBlog.class);
            return recommendBlogs;
        } else {
            List<RecommendBlog> recommendBlogList = blogMapper.getRecommendedBlog();
            String json = JSON.toJSONString(recommendBlogList);
            redisTemplate.opsForValue().set("RecommendBlog", json, 1, TimeUnit.DAYS);
            return recommendBlogList;
        }

    }

    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        return blogMapper.getSearchBlog(query);
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        DetailedBlog detailedBlog = blogMapper.getDetailedBlog(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();

        blogMapper.updateViews(id);
        blogMapper.getCommentCountById(id);
        return detailedBlog;
    }

    @Override
    public Integer getBlogTotal() {
        return blogMapper.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return blogMapper.getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return blogMapper.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return blogMapper.getBlogMessageTotal();
    }

    @Override
    public int updateViews(Long id) {
        return blogMapper.updateViews(id);
    }

    @Override
    public int getCommentCountById(Long id) {
        return blogMapper.getCommentCountById(id);
    }

    @Override
    public List<FirstPageBlog> getByTypeId(Long typeId) {
        return blogMapper.getByTypeId(typeId);
    }
}
