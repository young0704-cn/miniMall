package com.home.miniMall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.home.miniMall.pojo.ProductInfo;
import com.home.miniMall.service.ProductService;
import com.home.miniMall.utils.FileNameUtil;
import com.home.miniMall.utils.MyException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    @Autowired
    private ProductService service;
    String saveFileName="";

    @RequestMapping("/getAll.action")
    public String method01(Model model){
        List<ProductInfo> productInfos=service.getAll();
        model.addAttribute("list",productInfos);
        return "product";
    }

    @RequestMapping("/split.action")
    public String method02(Model model,Integer pageNum){
        PageInfo<ProductInfo> pageInfo=service.split(pageNum,5,null,null,null);
        model.addAttribute("info",pageInfo);
        return "product";
    }

    @RequestMapping("/ajaxsplit.action")
    @ResponseBody
    public void method03(Integer page,ProductInfo productInfo,Integer lprice,Integer hprice,HttpServletRequest request){
//        PageInfo<ProductInfo> pageInfo=service.split(page,5);
//        request.getSession().setAttribute("info",pageInfo);

        PageInfo<ProductInfo> pageInfo=service.split(page,5,productInfo,lprice,hprice);
        request.getSession().setAttribute("info",pageInfo);
    }

    @RequestMapping("/ajaxImg.action")
    @ResponseBody
    public String method04(MultipartFile pimage,HttpServletRequest request) throws IOException {
        //?????????????????????????????????,UUID.??????
        saveFileName= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //?????????????????????????????????
        String path=request.getServletContext().getRealPath("image_big");
        //??????????????????
        String savePath=path+File.separator+saveFileName;//????????????
        pimage.transferTo(new File(savePath));//????????????
        //??????json???????????????????????????????????????
        ObjectMapper om=new ObjectMapper();
        return om.writeValueAsString(saveFileName);
    }

    @RequestMapping("/save.action")
    public String method05(ProductInfo productInfo,Model model){
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());

        if (1!=service.save(productInfo)){
            model.addAttribute("msg","????????????");
        }else {
            model.addAttribute("msg","????????????");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one.action")
    public String method06(ProductInfo productInfo,int page,Model model){
        ProductInfo info=service.getById(productInfo);
        model.addAttribute("prod",info);
        return "update";
    }

    @RequestMapping("/update.action")
    public String method07(ProductInfo productInfo,Model model,Integer pageNum){
        //????????????????????????????????????????????????????????????saveFileName????????????????????????????????????saveFileName???UUID?????????
        //???saveFileName?????????productInfo??????????????????pImage??????
        if (!saveFileName.equals("")){
            productInfo.setpImage(saveFileName);
        }
        if (1!=service.update(productInfo)){
            model.addAttribute("msg","????????????");
        }else {
            model.addAttribute("msg","????????????");
        }
        saveFileName="";
        return "forward:/prod/split.action?pageNum="+pageNum;
    }

    @RequestMapping("/delete.action")
    @ResponseBody
    public String method08(ProductInfo productInfo,Model model) throws JsonProcessingException {

        String msg= null;
        try {
            msg = service.delete(productInfo);
        } catch (Exception e) {
            msg="?????????????????????????????????";
        }
        ObjectMapper om=new ObjectMapper();
        return om.writeValueAsString(msg);
    }

    @RequestMapping("/deletebatch.action")
    public String method09(int[] id, Model model,Integer pageNum) {
        String msg= null;
        try {
            msg = service.deletebatch(id);
        }catch (Exception e){
            msg="????????????????????????????????????";
        }

        model.addAttribute("msg",msg);
        return "forward:/prod/split.action?pageNum="+pageNum;
    }
}
