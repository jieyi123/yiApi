import { PageContainer } from '@ant-design/pro-components';
import {Button, Card, Descriptions, Divider, Form, message,Input} from 'antd';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import {
  getInterfaceInfoByIdUsingGet,
  invokeInterfaceInfoUsingPost
} from "@/services/yiapi-backend/interfaceInfoController";
import {getUserInterfaceInfoByIdUsingPost} from "@/services/yiapi-backend/userInterfaceInfoController";
import {useModel} from "@umijs/max";
import {result} from "lodash";
import {JSONSchema} from "@typescript-eslint/utils";

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  // 定义状态和钩子函数
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<API.InterfaceInfoVO>();
  const [response, setResponse] = useState<API.BaseResponsestring>();
  // 使用 useParams 钩子函数获取动态路由参数
  const params = useParams();
  const [invokeRes,setInvokeRes] = useState<any>();
  const [invokeLoading,setInvokeLoading] =useState(false);
  const { initialState, setInitialState } = useModel('@@initialState');
  const { loginUser } = initialState;

  const onFinish = async (values: any) => {
    const hide = message.loading('正在调用');
    // 检查是否存在接口id
    if (!params.id) {
      message.error('接口不存在');
      hide();
      return;
    }

    if(data?.leftNum === null || data?.leftNum <=0){
      message.error('接口调用次数不足，请先获取或者购买');
      hide();
      return ;
    }
    setInvokeLoading(true);

    try {
      // 发起接口调用请求，传入一个对象作为参数，这个对象包含了id和values的属性，
      // 其中，id 是从 params 中获取的，而 values 是函数的参数
      const res=await invokeInterfaceInfoUsingPost({
        id: params.id,
        ...values,
      });
      const result=res.data;
      if (result?.includes("403")){
        message.error("无权限");
        setInvokeRes("");
        setInvokeLoading(false);
        return;
      }
      if(res.code === 0){
        setInvokeRes(res.data);
        message.success('请求成功');
      }
    } catch (error: any) {
      message.error(error.message);
    }
    const res = await getInterfaceInfoByIdUsingGet({
      id: Number(params.id),
    });
    // 将获取到的接口信息设置到 data 状态中
    setData(res.data);
    setInvokeLoading(false);
    hide();
  };

  const loadData = async () => {
    // 检查动态路由参数是否存在
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    setLoading(true);
    try {
      // 发起请求获取接口信息，接受一个包含 id 参数的对象作为参数
      const res = await getInterfaceInfoByIdUsingGet({
        id: Number(params.id),
      });
      // 将获取到的接口信息设置到 data 状态中
      setData(res.data);
    } catch (error: any) {
      // 请求失败处理
      message.error('请求失败，' + error.message);
    }
    // 请求完成，设置 loading 状态为 false，表示请求结束，可以停止加载状态的显示
    setLoading(false);
  };

  useEffect(() => {
    loadData();
  }, []);

  const getFreeInterface = async () => {
    setInvokeLoading(true);
    try {
      const res = await getUserInterfaceInfoByIdUsingPost({
        userId: loginUser?.id,
        interfaceInfoId: data?.id,
      });
      if (res.data) {
        message.success('获取调用次数成功');
      } else {
        message.error('获取失败请重试');
      }
    } catch (e:any) {
      message.error('请求失败，' + e.message);
    }
    setInvokeLoading(false);
    loadData();
    return
  };

  return (
    <PageContainer title="查看接口文档">
      <Card>
        {data ? (
          <Descriptions
            title={data.name}
            column={1}
            extra={
              data?.leftNum >=50 ? (
                <Button >购买</Button>
              ) : (
                <Button onClick={getFreeInterface}>获取</Button>
              )
            }
          >
            <Descriptions.Item label="接口状态">{data.status ? '开启' : '关闭'}</Descriptions.Item>
            <Descriptions.Item label="描述">{data.description}</Descriptions.Item>
            {data.leftNum===null ? (
                <>
                  <Descriptions.Item label="接口剩余调用次数">
                    0次
                  </Descriptions.Item>
                </>
              ) :
              <Descriptions.Item label="接口剩余调用次数">
                {data.leftNum}次
              </Descriptions.Item>
            }
            <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
            <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
            <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
            <Descriptions.Item label="响应头">{data.responseHeader}</Descriptions.Item>
            <Descriptions.Item label="请求参数">{data.requestParams}</Descriptions.Item>
            <Descriptions.Item label="创建时间">
              {data.createTime?.substring(0, data.createTime?.indexOf('T'))}
            </Descriptions.Item>
            {/*<Descriptions.Item label="更新时间">{data.updateTime}</Descriptions.Item>*/}
          </Descriptions>
        ) : (
          <>接口不存在</>
        )}
      </Card>
      <Divider />
      <Card title="在线测试" loading={loading}>
        <Form
          name="invoke"
          layout="vertical"
          onFinish={onFinish}
          initialValues={{ userRequestParams: data?.parameterExample }}
        >
          <Form.Item label="请求参数" name="userRequestParams">
            <Input.TextArea style={{ height: '90px' }} />
          </Form.Item>
          <Form.Item wrapperCol={{ span: 16 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider />
      <Card title="返回结果" loading={invokeLoading}>
        <div style={{whiteSpace: 'pre-wrap'}}>{invokeRes}</div>
      </Card>
    </PageContainer>
);
};

export default Index;
