<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="社区编号" prop="commCode">
        <el-input
          v-model="queryParams.commCode"
          placeholder="请输入社区编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属区/县" prop="county">
        <el-input
          v-model="queryParams.county"
          placeholder="请输入所属区/县"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属街道/乡镇" prop="town">
        <el-input
          v-model="queryParams.town"
          placeholder="请输入所属街道/乡镇"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社区名" prop="commName">
        <el-input
          v-model="queryParams.commName"
          placeholder="请输入社区名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社区人数" prop="commNum">
        <el-input
          v-model="queryParams.commNum"
          placeholder="请输入社区人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社区状态" prop="commStatus">
        <el-select v-model="queryParams.commStatus"  clearable>
          <el-option
            v-for="dict in dict.type.sys_comm_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="社区封控状态" prop="commContStatus">
        <el-select v-model="queryParams.commContStatus"  clearable>
          <el-option
            v-for="dict in dict.type.sys_comm_cont_status"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:comm:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:comm:add']"
        >一键导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:comm:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:comm:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="社区编号" align="center" prop="commId" />
      <el-table-column label="社区编号" align="center" prop="commCode" />
      <el-table-column label="所属区/县" align="center" prop="county" />
      <el-table-column label="所属街道/乡镇" align="center" prop="town" />
      <el-table-column label="社区名" align="center" prop="commName" />
      <el-table-column label="社区人数" align="center" prop="commNum" />
      <el-table-column label="社区状态" align="center" prop="commStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_comm_status" :value="scope.row.commStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="社区封控状态" align="center" prop="commContStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_comm_cont_status" :value="scope.row.commContStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:comm:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:comm:remove']"
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

    <!-- 添加或修改社区管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="社区编号" prop="commCode">
          <el-input v-model="form.commCode" placeholder="请输入社区编号" />
        </el-form-item>
        <el-form-item label="所属区/县" prop="county">
          <el-input v-model="form.county" placeholder="请输入所属区/县" />
        </el-form-item>
        <el-form-item label="所属街道/乡镇" prop="town">
          <el-input v-model="form.town" placeholder="请输入所属街道/乡镇" />
        </el-form-item>
        <el-form-item label="社区名" prop="commName">
          <el-input v-model="form.commName" placeholder="请输入社区名" />
        </el-form-item>
        <el-form-item label="社区人数" prop="commNum">
          <el-input v-model="form.commNum" placeholder="请输入社区人数" />
        </el-form-item>
        <el-form-item label="社区状态" prop="commStatus">
          <el-select v-model="form.commStatus" >
            <el-option
              v-for="dict in dict.type.sys_comm_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="社区封控状态" prop="commContStatus">
          <el-select v-model="form.commContStatus" >
            <el-option
              v-for="dict in dict.type.sys_comm_cont_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listComm, getComm, delComm, addComm, updateComm, importComm} from "@/api/system/comm";

export default {
  name: "Comm",
  dicts: ['sys_comm_status', 'sys_comm_cont_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 社区管理表格数据
      commList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        commCode: null,
        county: null,
        town: null,
        commName: null,
        commNum: null,
        commStatus: null,
        commContStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        commCode: [
          { required: true, message: "社区编号，与地区表中地区编号相同不能为空", trigger: "blur" }
        ],
        county: [
          { required: true, message: "所属区/县不能为空", trigger: "blur" }
        ],
        town: [
          { required: true, message: "所属街道/乡镇不能为空", trigger: "blur" }
        ],
        commName: [
          { required: true, message: "社区名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询社区管理列表 */
    getList() {
      this.loading = true;
      listComm(this.queryParams).then(response => {
        this.commList = response.rows;
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
        commId: null,
        commCode: null,
        county: null,
        town: null,
        commName: null,
        commNum: null,
        commStatus: null,
        commContStatus: null
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
      this.ids = selection.map(item => item.commId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加社区管理";
    },
    handleImport(){
      this.$modal.confirm('是否将本区/县的所有社区信息导入到社区表中？').then(function() {
        return importComm();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const commId = row.commId || this.ids
      getComm(commId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改社区管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.commId != null) {
            updateComm(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addComm(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const commIds = row.commId || this.ids;
      this.$modal.confirm('是否确认删除社区管理编号为"' + commIds + '"的数据项？').then(function() {
        return delComm(commIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/comm/export', {
        ...this.queryParams
      }, `comm_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
