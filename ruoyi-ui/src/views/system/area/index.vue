<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="街道/镇" prop="town">
        <el-input
          v-model="queryParams.town"
          placeholder="请输入街道/镇"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社区/村" prop="community">
        <el-input
          v-model="queryParams.community"
          placeholder="请输入社区/村"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="具体位置" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入具体位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测开始时间" prop="ncStartTime">
        <el-date-picker clearable
                        v-model="queryParams.ncStartTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择检测开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="检测结束时间" prop="ncEndTime">
        <el-date-picker clearable
                        v-model="queryParams.ncEndTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择检测结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="核酸点状态" prop="ncStatus">
        <el-select v-model="queryParams.ncStatus" placeholder="请选择核酸点状态" clearable>
          <el-option
            v-for="dict in dict.type.nc_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:area:add']"
        >新增
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
          v-hasPermi="['system:area:edit']"
        >修改
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
          v-hasPermi="['system:area:remove']"
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
          v-hasPermi="['system:area:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="openQRCode"
        >生成二维码
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="areaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="区/县" align="center" prop="county"/>
      <el-table-column label="街道/镇" align="center" prop="town"/>
      <el-table-column label="社区/村" align="center" prop="community"/>
      <el-table-column label="具体位置" align="center" prop="address"/>
      <el-table-column label="经度" align="center" prop="logitude"/>
      <el-table-column label="纬度" align="center" prop="latitude"/>
      <el-table-column label="检测开始时间" align="center" prop="ncStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ncStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测结束时间" align="center" prop="ncEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ncEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="核酸点状态" align="center" prop="ncStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.nc_status" :value="scope.row.ncStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="排队人数" align="center" prop="queueNumber"/>
      <el-table-column label="创建人" align="center" prop="creator"/>
      <el-table-column label="修改时间" align="center" prop="modifyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.modifyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="modifier"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:area:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:area:remove']"
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
    <!-- 生成二维码 -->
    <el-dialog :title="QrCodetitle" :visible.sync="QrCodeOpen" width="500px" heigth="200px" append-to-body>
      <div>
        <el-row>
        <el-select v-model="collectionList.nca_id" size="medium" style="width:350px;" placeholder="请选择"
                   @change="chooseValue()">
          <el-option
            v-for="item in collectionList"
            :key="item.nca_id"
            :label="item.nca_name"
            :value="item.nca_id"
          >
          </el-option>
        </el-select>
        </el-row>
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            size="mini"
            icon="el-icon-plus"
            @click="obtainQrCode()"
          >生成二维码
          </el-button>
        </el-col>
      </div>
    </el-dialog>
    <!--展示二维码-->
    <el-dialog :title="CodeTitle" :visible.sync="CodeOpen" width="600px" append-to-body>
      <div class="QrCode-image">
        <div>
          <el-image
            style="width: 500px; height: 500px"
            :src="qrCodeUrl"
          ></el-image>
        </div>
      </div>
    </el-dialog>
    <!-- 添加或修改核酸点管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="区/县" prop="county">
              <el-input v-model="form.county" placeholder="请输入区/县"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="街道/镇" prop="town">
              <el-input v-model="form.town" placeholder="请输入街道/镇"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="社区/村" prop="community">
              <el-input v-model="form.community" placeholder="请输入社区/村"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="具体位置" prop="address">
              <el-input v-model="form.address" placeholder="请输入具体位置"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经度" prop="logitude">
              <el-input v-model="form.logitude" ref="logInput" placeholder="请输入经度"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="纬度" prop="latitude">
              <el-input v-model="form.latitude" ref="latInput" placeholder="请输入纬度"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="检测开始时间" prop="ncStartTime">
              <el-date-picker clearable
                              v-model="form.ncStartTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择检测开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="检测结束时间" prop="ncEndTime">
              <el-date-picker clearable
                              v-model="form.ncEndTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择检测结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="核酸点状态">
              <el-radio-group v-model="form.ncStatus">
                <el-radio
                  v-for="dict in dict.type.nc_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
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
import {listArea, getArea, delArea, addArea, updateArea, getCollectionList, getQrCode} from "@/api/system/area";

export default {
  name: "Area",
  dicts: ['nc_status'],
  data() {
    return {
      // 二维码url
      qrCodeUrl: '',
      // 检测点列表
      collectionList: [],
      // 检测点Id
      NCA_ID: 0,
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
      // 核酸点管理表格数据
      areaList: [],
      // 弹出层标题
      title: "",
      // 生成二维码标题
      QrCodetitle: "",
      // 是否显示弹出层
      open: false,
      // 显示生成二维码弹出层
      QrCodeOpen: false,
      // 二维码弹出层
      CodeOpen: false,
      // 二维码标题
      CodeTitle: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        county: null,
        town: null,
        community: null,
        address: null,
        logitude: null,
        latitude: null,
        ncStartTime: null,
        ncEndTime: null,
        ncStatus: null,
        queueNumber: null,
        creator: null,
        modifyTime: null,
        modifier: null
      },
      // 表单参数
      form: {
        logitude: '',
        latitude: '',
      },
      // 表单校验
      rules: {
        town: [
          {required: true, message: "街道/镇不能为空", trigger: "blur"}
        ],
        community: [
          {required: true, message: "社区/村不能为空", trigger: "blur"}
        ],
        address: [
          {required: true, message: "具体位置不能为空", trigger: "blur"}
        ],
        logitude: [
          {required: true, message: "经度不能为空", trigger: "blur"}
        ],
        latitude: [
          {required: true, message: "纬度不能为空", trigger: "blur"}
        ],
      },
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
      map.addEventListener('click', function (e) {
        that.form.logitude = e.point.lng
        that.form.latitude = e.point.lat
      });
    },
    /** 查询核酸点管理列表 */
    getList() {
      this.loading = true;
      listArea(this.queryParams).then(response => {
        this.areaList = response.rows;
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
        ncaId: null,
        county: null,
        town: null,
        community: null,
        address: null,
        logitude: null,
        latitude: null,
        ncStartTime: null,
        ncEndTime: null,
        ncStatus: "0",
        queueNumber: null,
        createTime: null,
        creator: null,
        modifyTime: null,
        modifier: null
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
      this.ids = selection.map(item => item.ncaId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加核酸点管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ncaId = row.ncaId || this.ids
      getArea(ncaId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改核酸点管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.ncaId != null) {
            updateArea(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArea(this.form).then(response => {
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
      const ncaIds = row.ncaId || this.ids;
      this.$modal.confirm('是否确认删除核酸点管理编号为"' + ncaIds + '"的数据项？').then(function () {
        return delArea(ncaIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/area/export', {
        ...this.queryParams
      }, `area_${new Date().getTime()}.xlsx`)
    },
    /**
     * 打开生成二维码操作窗口
     */
    openQRCode() {
      this.QrCodeOpen = true;
      this.QrCodetitle = "生成检测点二维码";
      getCollectionList().then(res => {
        this.collectionList = res.data;
      })
    },
    chooseValue() {
      this.NCA_ID = this.collectionList.nca_id;
    },
    obtainQrCode() {

      this.CodeOpen = true;
      this.CodeTitle = "检测点二维码";
      getQrCode(this.NCA_ID).then(res => {
        const url = window.URL.createObjectURL(res);
        this.qrCodeUrl = url;
      })
    }
  }
};
</script>
<style>
.bm-view {
  width: 100%;
  height: 500px;
}
</style>
