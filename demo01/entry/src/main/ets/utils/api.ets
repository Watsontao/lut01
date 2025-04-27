import { myRequest } from './HttpUtils';
import { http } from '@kit.NetworkKit';
import { User } from '../model/User'
import { Task } from '../model/Task'
import type { Greenhouse } from '../model/Greenhouse';
import type { Crop } from '../model/Crop';
import type { EnvironmentData } from '../model/EnvironmentData';



export interface LoginRequest {
  phone: string;
  password: string;
}

export interface LoginData {
  token: string;
  user: User;
}

export interface LoginResp {
  code: string;
  msg: string;
  data: LoginData;
}



// 登录接口封装
export function loginApi(phone: string, pwd: string): Promise<LoginResp> {
  const body: LoginRequest = {
    phone: phone,
    password: pwd
  };

  return myRequest<LoginResp, LoginRequest>('/api/harmony/user/login', http.RequestMethod.POST, body);
}




/**根据用户id获取greenhouse信息
 * 同时springboot那边还要根据根据用户id获取greenhouse信息信息获取environment信息
 * ----->返回greenhouse的信息 以及大棚对应的nvironment信息
 */


interface ResponseData<T> {
  code: string;
  msg: string;
  data: T;
}


export function getGreenhouseByUser(userId: number): Promise<Greenhouse[]> {
  return myRequest<ResponseData<Greenhouse[]>, null>(
    `/api/harmony/greenhouse/getByUserId?userId=${userId}`,
    http.RequestMethod.GET,
    null
  ).then(res => {
    if (res.code === '200') {
      return res.data;
    } else {
      // 你可以自定义错误对象，也可以直接抛出msg
      throw new Error(res.msg);
    }
  });
}



/**
 * 根据用户查询Task信息
 * 返回tasklist
 */

export function getTaskByUser(userId: number): Promise<Task[]> {
  return myRequest<ResponseData<Task[]>, null>(
    `/api/harmony/task/getByUserId?userId=${userId}`,
    http.RequestMethod.GET,
    null
  ).then(res => {
    if (res.code === '200') {
      return res.data
    } else {
      throw new Error(res.msg)
    }
  })
}


// 根据任务 id 标记完成
export function finishTask(id: number): Promise<void> {
  return myRequest<ResponseData<null>, null>(
    `/api/harmony/task/finish/${id}`,
    http.RequestMethod.POST,
    null
  ).then(res => {
    if (res.code === '200') return
    throw new Error(res.msg)
  })
}



/**
 * 修改密码 DTO
 */
export interface ChangePwdReq {
  oldPassword: string;
  newPassword: string;
}

/**
 * 修改密码接口
 * */
export function changePassword(oldPwd: string, newPwd: string): Promise<void> {
  const body: ChangePwdReq = { oldPassword: oldPwd, newPassword: newPwd };
  return myRequest<ResponseData<null>, ChangePwdReq>(
    `/api/harmony/user/changePassword`,
    http.RequestMethod.POST,
    body
  ).then(res => {
    if (res.code === '200') return;
    throw new Error(res.msg);
  });
}



/** 通用返回格式 */
interface ResponseDataCrop<T> {
  code: string;
  msg: string;
  data: T;
}

/** 获取所有作物 */
export function getAllCrops(): Promise<Crop[]> {
  return myRequest<ResponseDataCrop<Crop[]>, null>(
    `/api/harmony/crop/getAll`,
    http.RequestMethod.GET,
    null
  ).then(res => {
    if (res.code === '200') return res.data;
    throw new Error(res.msg);
  });
}

/** 根据 ID 获取单个作物 */
export function getCropById(cropId: number): Promise<Crop> {
  return myRequest<ResponseData<Crop>, null>(
    `/api/harmony/crop/getById?cropId=${cropId}`,
    http.RequestMethod.GET,
    null
  ).then(res => {
    if (res.code === '200') return res.data;
    throw new Error(res.msg);
  });
}



// api.ets
/** 获取某个大棚的环境数据历史 */
export function getEnvironmentHistory(greenhouseId: number): Promise<EnvironmentData[]> {
  return myRequest<ResponseData<EnvironmentData[]>, null>(
    `/api/harmony/environment/history/${greenhouseId}`,
    http.RequestMethod.GET,
    null
  ).then(res => {
    if (res.code === '200') return res.data;
    throw new Error(res.msg);
  });
}


/** 控制大棚状态（灌溉/通风/灯光） */
export function toggleGreenhouseStatus(greenhouseId: number, type: string): Promise<void> {
  return myRequest<ResponseData<null>, null>(
    `/api/harmony/greenhouse/${greenhouseId}/toggle/${type}`,
    http.RequestMethod.POST,
    null
  ).then(res => {
    if (res.code === '200') return;
    throw new Error(res.msg);
  });
}

