<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/lib/layui-v2.5.5/css/layui.css"   media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/css/layuimini.css?v=2.0.4.2" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/css/themes/default.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui/css/public.css" media="all">
    <%-- 添加layui-dtree样式 --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/layui_ext/dtree/font/dtreefont.css">
</head>
<body>
    <div class="layuimini-container">
    <div class="layuimini-main">
        <div class="layui-row">
            <%-- 左侧菜单树 --%>
            <div class="layui-col-md2">
                <!-- 树节点容器开始 -->
                <ul id="menuTree" class="dtree" data-id="0" style="width: 240px;"></ul>
                <!-- 树节点容器结束 -->
            </div>

            <%-- 右侧数据表格 --%>
            <div class="layui-col-md10">

                <%-- 表格工具栏 --%>
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>添加 </button>
                    </div>
                </script>

                <%-- 数据表格 --%>
                <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

                <%-- 行工具栏 --%>
                <script type="text/html" id="currentTableBar">
                    <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                    <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete"><i class="layui-icon layui-icon-close"></i>删除</a>
                </script>

                <%-- 添加和修改窗口 --%>
                <div style="display: none;padding: 5px" id="addOrUpdateWindow">
                    <form class="layui-form" style="width:90%;" id="dataFrm" lay-filter="dataFrm">
                        <%-- 菜单编号 --%>
                        <input type="hidden" name="id">
                        <div class="layui-form-item">
                            <label class="layui-form-label">父级菜单</label>
                            <div class="layui-input-block">
                                <input type="hidden" name="pid" id="pid">
                                <ul id="menuSelectTree" class="dtree" data-id="0"></ul>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">菜单名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" lay-verify="required" autocomplete="off" placeholder="请输入菜单名称" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">菜单地址</label>
                            <div class="layui-input-block">
                                <input type="text" name="href"  autocomplete="off" placeholder="请输入菜单地址" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">菜单图标</label>
                            <div class="layui-input-block">
                                <%-- 添加隐藏域 --%>
                                <input type="hidden" name="icon" id="icon">
                                <input type="text" name="icon" id="iconPicker" lay-filter="iconPicker" autocomplete="off" placeholder="请输入菜单图标" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">是否展开</label>
                                <div class="layui-input-block">
                                    <input type="radio" name="spread" value="1" title="是">
                                    <input type="radio" name="spread" value="0" title="否" checked>
                                </div>
                            </div>
                        </div>

                        <div class="layui-form-item layui-row layui-col-xs12">
                            <div class="layui-input-block" style="text-align: center;">
                                <button type="button" class="layui-btn" lay-submit lay-filter="doSubmit"><span class="layui-icon layui-icon-add-1"></span>提交</button>
                                <button type="reset" class="layui-btn layui-btn-warm"><span class="layui-icon layui-icon-refresh-1"></span>清空</button>
                                <button type="button" class="layui-btn layui-btn-danger" id="resetMenu"><span class="layui-icon layui-icon-return"></span>重置菜单</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

