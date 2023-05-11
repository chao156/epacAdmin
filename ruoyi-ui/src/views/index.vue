<template>
  <div class="app-container home">
    <el-divider />
    <body>
    <baidu-map :center="center" :zoom="zoom"  :position="{lng: 116.404, lat: 39.915}" @ready="handler" :scroll-wheel-zoom="true" class="bm-view">
      <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
      <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
      <bm-point-collection :points="points" shape="BMAP_POINT_SHAPE_WATERDROP" color="red" size="BMAP_POINT_SIZE_HUGE" @click="clickHandler"></bm-point-collection>
    </baidu-map>
    </body>
  </div>
</template>
<style>
.bm-view {
  width: 100%;
  height: 800px;
}
</style>
<script>
import {getPosition} from "@/api/system/area";
export default {
  name: "Index",
  data() {
    return {
      center:'长清区',
      zoom: 17,
      points:[]
    };
  },
  created() {
    const _this = this
    getPosition().then(response => {
      const points = [];
      response.forEach(function(e){
        const position = {lng:e.logitude,lat:e.latitude,name:e.name}
        points.push(position)
      });
      _this.points = points
      console.log(_this.points[0])
    });
  },
  methods: {
    handler ({BMap, map}) {
      map.removeOverlay();//移除覆盖物
      for(var i=0;i<this.points.length;i++) {
        var point = new BMap.Point(this.points[i].lng,this.points[i].lat);
        const marker = new BMap.Marker(point);
        map.addOverlay(marker);
      }
    },
    clickHandler(e){
      alert("aa");
    }

  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

