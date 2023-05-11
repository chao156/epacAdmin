<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="地区编号" prop="regionCode">
        <el-input
          v-model="queryParams.regionCode"
          placeholder="请输入地区编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地区父编号" prop="regionParentCode">
        <el-input
          v-model="queryParams.regionParentCode"
          placeholder="请输入地区父编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地区名称" prop="regionName">
        <el-input
          v-model="queryParams.regionName"
          placeholder="请输入地区名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地区等级" prop="regionLevel">
        <el-input
          v-model="queryParams.regionLevel"
          placeholder="请输入地区等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地区领导人" prop="regionLeader">
        <el-input
          v-model="queryParams.regionLeader"
          placeholder="请输入地区领导人"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:region:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:region:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="regionList"
      row-key="regionCode"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="地区名称" align="center" prop="regionName" />
      <el-table-column label="地区编号" prop="regionCode" />
      <el-table-column label="地区父编号" align="center" prop="regionParentCode" />
      <el-table-column label="地区等级" align="center" prop="regionLevel">
<!--        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_region_level" :value="scope.row.regionLevel"/>
        </template>-->
      </el-table-column>
      <el-table-column label="地区领导人" align="center" prop="regionLeader" />
      <el-table-column label="状态" align="center" prop="regionStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:region:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:region:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:region:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改地区管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="地区编号" prop="regionCode">
          <el-input v-model="form.regionCode" placeholder="请输入地区编号" />
        </el-form-item>
        <el-form-item label="地区父编号" prop="regionParentCode">
          <treeselect v-model="form.regionParentCode" :options="regionOptions" :normalizer="normalizer" placeholder="请选择地区父编号" />
        </el-form-item>
        <el-form-item label="地区名称" prop="regionName">
          <el-input v-model="form.regionName" placeholder="请输入地区名称" />
        </el-form-item>
        <el-form-item label="地区等级" prop="regionLevel">
          <el-input v-model="form.regionLevel" placeholder="请输入地区等级" />
        </el-form-item>
        <el-form-item label="地区领导人" prop="regionLeader">
          <el-input v-model="form.regionLeader" placeholder="请输入地区领导人" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 地区导入对话框 -->
    <el-dialog :title="regionUpload.title" :visible.sync="regionUpload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="regionUpload.headers"
        :action="regionUpload.url + '?updateSupport=' + regionUpload.updateSupport"
        :disabled="regionUpload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="regionUpload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="regionUpload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRegion, getRegion, delRegion, addRegion, updateRegion } from "@/api/system/region";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getToken} from "@/utils/auth";

export default {
  name: "Region",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 地区管理表格数据
      regionList: [],
      // 地区管理树选项
      regionOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        regionCode: null,
        regionParentCode: null,
        regionName: null,
        regionLevel: null,
        regionLeader: null,
        regionStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        regionCode: [
          { required: true, message: "地区编号不能为空", trigger: "blur" }
        ],
        regionParentCode: [
          { required: true, message: "地区父编号不能为空", trigger: "blur" }
        ],
        regionName: [
          { required: true, message: "地区名称不能为空", trigger: "blur" }
        ],
        regionLevel: [
          { required: true, message: "地区等级不能为空", trigger: "blur" }
        ],
        regionLeader: [
          { required: true, message: "地区领导人不能为空", trigger: "blur" }
        ],
      },
      // 地区导入参数
      regionUpload: {
        // 是否显示弹出层（地区导入）
        open: false,
        // 弹出层标题（地区导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的地区数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/region/importData"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询地区管理列表 */
    getList() {
      this.loading = true;
      listRegion(this.queryParams).then(response => {
        this.regionList = this.handleTree(response.data, "regionCode", "regionParentCode");
        this.loading = false;
      });
    },
    /** 转换地区管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.regionCode,
        label: node.regionName,
        children: node.children
      };
    },
	/** 查询地区管理下拉树结构 */
    getTreeselect() {
      listRegion().then(response => {
        this.regionOptions = [];
        const data = { regionCode: 0, regionName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "regionCode", "regionParentCode");
        this.regionOptions.push(data);
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
        regionId: null,
        regionCode: null,
        regionParentCode: null,
        regionName: null,
        regionLevel: null,
        regionLeader: null,
        regionStatus: "0"

      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.regionCode) {
        this.form.regionParentCode = row.regionCode;
      } else {
        this.form.regionParentCode = 0;
      }
      this.open = true;
      this.title = "添加地区管理";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.regionParentCode = row.regionCode;
      }
      getRegion(row.regionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改地区管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.regionId != null) {
            updateRegion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegion(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleImport() {
      this.regionUpload.title = "地区导入";
      this.regionUpload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/region/importTemplate', {
      }, `region_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.regionUpload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.regionUpload.open = false;
      this.regionUpload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除地区管理编号为"' + row.regionId + '"的数据项？').then(function() {
        return delRegion(row.regionId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
