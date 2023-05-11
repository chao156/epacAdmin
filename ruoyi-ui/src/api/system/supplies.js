import request from '@/utils/request'

// 查询物资管理列表
export function listSupplies(query) {
  return request({
    url: '/system/supplies/list',
    method: 'get',
    params: query
  })
}

// 查询物资管理详细
export function getSupplies(itemId) {
  return request({
    url: '/system/supplies/' + itemId,
    method: 'get'
  })
}

// 新增物资管理
export function addSupplies(data) {
  return request({
    url: '/system/supplies',
    method: 'post',
    data: data
  })
}

// 修改物资管理
export function updateSupplies(data) {
  return request({
    url: '/system/supplies',
    method: 'put',
    data: data
  })
}

// 删除物资管理
export function delSupplies(itemId) {
  return request({
    url: '/system/supplies/' + itemId,
    method: 'delete'
  })
}
