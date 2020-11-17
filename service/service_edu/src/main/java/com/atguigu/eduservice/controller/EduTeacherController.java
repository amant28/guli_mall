package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-17
 */
//表示内容需要返回json数据
@RestController
//访问路径
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    //访问地址：http://localhost:8001/eduservice/teacher/findAll
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师的所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("item",list);
    }
    //讲师逻辑删除
    @DeleteMapping("{id}")
    //id通过路径传id值
    public R removeById(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //分页查询方法
    @GetMapping("pageTeacher/{current}/{limit}")
    //current代表当前页，limit代表每页数
    public R pageListTeacher(@PathVariable long current,
                              @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法的时候，底层封装，把分页所有数据封装到pageTeacher
        teacherService.page(pageTeacher,null);
        long total =pageTeacher.getTotal();
        List<EduTeacher> records=pageTeacher.getRecords();
        //方式一
        return R.ok().data("total",total).data("rows",records);
        //方式2
//        Map<> map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);
    }
    //多条件组合查询带分页
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  //required = false  可以没有
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>();
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //判断条件值是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        teacherService.page(pageTeacher,null);
        long total =pageTeacher.getTotal();
        List<EduTeacher> records=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师接口方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
       boolean save = teacherService.save(eduTeacher);
       if (save){
           return R.ok();
       }else {
           return R.error();
       }
    }
    //根据id查询讲师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    //更新讲师
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

