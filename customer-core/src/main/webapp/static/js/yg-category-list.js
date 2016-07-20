/**
 * create by guoran
 */
var YG_Category_List = (function ($, M) {
    function Init() {
        CategoryPageHeight();
        Category();
        LinkJump();
    }

    function Category() {
        $('.yg-category-tabs').on('tap', '.yg-category-tab', function (e) {
            e.preventDefault();
            var $this = $(this),
                _id = $this.attr('href');
            $this.addClass('active').siblings('.active').removeClass('active');
            $(_id).addClass('active').siblings('.active').removeClass('active');
        });
    }

    function CategoryPageHeight() {
        var windowHeight = $(window).height() - 44 - 20;
        $('.yg-category-tabs').height(windowHeight);
        $('.yg-category-list-items').height(windowHeight);
    }

    function LinkJump() {
        $('#indexMenu').on('tap', '.mui-tab-item', function () {
            location.href = $(this).attr('href');
        });
        $('.yg-category-item').on('tap','a',function(){
            var $this = $(this),
                shopId=$('#shopId').val();
            $this.addClass('active').siblings('.active').removeClass('active');
            location.href =rootPath+"/"+shopId+"/category/"+$this.data('id')+'.sc';
        });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));