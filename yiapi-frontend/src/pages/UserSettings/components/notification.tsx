import {Button, Descriptions, message, Space, Typography} from 'antd';
import React, {Fragment, useState} from 'react';
import ProCard from "@ant-design/pro-card";
import {genKeyUsingPost, getKeyUsingGet} from "@/services/yiapi-backend/userController";
import {requestConfig} from "@/requestConfig";
const { Paragraph } = Typography;
const NotificationView: React.FC = () => {

  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<API.UserDevKeyVO>();

  const loadData = async () => {
    setLoading(true);
    try {
      const res = await getKeyUsingGet();
      // console.log(res);
      // console.log(res.data);
      setData(res.data);
    } catch (e: any) {
      message.error('获取数据失败，' + e.message);
    }
    setLoading(false);
    return;
  };

  const genKey = async () => {
    try {
      const res = await genKeyUsingPost();
      setData(res.data);
    } catch (e: any) {
      message.error('获取数据失败，' + e.message);
    }
  };

  const getSdk = () => {
    window.location.href = requestConfig.baseURL + '/api/sdk';
  };
  const showDevKey = () => {
    loadData();
  };

  return (
    <ProCard
      headerBordered={false}
      extra={
        <>
          <Space>
            <Button onClick={getSdk}>下载SDK</Button>
            <Button onClick={showDevKey}>显示密钥</Button>
            <Button onClick={genKey}>重新生成</Button>
          </Space>
        </>
      }
    >
      <Descriptions column={1} bordered size="small" layout="vertical">
        <Descriptions.Item label="accessKey">
          <Paragraph copyable={{ tooltips: false }}>
            {data?.accessKey ?? '******************'}
          </Paragraph>
        </Descriptions.Item>
        <Descriptions.Item label="secretKey">
          <Paragraph copyable={{ tooltips: false }}>
            {data?.secretKey ??'******************'}
          </Paragraph>
        </Descriptions.Item>
      </Descriptions>
    </ProCard>
  );
};

export default NotificationView;
