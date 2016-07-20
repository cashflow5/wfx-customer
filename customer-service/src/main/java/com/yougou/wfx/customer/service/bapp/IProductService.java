package com.yougou.wfx.customer.service.bapp;


import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.product.OnAndOffProduct;
import com.yougou.wfx.appserver.vo.product.Product;
import com.yougou.wfx.appserver.vo.product.ProductDetail;
import com.yougou.wfx.appserver.vo.product.ProductSearcher;
import com.yougou.wfx.appserver.vo.product.ProductShelf;
import com.yougou.wfx.appserver.vo.product.ShopProductSearcher;
import com.yougou.wfx.appserver.vo.supply.BrandProductSearcher;
import com.yougou.wfx.appserver.vo.supply.CategoryBrandInfo;
import com.yougou.wfx.appserver.vo.supply.CategoryBrandSearcher;
import com.yougou.wfx.appserver.vo.supply.CategoryProductSearcher;

/**
 * Created by lizhw on 2016/4/11.
 */
public interface IProductService  extends IBaseService{
    PageSearchResult<ShopProductSearcher, Product> getShopProduct(ShopProductSearcher searcher);

    OnAndOffProduct productShelfManage(ProductShelf onAndoff);

    PageSearchResult<CategoryBrandSearcher,CategoryBrandInfo> getCategoryBrandInfo(CategoryBrandSearcher searcher);

    PageSearchResult<CategoryProductSearcher,Product> getProductByLeafCategory(CategoryProductSearcher searcher);

    ProductDetail getProductDetailById(String productId,UserInfo userInfo);

    PageSearchResult<ProductSearcher,Product> searchProduct(ProductSearcher searcher);

    PageSearchResult<BrandProductSearcher,Product> getProductByBrandNo(BrandProductSearcher searcher);
}
