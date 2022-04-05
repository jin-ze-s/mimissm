package com.tom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.tom.pojo.ProductInfo;
import com.tom.pojo.vo.ProductInfoVo;
import com.tom.service.ProductInfoService;
import com.tom.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author tom
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    @Autowired
    ProductInfoService productInfoService;

    //文件异步上传的图片名称
    String saveFileName = "";

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(Model model){
        List<ProductInfo> productInfos = productInfoService.getAll();
        model.addAttribute("list",productInfos);
        return "product";
    }

    /**
     * 显示第一页数据
     * @param pageNo 页码
     * @param session session对象
     * @return 页面
     */
    @RequestMapping("/getPage")
    public String getPage(Integer pageNo,HttpSession session){
        if (pageNo==null){
            pageNo = 1;
        }
        PageInfo<ProductInfo> pageInfo = productInfoService.getPage(pageNo);
        session.setAttribute("pageInfo",pageInfo);
        return "product";
    }

    /**
     * 分页
     * @param vo 前端传入后端的信息封装对象
     * @param session session对象
     */
    @RequestMapping("/splitPage")
    @ResponseBody
    public void splitPage(ProductInfoVo vo, HttpSession session){

        System.out.println(vo);

        PageInfo<ProductInfo> pageInfo = productInfoService.splitPageVo(vo);
        session.setAttribute("pageInfo",pageInfo);
    }

    /**
     * 分页
     * @param vo 前端传入后端的信息封装对象
     * @param session session对象
     */
    @RequestMapping("/splitBackPage")
    public String splitBackPage(ProductInfoVo vo, HttpSession session,Model model){

        System.out.println(vo);

        PageInfo<ProductInfo> pageInfo = productInfoService.splitPageVo(vo);
        session.setAttribute("pageInfo",pageInfo);
        model.addAttribute("vo",vo);
        return "product";
    }

    /**
     * 多条件查询
     */
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);

    }

    //异步文件上传

    /**
     *
     * @param pimage 此参数名要和文件域中的name属性值相同
     * @return 返回json
     */
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        //提取生成文件名UUID+上传图片的后缀
        saveFileName = FileNameUtil.getUUIDFileName()+ FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        obj.put("imgurl",saveFileName);
        //返回json字符串
        return obj.toString();
    }

    /**
     * 新增
     * @param info 新增的商品信息
     * @param model model对选哪个
     * @return 页面
     */
    @RequestMapping("/save")
    public String save(ProductInfo info,Model model){
        info.setpImage(saveFileName);
        Date date = new Date();
        System.out.println(date);
        info.setpDate(date);
        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0){
            model.addAttribute("msg","添加成功");
        }else {
            model.addAttribute("msg","添加失败");
        }
        //添加完成后，重定向到显示页面
        return "forward:/prod/getPage.action";
    }

    @RequestMapping("/one")
    public String one(Integer pid,ProductInfoVo vo,Model model){
        System.out.println(vo);
        ProductInfo info = productInfoService.getOne(pid);
        if (info != null) {
            model.addAttribute("prod", info);
        }
        model.addAttribute("productInfoVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,ProductInfoVo vo,HttpSession session,Model model){
        System.out.println(vo);
        int num = -1;
        num = productInfoService.update(info);
        if (num > 0){
            model.addAttribute("msg","修改成功");
        }else {
            model.addAttribute("msg","修改失败");
        }
        PageInfo<ProductInfo> pageInfo = productInfoService.splitPageVo(vo);
        session.setAttribute("pageInfo",pageInfo);
        model.addAttribute("vo",vo);
        return "product";

    }

    /**
     * 单个文件删除
     */
    @RequestMapping("/delete")
    public String delete(Integer pid,ProductInfoVo vo,Model model,HttpSession session){
        int num = -1;
        num = productInfoService.delete(pid);
        if (num > 0){
            model.addAttribute("msg","删除成功");
        }else {
            model.addAttribute("msg","删除失败");
        }
        PageInfo<ProductInfo> pageInfo = productInfoService.splitPageVo(vo);
        session.setAttribute("pageInfo",pageInfo);
        model.addAttribute("vo",vo);
        return "product";
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/batchDelete",method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(HttpServletRequest request){
        String pids = request.getParameter("pids");
        JSONArray jsonArray = JSON.parseArray(pids);
        Object[] array = jsonArray.stream().toArray();
        Integer[] prodIds = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            prodIds[i] = Integer.parseInt(array[i].toString());
        }
        productInfoService.deleteBatch(prodIds);
        JSONObject obj = new JSONObject();
        obj.put("msg",true);
        //返回json字符串
        return obj.toString();
    }


}