<%-- 导入layui的js文件--%>
<script src="${pageContext.request.contextPath}/statics/layui/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>

    <script>
        layui.extend({
            dtree: "${pageContext.request.contextPath}/statics/layui_ext/dtree/dtree",
            iconPickerFa : '${pageContext.request.contextPath}/statics/layui/js/lay-module/iconPicker/iconPickerFa'
        }).use(['form','jquery','table','layer','dtree','iconPickerFa'],function() {
            var form = layui.form;
            var $ = layui.jquery;
            var table = layui.table;
            var layer = layui.layer;
            var dtree = layui.dtree;
            var iconPickerFa = layui.iconPickerFa;

            //渲染左侧菜单树
            var menuTree = dtree.render({
                 elem: "#menuTree",//绑定ul标签的ID属性值
                 url: "/admin/menu/loadMenuTreeLeft", //请求地址
                 dataStyle: "layuiStyle",  //使用layui风格的数据格式
                 dataFormat: "list",  //配置data的风格为list
                 response:{message:"msg",statusCode:0},  //修改response中返回数据的定义
            });


            //监听dtree树节点事件
            dtree.on("node('menuTree')",function(obj) {
                //nodeId是当前选中节点Id
                tableInfos.reload({
                     where:{"id":obj.param.nodeId},
                     page:{
                       curr:1
                       }
                   });
             });


             /**
             * toolbar是头部工具栏事件
             * 监听头部工具栏事件---添加工具栏
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
                    switch (obj.event) {
                    //编辑按钮-lay-event="edit"
                    case "edit":
                    //打开编辑窗口
                    openEditorWindow(obj.data);
                    break;
                    case "delete":
                    //删除事件
                    deleteById(obj.data);
                    break;
                    }
            });

                //下拉菜单
                var menuSelectTree = dtree.renderSelect({
                elem: "#menuSelectTree",//绑定ul标签的ID属性值
                url: "/admin/menu/loadMenuTreeLeft", //请求地址
                dataStyle: "layuiStyle",  //使用layui风格的数据格式
                dataFormat: "list",  //配置data的风格为list
                response:{message:"msg",statusCode:0},  //修改response中返回数据的定义
                });

                //监听下拉菜单选中事件
                dtree.on("node('menuSelectTree')",function(obj) {
                //给隐藏域赋值
                $("#pid").val(obj.param.nodeId);
                });

             var url;
             var mainIndex;

             /**
             * 打开添加窗口
             */
             function openAddWindow() {
                 mainIndex = layer.open({
                 type: 1,//打开类型
                 title: "添加菜单",//窗口标题
                 area: ["800px", "500px"],//窗口的宽度800px，高度400px
                 content: $("#addOrUpdateWindow"),//引用的内容窗口
                 success: function () {
                                //清空表单数据
                               $("#dataFrm")[0].reset();
                               //添加的提交请求
                              url = "${pageContext.request.contextPath}/admin/menu/addMenu";
                              //设置默认图标
                              iconPickerFa.checkIcon('iconPicker', 'fa fa-star');
                      }
                 });
             }

            /**
            * 打开编辑窗口
            * @Param data 当前编辑行的数据
            */
            function openEditorWindow(data) {
                    mainIndex = layer.open({
                    type: 1,//打开类型
                    title: "编辑菜单",//窗口标题
                    area: ["800px", "400px"],//窗口的宽度800px，高度400px
                    content: $("#addOrUpdateWindow"),//引用的内容窗口
                    success: function () {
                    form.val("dataFrm",data);
                    //编辑的提交请求
                    url = "${pageContext.request.contextPath}/admin/menu/editMenu";
                    //图标回显
                    iconPickerFa.checkIcon('iconPicker',data.icon);
                    //父级菜单回显
                    dtree.dataInit("menuSelectTree",data.pid);//参数1:下拉菜单的id属性值，参数2:父节点ID值
                    dtree.selectVal("menuSelectTree");//下拉菜单树的id属性值
                    if(data.pid == 0){
                    menuSelectTree.reload();
                    }
                    }
                    });
                    }

            /**
            * 删除菜单
            * @param data
            */
            function deleteById(data) {
                $.get("/admin/menu/checkMenuHasChild",{"id":data.id},function(result) {
                if(result.exist){
                    layer.msg(result.message);
                }else {
                //提示用户确认删除该菜单
                layer.confirm("确定删除["+data.title+"]?",{icon: 3, title:'提示'}, function (index) {
                //发送ajax请求进行删除
                $.post("/admin/menu/deleteById",{"id":data.id},function (result) {
                if(result.success){
                //刷新数据表格
                tableInfos.reload();
                //刷新左侧菜单树
                menuTree.reload();
                //刷新下拉菜单树
                menuSelectTree.reload();
                }
                //提示信息
                layer.msg(result.message);
                },"json");
                layer.close(index);
                });
                }
                },"json")

                        }
                //初始化图标选择器组件
                iconPickerFa.render({
                // 选择器，推荐使用input
                elem: '#iconPicker',
                // fa 图标接口
                url: "/statics/layui/lib/font-awesome-4.7.0/less/variables.less",
                // 是否开启搜索：true/false，默认true
                search: true,
                // 是否开启分页：true/false，默认true
                page: true,
                // 每页显示数量，默认12
                limit: 12,
                // 点击回调
                click: function (data) {
                //给图标隐藏域赋值
                $("#icon").val("fa "+data.icon);
                },
                // 渲染成功后的回调
                success: function (d) {
                console.log(d);
                }
                });

                //监听表单提交事件
                form.on("submit(doSubmit)",function (data) {
                $.post(url,data.field,function(result){
                if(result.success){
                //刷新数据表格
                tableInfos.reload();
                //关闭窗口
                layer.close(mainIndex);
                //刷新左侧菜单树
                menuTree.reload();
                //刷新下拉菜单树
                menuSelectTree.reload();
                }
                //提示信息
                layer.msg(result.message);
                },"json");
                //禁止页面刷新
                return false;
                });

             var tableInfos = table.render({
             elem: '#currentTableId',
             url: "${pageContext.request.contextPath}/admin/menu/list",
             toolbar: '#toolbarDemo',
             cols: [[
                  {field: 'id', width: 100, title: "菜单编号",align: 'center'},
                  {field: 'title', minWidth: 180, title: '部门名称',align: 'center'},
                  {field: 'href', minWidth: 200, title: '菜单地址',align: 'center'},
                  {field: 'spread', minWidth: 100, title: '是否展开',align: 'center',templet:function(spread) {
                      return spread.spread == 1 ? "<font color='green'>是</font>" : "<font color='red'>否</font>"
                  }},
                  {field: 'icon', minWidth: 100, title: '菜单图标',align: 'center',templet:function (icon) {
                      return "<i class='"+icon.icon+"'></i>"
                  }},
                  {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
             ]],
             page: true,
        });
    });
    </script>
</html>
