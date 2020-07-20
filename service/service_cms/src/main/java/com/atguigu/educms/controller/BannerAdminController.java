package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private  CrmBannerService bannerService; 
    
    
    // 分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page,
                        @PathVariable("limit") long limit){
        Page<CrmBanner> bannerPage =new Page<>(page,limit);

        bannerService.page(bannerPage, null);


        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }
    
    // 根据id查
    @GetMapping("get/{id}")
    public  R get(@PathVariable("id")String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item",banner);
    }
    
    // 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        boolean save = bannerService.save(banner);
        if (save) {
            return R.ok();
        }else{
            return R.error();
        }
    }
    
    
    // 修改Banner
    @PostMapping("update")
    public R updateBanner(@RequestBody CrmBanner banner){
        boolean update = bannerService.updateById(banner);
        if (update) {
            return R.ok();
        }else{
            return R.error();
        }
    }
    
    // 删除
    @DeleteMapping("remove/{bannerId}")
    public R deleteBanner(@PathVariable("bannerId") String bannerId){
        bannerService.removeById(bannerId);
        return R.ok();
    }
}

