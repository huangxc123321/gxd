package com.gxa.jbgsw.basis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxa.jbgsw.basis.entity.ProvinceCityDistrict;
import com.gxa.jbgsw.basis.mapper.ProvinceCityDistrictMapper;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryRequest;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictQueryResponse;
import com.gxa.jbgsw.basis.protocol.dto.ProvinceCityDistrictVO;
import com.gxa.jbgsw.basis.service.ProvinceCityDistrictService;
import com.gxa.jbgsw.common.utils.PageResult;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 省市县数据表 服务实现类
 * </p>
 *
 * @author huangxc
 * @since 2021-03-26
 */
@Service
public class ProvinceCityDistrictServiceImpl extends ServiceImpl<ProvinceCityDistrictMapper, ProvinceCityDistrict> implements ProvinceCityDistrictService {
    @Resource
    ProvinceCityDistrictMapper provinceCityDistrictMapper;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public PageResult<ProvinceCityDistrictQueryResponse> pageQuery(ProvinceCityDistrictQueryRequest request) {
        // 分页查询
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        // 根据条件查询
        LambdaQueryWrapper<ProvinceCityDistrict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(request.getId() != null){
            lambdaQueryWrapper.eq(ProvinceCityDistrict::getId, request.getId());
        }
        if(request.getPid() != null){
            lambdaQueryWrapper.eq(ProvinceCityDistrict::getPid, request.getPid());
        }
        if(StrUtil.isNotBlank(request.getName())){
            lambdaQueryWrapper.like(ProvinceCityDistrict::getName, request.getName());
        }
        List<ProvinceCityDistrict> provinceCityDistricts = provinceCityDistrictMapper.selectList(lambdaQueryWrapper);

        // 对象转换
        PageInfo<ProvinceCityDistrict> pageInfo = new PageInfo<>(provinceCityDistricts);

        // 返回查询对象
        return mapperFacade.map(pageInfo, new TypeBuilder<PageInfo<ProvinceCityDistrict>>() {
        }.build(), new TypeBuilder<PageResult<ProvinceCityDistrictQueryResponse>>() {}.build());
    }

    @Override
    public List<ProvinceCityDistrictQueryResponse> pageSonQuery(String pid) {
        // 根据条件查询
        LambdaQueryWrapper<ProvinceCityDistrict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(pid != null){
            lambdaQueryWrapper.eq(ProvinceCityDistrict::getPid, pid);
        }
        List<ProvinceCityDistrict> provinceCityDistricts = provinceCityDistrictMapper.selectList(lambdaQueryWrapper);

        // 对象转换
        return mapperFacade.mapAsList(provinceCityDistricts, ProvinceCityDistrictQueryResponse.class);
    }

    @Override
    public ProvinceCityDistrictVO getProvinceCityDistrictById(Integer id) {
        ProvinceCityDistrictVO provinceCityDistrictVO = new ProvinceCityDistrictVO();

        // 根据条件查询
        ProvinceCityDistrict provinceCityDistrict = provinceCityDistrictMapper.selectById(id);
        if(provinceCityDistrict != null && provinceCityDistrict.getPid() == 0){
            // 说明是省级
            provinceCityDistrictVO.setProvinceId(provinceCityDistrict.getId());
            provinceCityDistrictVO.setProvinceName(provinceCityDistrict.getName());

            return provinceCityDistrictVO;
        }

        Long pid = provinceCityDistrict.getPid();
        ProvinceCityDistrict one = provinceCityDistrictMapper.selectById(pid);
        if(one != null && one.getPid() == 0){
            //说明是市级
            provinceCityDistrictVO.setProvinceId(one.getId());
            provinceCityDistrictVO.setProvinceName(one.getName());

            provinceCityDistrictVO.setCityId(provinceCityDistrict.getId());
            provinceCityDistrictVO.setCityName(provinceCityDistrict.getName());

            return provinceCityDistrictVO;
        }

        Long nextPid = one.getPid();
        ProvinceCityDistrict two = provinceCityDistrictMapper.selectById(nextPid);
        if(two != null && two.getPid() == 0){
            //说明是区级
            provinceCityDistrictVO.setProvinceId(two.getId());
            provinceCityDistrictVO.setProvinceName(two.getName());

            provinceCityDistrictVO.setCityId(one.getId());
            provinceCityDistrictVO.setCityName(one.getName());

            provinceCityDistrictVO.setAreaId(provinceCityDistrict.getId());
            provinceCityDistrictVO.setAreaName(provinceCityDistrict.getName());

            return provinceCityDistrictVO;
        }

        return provinceCityDistrictVO;
    }

    @Override
    public ProvinceCityDistrictVO getProvinceCityDistrictByName(String name) {
        ProvinceCityDistrictVO provinceCityDistrictVO = new ProvinceCityDistrictVO();

        // 根据条件查询
        LambdaQueryWrapper<ProvinceCityDistrict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(ProvinceCityDistrict::getName, name);
        lambdaQueryWrapper.last("limit 1");

        ProvinceCityDistrict provinceCityDistrict = provinceCityDistrictMapper.selectOne(lambdaQueryWrapper);
        if(provinceCityDistrict == null){
            return null;
        }

        if(provinceCityDistrict != null && provinceCityDistrict.getPid() == 0){
            // 说明是省级
            provinceCityDistrictVO.setProvinceId(provinceCityDistrict.getId());
            provinceCityDistrictVO.setProvinceName(provinceCityDistrict.getName());

            return provinceCityDistrictVO;
        }

        Long pid = provinceCityDistrict.getPid();
        ProvinceCityDistrict one = provinceCityDistrictMapper.selectById(pid);
        if(one != null && one.getPid() == 0){
            //说明是市级
            provinceCityDistrictVO.setProvinceId(one.getId());
            provinceCityDistrictVO.setProvinceName(one.getName());

            provinceCityDistrictVO.setCityId(provinceCityDistrict.getId());
            provinceCityDistrictVO.setCityName(provinceCityDistrict.getName());

            return provinceCityDistrictVO;
        }

        Long nextPid = one.getPid();
        ProvinceCityDistrict two = provinceCityDistrictMapper.selectById(nextPid);
        if(two != null && two.getPid() == 0){
            //说明是区级
            provinceCityDistrictVO.setProvinceId(two.getId());
            provinceCityDistrictVO.setProvinceName(two.getName());

            provinceCityDistrictVO.setCityId(one.getId());
            provinceCityDistrictVO.setCityName(one.getName());

            provinceCityDistrictVO.setAreaId(provinceCityDistrict.getId());
            provinceCityDistrictVO.setAreaName(provinceCityDistrict.getName());

            return provinceCityDistrictVO;
        }

        return provinceCityDistrictVO;
    }
}
