import request from '@/utils/request'

// 查询物资审核列表
export function listApply(query) {
  return request({
    url: '/system/apply/list',
    method: 'get',
    params: query
  })
}

// 查询物资审核详细
export function getApply(itemApplyId) {
  return request({
    url: '/system/apply/' + itemApplyId,
    method: 'get'
  })
}
// 通过物资申请
export function handleItemApply(itemIds) {
  return request({
    url: '/system/apply/handleItemApply/'+itemIds,
    method: 'get',
  })
}

//驳回物资申请
export function RejectItem(itemIds) {
  return request({
    url: '/system/apply/rejectItem/'+itemIds,
    method: 'get',
  })
}



