package zstu.utils.constants;

/**
 * User: Aning
 */
public interface AppConstants {

    // 后台用户操作类型
    interface OptTypeName{
        //String ADD = "add"; // 添加
        //String DATAAUTH = "dataAuth";//设置数据权限
        String DEL = "删除";// 删除
        //String DELETE = "delete";//
        //String MENUAUTH = "menuAuth";//设置菜单权限
        String MODIFY = "修改";// 修改
        //String PUBLISH = "publish";// 发布
        //String PUSH = "push";// 推送
        //String RESET = "reset";// 重置
        //String UPLOAD = "upload";// 上传
        String VIEW = "查询";// 查询
    }

    // 后台用户操作类型
    interface ApiMethod{
        String POST = "POST"; // 添加
        String DELETE = "DELETE";//
        String PUT = "PUT";// 修改
        String GET = "GET";// 查询
    }
}
