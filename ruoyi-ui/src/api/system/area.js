import request from '@/utils/request'

// 查询核酸点管理列表
export function listArea(query) {
  return request({
    url: '/system/area/list',
    method: 'get',
    params: query
  })
}

// 查询核酸点管理详细
export function getArea(ncaId) {
  return request({
    url: '/system/area/' + ncaId,
    method: 'get'
  })
}

// 新增核酸点管理
export function addArea(data) {
  return request({
    url: '/system/area',
    method: 'post',
    data: data
  })
}

// 修改核酸点管理
export function updateArea(data) {
  return request({
    url: '/system/area',
    method: 'put',
    data: data
  })
}

// 删除核酸点管理
export function delArea(ncaId) {
  return request({
    url: '/system/area/' + ncaId,
    method: 'delete'
  })
}

export function getPosition(){
  return request({
    url:'/system/area/getPosition',
    method:'get'
  })
}

export function getCollectionList(){
  return request({
    url:'/system/area/getCollectionList',
    method:'get'
  })
}

export function getQrCode(ncaId){
  return request({
    url:'/system/area/getQrCode/'+ncaId,
    method:'get',
    responseType: "blob"
  })
}
