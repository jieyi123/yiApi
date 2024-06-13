import { PageContainer } from '@ant-design/pro-components';
import React, { useEffect, useState } from 'react';
import { List, message } from 'antd';
import {listInterfaceInfoByPageUsingGet} from "@/services/yiapi-backend/interfaceInfoController";
import {Link} from "@umijs/max";

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  // 使用 useState 和泛型来定义组件内的状态
  // 加载状态
  const [loading, setLoading] = useState(false);
  // 列表数据
  const [list, setList] = useState<API.InterfaceInfo[]>([]);
  // 总数
  const [total, setTotal] = useState<number>(0);

  // 定义异步加载数据的函数
  const loadData = async (current = 1, pageSize = 10) => {
    // 开始加载数据，设置 loading 状态为 true
    setLoading(true);
    try {
      // 调用接口获取数据
      const res = await listInterfaceInfoByPageUsingGet({
        current,
        pageSize,
      });
      // 将请求返回的数据设置到列表数据状态中
      setList(res?.data?.records ?? []);
      // 将请求返回的总数设置到总数状态中
      setTotal(res?.data?.total ?? 0);
      // 捕获请求失败的错误信息
    } catch (error: any) {
      // 请求失败时提示错误信息
      message.error('请求失败，' + error.message);
    }
    // 数据加载成功或失败后，设置 loading 状态为 false
    setLoading(false);
  };

  useEffect(() => {
    // 页面加载完成后调用加载数据的函数
    loadData();
  }, []);

  return (
    // 使用 antd 的 PageContainer 组件作为页面容器
    <PageContainer title="在线接口开放平台">
      <List
        className="my-list"
        // 设置 loading 属性，表示数据是否正在加载中
        loading={loading}
        itemLayout="horizontal"
        // 将列表数据作为数据源传递给 List 组件
        dataSource={list}
        // 渲染每个列表项
        renderItem={(item)=>{
          const apiLink=`/interface_info/${item.id}`;
          return(
            <List.Item actions={[<Link key={item.id} to={apiLink}>查看</Link>]}>
              <List.Item.Meta
                title={<Link to={apiLink}>{item.name}</Link>}
                description={item.description}
              >
              </List.Item.Meta>
            </List.Item>
          )
        }}



        // 分页配置
        pagination={{
          // 自定义显示总数
          // eslint-disable-next-line @typescript-eslint/no-shadow
          showTotal(total: number) {
            return '总数：' + total;
          },
          // 每页显示条数
          pageSize: 10,
          // 总数，从状态中获取
          total,
          // 切换页面触发的回调函数
          onChange(page, pageSize) {
            // 加载对应页面的数据
            loadData(page, pageSize);
          },
        }}
      />

    </PageContainer>
  );
};

export default Index;
