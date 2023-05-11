<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在社区" prop="community">
        <el-input
          v-model="queryParams.community"
          placeholder="请输入所在社区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外出原因" prop="outReason">
        <el-input
          v-model="queryParams.outReason"
          placeholder="请输入外出原因"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外出去向" prop="outDestination">
        <el-input
          v-model="queryParams.outDestination"
          placeholder="请输入外出去向"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外出时间" prop="outTime">
        <el-date-picker clearable
          v-model="queryParams.outTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择外出时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="返回时间" prop="returnTime">
        <el-date-picker clearable
          v-model="queryParams.returnTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择返回时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="凭证" prop="certificate">
        <el-input
          v-model="queryParams.certificate"
          placeholder="请输入凭证"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外出类型" prop="outType">
        <el-select v-model="queryParams.outType" placeholder="请选择外出类型" clearable>
          <el-option
            v-for="dict in dict.type.out_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          @click="handleApply"
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
          @click="handleReject"
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

    <el-table v-loading="loading" :data="approvalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="userName" />
      <el-table-column label="所在社区" align="center" prop="community" />
      <el-table-column label="外出原因" align="center" prop="outReason" />
      <el-table-column label="外出去向" align="center" prop="outDestination" />
      <el-table-column label="外出时间" align="center" prop="outTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="返回时间" align="center" prop="returnTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="凭证" align="center" prop="certificate" />
      <el-table-column label="外出类型" align="center" prop="outType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.out_type" :value="scope.row.outType"/>
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

  </div>
</template>

<script>
import {listApproval, handleApply, Reject} from "@/api/system/approval";

export default {
  name: "Approval",
  dicts: ['out_type'],
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
      // 外出审核表格数据
      approvalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        community: null,
        outReason: null,
        outDestination: null,
        outTime: null,
        returnTime: null,
        status: null,
        certificate: null,
        outType: null
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
    /** 查询外出审核列表 */
    getList() {
      this.loading = true;
      listApproval(this.queryParams).then(response => {
        this.approvalList = response.rows;
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
        outId: null,
        userId: null,
        userName: null,
        community: null,
        outReason: null,
        outDestination: null,
        outTime: null,
        returnTime: null,
        status: "0",
        remark: null,
        certificate: null,
        outType: null
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
      this.ids = selection.map(item => item.outId)
      this.userNames = selection.map(item => item.userName)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 审核通过按钮操作 */
    handleApply(row) {
      const outName = row.userName || this.userNames ;
      this.$modal.confirm('是否审核通过姓名为"' + outName + '"的外出申请？').then(function() {
        return handleApply(outIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("审核通过成功");
      }).catch(() => {});
    },
    handleReject(row) {
      const outIds = row.outId || this.ids;
      const outName = row.userName || this.userNames ;
      this.$modal.confirm('是否驳回姓名为"' + outName + '"外出申请？').then(function() {
        return Reject(outIds);
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
