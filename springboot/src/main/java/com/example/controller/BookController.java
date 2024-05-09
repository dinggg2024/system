package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Book;
import com.example.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图书信息前端操作接口
 **/
@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        bookService.add(book);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        bookService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Book book) {
        bookService.updateById(book);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Book book = bookService.selectById(id);
        return Result.success(book);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Book book) {
        List<Book> list = bookService.selectAll(book);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Book book,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Book> page = bookService.selectPage(book, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/pie")
    public Result getPie() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Book> books = bookService.selectAll(new Book());
        // 所有图书根据图书分类做筛选，重新处理数据结构，将List<Book>变成Map<String,Long>的数据结构
        Map<String, Long> collect = books.stream().filter(x -> ObjectUtil.isNotEmpty(x.getTypeName()))
                .collect(Collectors.groupingBy(Book::getTypeName, Collectors.counting()));

        // 创建一个List，用于存储饼图数据
        List<Map<String, Object>> list = new ArrayList<>();
        // 遍历新的数据结构，开始封装饼图要展示的数据
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", collect.get(key));
            // 将每个分类对应的数据塞到list中
            list.add(map);
        }

        resultMap.put("text", "平台图书数量统计");
        resultMap.put("subText", "统计维度：图书分类");
        resultMap.put("data", list);
        return Result.success(resultMap);
    }

}