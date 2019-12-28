package com.lh.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.common.tree.TreeNode;
import com.lh.system.entity.SysDept;
import com.lh.system.model.vo.DepartIdModel;
import com.lh.system.model.vo.SysDeptTree;
import com.lh.system.model.vo.SysDeptVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 功能描述：
 *
 *  <p>版权所有：</p>
 *  未经本人许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: LionHerding
 * @Author xieyc
 * @Date 2019-09-20
 * @Version: 1.0.0
 *
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查出所有部门,并以树结构格式返回前端
     * @return tree
     */
    List<TreeNode> queryTreeList(String departName);

    /**
     * 查询子级部门
     * @param parentId 父级部门ID
     * @return deptList
     */
    Page<SysDept> childrenDept(Page<SysDeptVO> page, String parentId);

    /**
     * 部门搜索功能方法,根据关键字模糊搜索相关部门
     * @param keyWord
     * @return
     */
    List<SysDeptTree> searchBy(String keyWord);

    /**
     * 添加或编辑页面时对该方法发起请求,以树结构形式加载所有部门的名称
     * @return
     */
    List<DepartIdModel> queryDepartIdTreeList();

    /**
     * 根据部门Id修改
     * @param sysDept
     */
    void editByDeptId(SysDept sysDept);

    /**
     * 根据部门ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 部门批量删除
     * @param ids
     */
    void deleteBatch(String ids);

    /**
     * 创建部门
     * @param sysDept
     * @param request
     */
    void create(SysDept sysDept, HttpServletRequest request);
}
