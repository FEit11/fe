<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/lib/layui-v2.5.5/css/layui.css"
          media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/css/public.css" media="all">
    <style>
        .thumbBox{ height:200px; overflow:hidden; border:1px solid #e6e6e6; border-radius:2px; cursor:pointer; position:relative; text-align:center; line-height:200px;width: 210px;}
        .thumbImg{ max-width:100%; max-height:100%; border:none;}
        .thumbBox:after{ position:absolute; width:100%; height:100%;line-height:200px; z-index:-1; text-align:center; font-size:20px; content:"缩略图"; left:0; top:0; color:#9F9F9F;}
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">

        <%-- 搜索条件 --%>
        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">房型名称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="typename" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="submit" class="layui-btn" lay-submit lay-filter="data-search-btn"><i
                                    class="layui-icon layui-icon-search"></i>搜索
                            </button>
                            <button type="reset" class="layui-btn layui-btn-warm"><i
                                    class="layui-icon layui-icon-refresh-1"></i>重置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>

        <%-- 表格工具栏 --%>
        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add"><i
                        class="layui-icon layui-icon-add-1"></i>添加
                </button>
            </div>
        </script>

        <%-- 数据表格 --%>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

        <%-- 行工具栏 --%>
        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit"><i
                    class="layui-icon layui-icon-edit"></i>编辑</a>
        </script>

        <%-- 添加和修改窗口 --%>
        <div style="display: none;padding: 5px" id="addOrUpdateWindow">
            <form class="layui-form" style="width:90%;" id="dataFrm" lay-filter="dataFrm">
                <!-- 隐藏域，保存房型id -->
                <input type="hidden" name="id">
                <div class="layui-col-md12 layui-col-xs12">
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-md9 layui-col-xs7">
                            <div class="layui-form-item magt3" style="margin-top: 8px;">
                                <label class="layui-form-label">房型名称</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="typename" lay-verify="required"  placeholder="请输入房型名称">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">房型状态</label>
                                <div class="layui-input-block">
                                    <select name="status" id="status" lay-verify="required">
                                        <option value="">请选择房型状态</option>
                                        <option value="1">可预订</option>
                                        <option value="2">房型已满</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">房型备注</label>
                                <div class="layui-input-block">
                                    <textarea class="layui-textarea" name="remark" id="remark"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md3 layui-col-xs5">
                            <div class="layui-upload-list thumbBox mag0 magt3">
                                <input type="hidden" name="photo" id="photo" value="images/defaultimg.jpg">
                                <img class="layui-upload-img thumbImg" src="/hotel/show/images/defaultimg.jpg">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item magb0">
                        <div class="layui-inline">
                            <label class="layui-form-label">参考价格</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="price" lay-verify="required|number"
                                       placeholder="请输入参考价格">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">可住人数</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="livenum" lay-verify="required|number"
                                       placeholder="请输入可住人数">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">床位数</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="bednum" lay-verify="required|number" placeholder="请输入床位数">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">房间数</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="roomnum" lay-verify="required|number"
                                       placeholder="请输入房间数">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block" style="text-align: center;">
                            <button type="button" class="layui-btn" lay-submit lay-filter="doSubmit" id="doSubmit"><span
                                    class="layui-icon layui-icon-add-1"></span>提交
                            </button>
                            <button type="reset" class="layui-btn layui-btn-warm"><span
                                    class="layui-icon layui-icon-refresh-1"></span>重置
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>
</div>
<script src="${pageContext.request.contextPath}/statics/layui/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form', 'table', 'laydate', 'jquery', 'layer','upload'], function () {
        var $ = layui.jquery,
            form = layui.form,
            laydate = layui.laydate,
            upload = layui.upload,
            layer = layui.layer,
            table = layui.table;

        //渲染表格组件
        var tableInfos = table.render({
            elem: '#currentTableId',
            url: '${pageContext.request.contextPath}/admin/roomType/list',
            toolbar: '#toolbarDemo',
            cols: [[
                {field: 'id', width: 60, title: '编号', align: "center"},
                {field: 'typename', minWidth: 150, title: '名称', align: "center"},
                {field: 'price', minWidth: 100, title: '价格', align: "center"},
                {field: 'livenum', minWidth: 100, title: '可住人数', align: "center"},
                {field: 'bednum', minWidth: 100, title: '床位数', align: "center"},
                {field: 'roomnum', minWidth: 100, title: '房间数', align: "center"},
                {field: 'avilablenum', minWidth: 100, title: '可用房间数', align: "center"},
                {field: 'reservednum', minWidth: 100, title: '已预订数', align: "center"},
                {field: 'livednum', minWidth: 100, title: '已入住数', align: "center"},
                {field: 'status', minWidth: 100, title: '状态', align: "center",templet:function (status) {
                        return status.status == 1 ? "可预订" : "<font color='red'>房型已满</font>";
                    }},
                {field: 'remark', minWidth: 100, title: '备注', align: "center"},
                {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
            ]],
            page: true,
        });


        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            tableInfos.reload({
                where: data.field,
                page: {
                    curr: 1
                }
            });
            return false;
        });


        /**
         * toolbar是头部工具栏事件
         *监听头部工具栏事件---添加工具栏
         * currentTableFilter是表格lay-filter过滤器的值
         */
        table.on("toolbar(currentTableFilter)",function (obj) {
            switch (obj.event) {
                //添加按钮-lay-event="add"
                case "add":
                    //打开添加窗口
                    openAddWindow();
                    break;
            }
        });

        /**
         * tool是行工具栏事件
         * 监听行工具栏事件---编辑工具栏
         */
        table.on("tool(currentTableFilter)",function (obj) {
            console.log(obj);
            switch (obj.event) {
                //编辑按钮-lay-event="edit"
                case "edit":
                    //打开编辑窗口
                    openEditorWindow(obj.data);
                    break;
            }
        });

        //初始化渲染文件上传组件
        upload.render({
            elem: ".thumbImg",//绑定元素
            url: '/admin/file/uploadFile',//文件上传地址
            acceptMime: 'image/*',//指定允许上传的文件类型，image/*只能显示---图片类
            field: 'file',//文件上传的字段值，等同于input标签的name属性值，该值必须与控制器中的方法参数名一致
            method: "post",//提交方式
            //文件上传成功后的回调函数
            done: function (res,index,upload) {
                //设置图片回显的路径
                $(".thumbImg").attr("src", res.data.src);
                $(".thumbBox").css("background", "#fff");
                //给隐藏域赋值
                $("#photo").val(res.imagePath);
            }
        })

        //url---提交地址
        var url;
        //打开窗口的索引
        var mainIndex;

        /**
         * 添加窗口function
         */
        function openAddWindow() {
            mainIndex = layer.open({
                type: 1,//打开类型
                title: "添加房型",//窗口标题
                area: ["800px", "500px"],//窗口的宽度800px，高度400px
                content: $("#addOrUpdateWindow"),//引用的内容窗口
                success: function () {
                    //清空表单数据
                    $("#dataFrm")[0].reset();
                    //添加的提交请求
                    url = "${pageContext.request.contextPath}/admin/roomType/addRoomType";
                    //重置默认图片
                    $(".thumbImg").attr("src", "/hotel/show/images/defaultimg.jpg");

                    $("#photo").val("images/defaultimg.jpg");
                }
            });
        }

        /**
         * 编辑窗口
         * @Param data 当前编辑行的数据
         */
        function openEditorWindow(data) {
            mainIndex = layer.open({
                type: 1,//打开类型
                title: "修改房型",//窗口标题
                area: ["800px", "500px"],//窗口的宽度800px，高度400px
                content: $("#addOrUpdateWindow"),//引用的内容窗口
                success: function () {
                    /**
                     * 获取表单的数据
                     * 参数1，jsp中的form表单中lay-filter的值
                     * 参数2，回显的数据
                     */
                    form.val("dataFrm",data);
                    //编辑的提交请求
                    url = "${pageContext.request.contextPath}/admin/roomType/editRoomType";
                    //图片回显
                    $(".thumbImg").attr("src","/hotel/show/"+data.photo);
                    $("#photo").val(data.photo);
                }
            });
        }

        //监听表单事件
        form.on("submit(doSubmit)",function (data) {
            $.post(url,data.field,function(result) {
                if(result.success){
                    //刷新数据表格
                    tableInfos.reload();
                    //关闭窗口
                    layer.close(mainIndex);
                }
                //接收controller传过来的json信息，并提示添加状态
                layer.msg(result.message);
            },"json");
            //禁止页面刷新
            return false;
        });

    });
</script>

</body>
</html>

