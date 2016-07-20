package com.yougou.wfx.customer.service.system.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yougou.wfx.basicset.api.front.ISystemmgAreaFrontApi;
import com.yougou.wfx.basicset.dto.output.SystemmgAreaOutputDto;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.system.api.IWFXSystemApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/5/5
 */
@Service
public class SystemServiceImpl implements ISystemService {

    private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);
    @Autowired
    private IWFXSystemApi iwfxSystemApi;
    @Autowired
    private ISystemmgAreaFrontApi systemmgAreaFrontApi;

    /**
     * 获取购物车最大sku数量
     *
     * @return
     */
    @Override
    public int getShoppingCartSkusCount() {
        String value = iwfxSystemApi.getSystemConfigValue(WfxConstant.WFX_ORDER_SHOPPINGCART_MAX_NUM_KEY);
        int retValue = WfxConstant.WFX_SHOPPINGCART_SKUs_COUNT_DEFAULT;
        if (StringUtils.hasText(value)) {
            try {
                retValue = Integer.parseInt(value);
            } catch (Exception ex) {
                log.error("{}类型转化失败!", value);
            }
        }
        return retValue;
    }

    /**
     * 购物车每个sku最多购买数量
     *
     * @return
     */
    @Override
    public int getShoppingCartMaxSkuCount() {
        String value = iwfxSystemApi.getSystemConfigValue(WfxConstant.WFX_ORDER_SINGLE_COMM_MAX_NUM_KEY);
        int retValue = WfxConstant.WFX_SHOPPINGCART_SKU_MAX_COUNT_DEFAULT;
        if (StringUtils.hasText(value)) {
            try {
                retValue = Integer.parseInt(value);
            } catch (Exception ex) {
                log.error("{}类型转化失败!", value);
            }
        }
        return retValue;
    }

    /**
     * 获取用户一天可下单的订单总数量
     *
     * @since 1.0 Created by lipangeng on 16/5/6 下午2:37. Email:lipg@outlook.com.
     */
    @Override
    public int getUserOneDayOrderNum() {
        String value = iwfxSystemApi.getSystemConfigValue(WfxConstant.WFX_ORDER_USER_ONE_DAY_MAX_KEY);
        int result = WfxConstant.WFX_ORDER_USER_ONE_DAY_MAX;
        if (! Strings.isNullOrEmpty(value)) {
            try {
                result = Integer.parseInt(value);
            } catch (Exception e) {
                log.error("获取用户一天可下单总量错误", e);
            }
        }
        return result;
    }

    @Override
    public String getConfigByKey(String key) {
        return iwfxSystemApi.getSystemConfigValue(key);
    }

    /**
     * 获取图片域名
     *
     * @return
     */
    @Override
    public String obtainImgBaseUrl() {
        return iwfxSystemApi.obtainImgBaseUrl();
    }

    /**
     * 获取全国地址的json
     *
     * @since 1.0 Created by lipangeng on 16/5/18 下午4:30. Email:lipg@outlook.com.
     */
    @Override
    public String areaJson() {
        // 所有省份信息
        List<Map<String, Object>> rootMaps = Lists.newArrayList();
        List<SystemmgAreaOutputDto> provinces = systemmgAreaFrontApi.queryAreaList(1, "root");
        for (SystemmgAreaOutputDto province : provinces) {
            Map<String, Object> provinceMap = Maps.newHashMap();
            rootMaps.add(provinceMap);
            provinceMap.put("text", province.getName());
            List<Map<String, Object>> provinceChildList = Lists.newArrayList();
            provinceMap.put("children", provinceChildList);
            // 城市信息
            List<SystemmgAreaOutputDto> citys = systemmgAreaFrontApi.queryAreaList(2, province.getNo());
            for (SystemmgAreaOutputDto city : citys) {
                Map<String, Object> cityMap = Maps.newHashMap();
                provinceChildList.add(cityMap);
                cityMap.put("text", city.getName());
                List<Map<String, Object>> cityChildList = Lists.newArrayList();
                cityMap.put("children", cityChildList);
                // 城区信息
                List<SystemmgAreaOutputDto> areas = systemmgAreaFrontApi.queryAreaList(3, city.getNo());
                for (SystemmgAreaOutputDto area : areas) {
                    Map<String, Object> areaMap = Maps.newHashMap();
                    areaMap.put("text", area.getName());
                    cityChildList.add(areaMap);
                }
            }
        }
        return JacksonUtils.Convert(rootMaps);
    }


}
