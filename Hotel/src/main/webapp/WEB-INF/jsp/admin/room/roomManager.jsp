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
                            <label class="layui-form-label">房间编号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="roomnum" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">房间类型</label>
                            <div class="layui-input-inline">
                                <select name="roomtypeid" id="s_roomTypeId" autocomplete="off" class="layui-input">
                                    <option value="">全部</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">所属楼层</label>
                            <div class="layui-input-inline">
                                <select name="floorid" id="s_floorId" autocomplete="off" class="layui-input">
                                    <option value="">全部</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">房间状态</label>
                            <div class="layui-input-inline">
                                <select name="status" id="s_status" autocomplete="off" class="layui-input">
                                    <option value="">全部</option>
                                    <option value="1">已预订</option>
                                    <option value="2">已入住</option>
                                    <option value="3">可预订</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block" style="text-align: center">
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
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete"><i
                    class="layui-icon layui-icon-close"></i>删除</a>
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
                                <label class="layui-form-label">房间编号</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="roomnum" lay-verify="required"  placeholder="请输入房间编号">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">房间类型</label>
                                <div class="layui-input-block">
                                    <select name="roomtypeid" id="roomtypeid" lay-verify="required">
                                        <option value="">请选择房型</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">房间备注</label>
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
                            <label class="layui-form-label">所属楼层</label>
                            <div class="layui-input-inline">
                                <select name="floorid" id="floorid" lay-verify="required">
                                    <option value="">请选择楼层</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">房间状态</label>
                            <div class="layui-input-inline">
                                <select name="status" id="status" lay-verify="required">
                                    <option value="">请选择房间状态</option>
                                    <option value="1">已预订</option>
                                    <option value="2">已入住</option>
                                    <option value="3">可预订</option>
                                </select>
                            </div>
                        </div>

                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">房间要求</label>
                        <div class="layui-input-block" >
                            <textarea id="roomrequirement" name="roomrequirement" class="layui-textarea"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">房间详情</label>
                        <div class="layui-input-block" >
                            <textarea id="roomdesc" name="roomdesc" style="display: none;"></textarea>
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
    layui.use(['form', 'table', 'laydate', 'jquery', 'layer','upload','layedit'], function () {
        var $ = layui.jquery,
            form = layui.form,
            laydate = layui.laydate,
            upload = layui.upload,
            layedit = layui.layedit,
            layer = layui.layer,
            table = layui.table;

        //渲染表格组件
        var tableInfos = table.render({
            elem: '#currentTableId',
            url: '${pageContext.request.contextPath}/admin/room/list',
            toolbar: '#toolbarDemo',
            cols: [[
                {field: 'id', width: 60, title: '编号', align: "center"},
                {field: 'roomnum', minWidth: 120, title: '房间编号', align: "center"},
                {field: 'typeName', minWidth: 100, title: '房间类型', align: "center"},
                {field: 'floorName', minWidth: 100, title: '所属楼层', align: "center"},
                {field: 'statusStr', minWidth: 100, title: '房间状态', align: "center"},
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

        //查询房型下拉列表
        $.get("/admin/roomType/findAll",function (result) {
            var html = "";
            for (var i = 0; i < result.length; i++){
                html += "<option value='"+result[i].id+"'>"+result[i].typename+"</option>"
            }
            //将网页代码追加到下拉列表中
            $("[name='roomtypeid']").append(html);
            //重新渲染下拉列表
            form.render("select");
        },"json");

        //查询楼层下拉列表
        $.get("/admin/floor/findAll",function (result) {
            var html = "";
            for (var i = 0; i < result.length; i++){
                html += "<option value='"+result[i].id+"'>"+result[i].name+"</option>"
            }
            //将网页代码追加到下拉列表中
            $("[name='floorid']").append(html);
            //重新渲染下拉列表
            form.render("select");
        },"json");

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
                case "delete"://删除按钮
                    deleteById(obj.data);//删除
                    break;
            }
        });


        //url---提交地址
        var url;
        //打开窗口的索引
        var mainIndex;
        //副文本编辑器的索引
        var detailIndex;

        /**
         * 添加窗口function
         */
        function openAddWindow() {
            mainIndex = layer.open({
                type: 1,//打开类型
                title: "添加房间",//窗口标题
                area: ["800px", "500px"],//窗口的宽度800px，高度400px
                content: $("#addOrUpdateWindow"),//引用的内容窗口
                maxmin:true,
                success: function () {
                    //清空表单数据
                    $("#dataFrm")[0].reset();
                    //添加的提交请求
                    url = "${pageContext.request.contextPath}/admin/room/addRoom";
                    //重置默认图片
                    $(".thumbImg").attr("src", "/hotel/show/images/defaultimg.jpg");

                    $("#photo").val("images/defaultimg.jpg");
                }
            });
            //设置窗口最大化显示
            layer.full(mainIndex);
            //初始化副文本编辑器
            detailIndex = layedit.build("roomdesc",{
                uploadImage: {
                    url: "/admin/file/uploadFile",
                    type: "post"
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
                title: "修改房间",//窗口标题
                area: ["800px", "500px"],//窗口的宽度800px，高度400px
                content: $("#addOrUpdateWindow"),//引用的内容窗口
                maxmin:true,
                success: function () {
                    /**
                     * 获取表单的数据
                     * 参数1，jsp中的form表单中lay-filter的值
                     * 参数2，回显的数据
                     */
                    form.val("dataFrm",data);
                    //编辑的提交请求
                    url = "${pageContext.request.contextPath}/admin/room/editRoom";
                    //图片回显
                    $(".thumbImg").attr("src","/hotel/show/"+data.photo);
                    $("#photo").val(data.photo);
                }
            });
            //设置窗口最大化显示
            layer.full(mainIndex);
            //初始化副文本编辑器
            detailIndex = layedit.build("roomdesc",{
                uploadImage: {
                    url: "/admin/file/uploadFile",
                    type: "post"
                }
            });
        }

        /**
         * 删除事件
         * @param data
         */
        function deleteById(data){
            //先判断是否能够删除,为可预订方可删除
            if(data.status == 3){
                //提示是否删除
                layer.confirm("确定删除["+data.roomnum+"]房间?", {icon: 3, title:'提示'}, function(index){
                    //发送ajax请求进行删除
                    $.post("/admin/room/deleteById",{"id":data.id},function(result){
                        if(result.success){
                            //刷新数据表格
                            tableInfos.reload();
                        }
                        //提示
                        layer.msg(result.message);
                    },"json");

                    layer.close(index);
                });
            }else {
                layer.msg("该状态不可删除");
            }
        }

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

        //监听表单事件
        form.on("submit(doSubmit)",function (data) {
            //将副文本内容同步到文本域中---.serialize()一次性获取表单数据
            layedit.sync(detailIndex);
            $.post(url,$("#dataFrm").serialize(),function(result) {
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
