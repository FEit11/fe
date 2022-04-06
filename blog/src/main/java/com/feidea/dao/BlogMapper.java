package com.feidea.dao;

import com.feidea.entity.Blog;
import com.feidea.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {

    //获取所有博客信息
    List<Blog> getAllBlog();

    //查询博客管理列表
    List<BlogQuery> getAllBlogQuery();

    //新增博客信息
    int saveBlog(Blog blog);

    //更新博客信息
    int updateBlog(ShowBlog showBlog);

    //通过id获取博客
    ShowBlog getBlogById(Long id);

    //删除博客
    void deleteBlog(Long id);

    //获取首页最新博客信息
    List<FirstPageBlog> getAllFirstPageBlog();

    //获取推荐博客信息
    List<RecommendBlog> getRecommendedBlog();

    //获取博客查询信息
    List<FirstPageBlog> getSearchBlog(String query);

    //获取博客详情
    DetailedBlog getDetailedBlog(Long id);

    //统计博客总数
    Integer getBlogTotal();

    //统计访问次数
    Integer getBlogViewTotal();

    //统计评论总数
    Integer getBlogCommentTotal();

    //统计留言总数
    Integer getBlogMessageTotal();

    //更新阅读人数
    int updateViews(Long id);

    //根据博客id查询出评论数量
    int getCommentCountById(Long id);

    //通过类型id获取博客信息
    List<FirstPageBlog> getByTypeId(Long typeId);
}
