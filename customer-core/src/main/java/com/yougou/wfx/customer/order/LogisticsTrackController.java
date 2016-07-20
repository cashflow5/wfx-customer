package com.yougou.wfx.customer.order;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.order.LogisticsVo;
import com.yougou.wfx.customer.model.order.OrderVo;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.order.ILogisticsService;
import com.yougou.wfx.customer.service.order.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 物流跟踪页面
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/11 下午3:07
 * @since 1.0 Created by lipangeng on 16/4/11 下午3:07. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("order/{orderId}/logistics")
public class LogisticsTrackController {
    private static final Logger logger = LoggerFactory.getLogger(LogisticsTrackController.class);
    @Autowired
    private ILogisticsService logisticsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICommodityService commodityService;

    /**
     * 获取订单的物流信息
     *
     * @since 1.0 Created by lipangeng on 16/4/11 下午3:51. Email:lipg@outlook.com.
     */
    @RequestMapping
    @LoginValidate
    public String logistics(@PathVariable("orderId") String orderId,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            Model model) {
        OrderVo userOrder = orderService.getUserOrder(SessionUtils.getLoginUserDetails().getUserId(), orderId);
        if (userOrder == null) {
            model.addAttribute("errorMsg", "订单不存在或无权访问此订单物流信息");
            return "redirect:/order/" + orderId + ".sc";
        }
        List<LogisticsVo> logistics = logisticsService.getLogisticsByOrder(orderId);
        page = page > logistics.size() ? logistics.size() : page;
        model.addAttribute("order", userOrder);
        model.addAttribute("logisticses", logistics);
        if (logistics.size() > 0) {
            model.addAttribute("logistics", logistics.get(page - 1));
            model.addAttribute("page", page);
            // 注入商品的no
            List<String> commodityIds = Lists.newArrayList();
            Map<String, String> commodityNos = Maps.newHashMap();
            for (LogisticsVo.Style style : logistics.get(page - 1).getStyles()) {
                commodityIds.add(style.getCommodityId());
            }
            List<CommodityStyleVo> commodityList = commodityService.getCommodityListByIds(commodityIds, false, false);
            if (commodityList != null) {
                for (CommodityStyleVo commodityStyleVo : commodityList) {
                    commodityNos.put(commodityStyleVo.getId(), commodityStyleVo.getNo());
                }
            }
            model.addAttribute("commodityNos", commodityNos);
        }
        return "order/logistics-track";
    }
}
