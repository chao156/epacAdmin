<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请物资类别" prop="itemApplyType">
        <el-select v-model="queryParams.itemApplyType" placeholder="请选择申请物资类别" clearable>
          <el-option
            v-for="dict in dict.type.supplie_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="申请物资名称" prop="itemApplyName">
        <el-input
          v-model="queryParams.itemApplyName"
          placeholder="请输入申请物资名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请物资单位" prop="itemApplyUnit">
        <el-input
          v-model="queryParams.itemApplyUnit"
          placeholder="请输入申请物资单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请物资数量" prop="itemApplyAmount">
        <el-input
          v-model="queryParams.itemApplyAmount"
          placeholder="请输入申请物资数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="详细地址" prop="itemApplyLocal">
        <el-input
          v-model="queryParams.itemApplyLocal"
          placeholder="请输入详细地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物资申请时间" prop="itemApplyTime">
        <el-date-picker clearable
          v-model="queryParams.itemApplyTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择物资申请时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态修改时间" prop="itemApplyStatusTime">
        <el-date-picker clearable
          v-model="queryParams.itemApplyStatusTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择状态修改时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="驳回原因" prop="itemApplyRemark">
        <el-input
          v-model="queryParams.itemApplyRemark"
          placeholder="请输入驳回原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在街道" prop="itemApplyTown">
        <el-input
          v-model="queryParams.itemApplyTown"
          placeholder="请输入所在街道"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在社区" prop="itemApplyComm">
        <el-input
          v-model="queryParams.itemApplyComm"
          placeholder="请输入所在社区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleItemApply"
          v-hasPermi="['system:approval:edit']"
        >审核通过</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="RejectItem"
          v-hasPermi="['system:approval:edit']"
        >审核驳回</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:approval:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请人姓名" align="center" prop="userName" />
      <el-table-column label="申请物资类别" align="center" prop="itemApplyType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.supplie_type" :value="scope.row.itemApplyType"/>
        </template>
      </el-table-column>
      <el-table-column label="申请物资名称" align="center" prop="itemApplyName" />
      <el-table-column label="申请物资单位" align="center" prop="itemApplyUnit" />
      <el-table-column label="申请物资数量" align="center" prop="itemApplyAmount" />
      <el-table-column label="详细地址" align="center" prop="itemApplyLocal" />
      <el-table-column label="物资申请时间" align="center" prop="itemApplyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.itemApplyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前申请状态" align="center" prop="itemApplyStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.apply_status" :value="scope.row.itemApplyStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="状态修改时间" align="center" prop="itemApplyStatusTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.itemApplyStatusTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="驳回原因" align="center" prop="itemApplyRemark" />
      <el-table-column label="所在街道" align="center" prop="itemApplyTown" />
      <el-table-column label="所在社区" align="center" prop="itemApplyComm" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:apply:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改物资审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listApply, getApply, delApply, addApply, updateApply, handleItemApply, RejectItem} from "@/api/system/apply";
import {handleApply, Reject} from "@/api/system/approval";

export default {
  name: "Apply",
  dicts: ['supplie_type', 'apply_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      userNames:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物资审核表格数据
      applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemApplyType: null,
        itemApplyName: null,
        itemApplyUnit: null,
        itemApplyAmount: null,
        itemApplyLocal: null,
        itemApplyTime: null,
        itemApplyStatusTime: null,
        itemApplyRemark: null,
        itemApplyTown: null,
        itemApplyComm: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物资审核列表 */
    getList() {
      this.loading = true;
      listApply(this.queryParams).then(response => {
        this.applyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        itemApplyId: null,
        itemApplyType: "0",
        itemApplyName: null,
        itemApplyUnit: null,
        itemApplyAmount: null,
        itemApplyLocal: null,
        itemApplyTime: null,
        itemApplyStatus: "0",
        itemApplyStatusTime: null,
        itemApplyRemark: null,
        itemApplyTown: null,
        itemApplyComm: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.itemApplyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 审核通过按钮操作 */
    handleItemApply(row) {
      const itemApplyIds = row.itemApplyId || this.ids;
      const userName = row.userName || this.userNames ;
      this.$modal.confirm('是否审核通过姓名为"' + userName + '"的物资申请？').then(function() {
        return handleItemApply(itemApplyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("审核通过成功");
      }).catch(() => {});
    },
    RejectItem(row) {
      const itemApplyIds = row.itemApplyId || this.ids;
      const userName = row.userName || this.userNames ;
      this.$modal.confirm('是否驳回姓名为"' + userName + '"物资申请？').then(function() {
        return RejectItem(itemApplyIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("审核驳回成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/approval/export', {
        ...this.queryParams
      }, `approval_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
