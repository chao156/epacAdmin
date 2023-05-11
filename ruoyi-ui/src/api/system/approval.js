import request from '@/utils/request'

// 查询外出申请列表
export function listApproval(query) {
  return request({
    url: '/system/approval/list',
    method: 'get',
    params: query
  })
}

// 通过外出申请
export function handleApply(outIds) {
  return request({
    url: '/system/approval/handleApply/'+outIds,
    method: 'get',
  })
}

//驳回外出申请
export function Reject(outIds) {
  return request({
    url: '/system/approval/handleReject/'+outIds,
    method: 'get',
  })
}


