// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** upload POST /api/upload */
export async function uploadUsingPost(body: string, options?: { [key: string]: any }) {
  return request<API.BaseResponsestring>('/api/upload', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
