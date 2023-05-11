import request from '@/utils/request'

// 查询社区管理列表
export function listComm(query) {
  return request({
    url: '/system/comm/list',
    method: 'get',
    params: query
  })
}

// 查询社区管理详细
export function getComm(commId) {
  return request({
    url: '/system/comm/' + commId,
    method: 'get'
  })
}

// 新增社区管理
export function addComm(data) {
  return request({
    url: '/system/comm',
    method: 'post',
    data: data
  })
}
//将地区管理中的社区信息导入到社区管理中
export function importComm(){
  return request({
    url: '/system/comm/',
    method:'get',

  })
}
// 修改社区管理
export function updateComm(data) {
  return request({
    url: '/system/comm',
    method: 'put',
    data: data
  })
}

// 删除社区管理
export function delComm(commId) {
  return request({
    url: '/system/comm/' + commId,
    method: 'delete'
  })
}
