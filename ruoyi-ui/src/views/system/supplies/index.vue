<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物资类别" prop="itemType">
        <el-select v-model="queryParams.itemType" placeholder="请选择物资类别" clearable>
          <el-option
            v-for="dict in dict.type.supplie_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物资名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入物资名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="存放地址" prop="storagePlace">
        <el-input
          v-model="queryParams.storagePlace"
          placeholder="请输入存放地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库时间" prop="warehousingTime">
        <el-date-picker clearable
                        v-model="queryParams.warehousingTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择入库时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="出库时间" prop="deliveryTime">
        <el-date-picker clearable
                        v-model="queryParams.deliveryTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择出库时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="来源" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入来源"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出库去向" prop="deliveryDestination">
        <el-input
          v-model="queryParams.deliveryDestination"
          placeholder="请输入出库去向"
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
          v-hasPermi="['system:supplies:add']"
        >物资入库
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:supplies:edit']"
        >物资出库
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:supplies:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:supplies:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="suppliesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="物资名称" align="center" prop="itemName"/>
      <el-table-column label="物资类别" align="center" prop="itemType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.supplie_type" :value="scope.row.itemType"/>
        </template>
      </el-table-column>
      <el-table-column label="物资数量" align="center" prop="itemAmount"/>
      <el-table-column label="物资单位" align="center" prop="itemUnit"/>
      <el-table-column label="存放地址" align="center" prop="storagePlace"/>
      <el-table-column label="入库时间" align="center" prop="warehousingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warehousingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库时间" align="center" prop="deliveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="来源" align="center" prop="source"/>
      <el-table-column label="出库去向" align="center" prop="deliveryDestination"/>
      <el-table-column label="物品状态" align="center" prop="status"/>
      <el-table-column label="经度" align="center" prop="longitude"/>
      <el-table-column label="维度" align="center" prop="latitude"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:supplies:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:supplies:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改物资管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="物资名称" prop="itemName" required>
              <el-input v-model="form.itemName" placeholder="请输入物资名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="物资类别" prop="itemType" required>
              <el-select v-model="form.itemType" placeholder="请选择物资类别" >
                <el-option
                  v-for="dict in dict.type.supplie_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="物资数量" prop="itemAmount" required>
              <el-input v-model="form.itemAmount" placeholder="请输入物资数量" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="物资单位" prop="itemUnit" required>
              <el-input v-model="form.itemUnit" placeholder="请输入物资单位" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="存放地址" prop="storagePlace" required>
              <el-input v-model="form.storagePlace" placeholder="请输入存放地址" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="入库时间" prop="warehousingTime" >
              <el-date-picker clearable
                              v-model="form.warehousingTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择入库时间"
                              required>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出库时间" prop="deliveryTime">
              <el-date-picker clearable
                              v-model="form.deliveryTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择出库时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="来源" prop="source" required>
              <el-input v-model="form.source" placeholder="请输入来源" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出库去向" prop="deliveryDestination">
              <el-input v-model="form.deliveryDestination" placeholder="请输入出库去向"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="经度" prop="longitude" required>
              <el-input v-model="form.longitude" placeholder="请点击下方地图，自动获取经度"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="维度" prop="latitude" required>
              <el-input v-model="form.latitude" placeholder="请点击下方地图，自动获取纬度"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注"/>
          </el-form-item>
        </el-row>
        <el-row>
          <baidu-map :center="center" :zoom="zoom" :position="{lng: 116.404, lat: 39.915}" @ready="handler"
                     :scroll-wheel-zoom="true" class="bm-view">
            <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
            <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true"
                            :autoLocation="true"></bm-geolocation>
          </baidu-map>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listSupplies, getSupplies, delSupplies, addSupplies, updateSupplies} from "@/api/system/supplies";

export default {
  name: "Supplies",
  dicts: ['supplie_type'],
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
      // 物资管理表格数据
      suppliesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemType: null,
        itemName: null,
        storagePlace: null,
        warehousingTime: null,
        deliveryTime: null,
        source: null,
        deliveryDestination: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      center: '长清区',
      zoom: 17,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handler({BMap, map}) {
      const that = this
      //监听时间，点击获取坐标
      map.addEventListener('click',function(e){
        that.form.longitude = e.point.lng
        that.form.latitude = e.point.lat
      });
    },
    /** 查询物资管理列表 */
    getList() {
      this.loading = true;
      listSupplies(this.queryParams).then(response => {
        this.suppliesList = response.rows;
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
        itemId: null,
        itemType: null,
        itemName: null,
        itemUnit: null,
        itemAmount: null,
        storagePlace: null,
        warehousingTime: null,
        deliveryTime: null,
        source: null,
        deliveryDestination: null,
        status: "0",
        remark: null,
        longitude: null,
        latitude: null
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
      this.ids = selection.map(item => item.itemId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "物资入库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const itemId = row.itemId || this.ids
      getSupplies(itemId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "物资出库";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.itemId != null) {
            updateSupplies(this.form).then(response => {
              this.$modal.msgSuccess("出库成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSupplies(this.form).then(response => {
              this.$modal.msgSuccess("入库成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const itemIds = row.itemId || this.ids;
      this.$modal.confirm('是否确认删除物资管理编号为"' + itemIds + '"的数据项？').then(function () {
        return delSupplies(itemIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/supplies/export', {
        ...this.queryParams
      }, `supplies_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
