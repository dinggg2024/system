package com.example.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.exception.CustomException;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 借阅管理业务处理
 **/
@Service
public class BorrowService {

    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BookMapper bookMapper;

    /**
     * 新增
     */
    public void add(Borrow borrow) {
        // 先判断下图书的剩余数量
        Book book = bookMapper.selectById(borrow.getBookId());
        if (0 == book.getNum()) {
            throw new CustomException(ResultCodeEnum.BOOK_NO_ERROR);
        }

        // 默认借阅15天
        Date today = new Date();
        borrow.setStartTime(DateUtil.now());
        DateTime days = DateUtil.offsetDay(today, 15);
        borrow.setEndTime(days.toString("yyyy-MM-dd HH:mm:ss"));
        borrowMapper.insert(borrow);

        // 借阅成功后，图书剩余数量 -1
        book.setNum(book.getNum() - 1);
        bookMapper.updateById(book);

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            borrowMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Borrow borrow) {
        if (StatusEnum.CHECK_OK.status.equals(borrow.getStatus())) {
            Borrow dbBorrow = borrowMapper.selectById(borrow.getId());
            if (StatusEnum.CHECK_AGAIN.status.equals(dbBorrow.getStatus())) {
                // 审核通过，状态变成续借中，更新下实际的归还时间
                borrow.setStatus(StatusEnum.BORROW_AGAIN.status);
                // 默认借阅15天
                String endTime = dbBorrow.getEndTime();
                DateTime days = DateUtil.offsetDay(DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss"), 15);
                borrow.setEndTime(days.toString("yyyy-MM-dd HH:mm:ss"));
            }
            if (StatusEnum.CHECK_BACK.status.equals(dbBorrow.getStatus())) {
                // 审核通过，状态变成已归还，更新下实际的归还时间
                borrow.setStatus(StatusEnum.BACKED.status);
                borrow.setEndTime(DateUtil.now());
            }
            // 图书数量 +1
            Book book = bookMapper.selectById(borrow.getBookId());
            if (ObjectUtil.isNotEmpty(book)) {
                book.setNum(book.getNum() + 1);
                bookMapper.updateById(book);
            }
        }
        borrowMapper.updateById(borrow);
    }

    /**
     * 根据ID查询
     */
    public Borrow selectById(Integer id) {
        return borrowMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Borrow> selectAll(Borrow borrow) {
        return borrowMapper.selectAll(borrow);
    }

    /**
     * 分页查询
     */
    public PageInfo<Borrow> selectPage(Borrow borrow, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Borrow> list = this.selectAll(borrow);
        return PageInfo.of(list);
    }

}