import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  FooterToolbar,
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import {Button, Drawer, message, Space, Table} from 'antd';
import React, { useRef, useState } from 'react';
import UpdateForm from './components/UpdateForm';
import {
  addInterfaceInfoUsingPost, deleteInterfaceInfoUsingPost,
  listInterfaceInfoByPageUsingGet, updateInterfaceInfoUsingPost
} from "@/services/yiapi-backend/interfaceInfoController";
import type {SortOrder} from "antd/lib/table/interface";
import CreateForm, {FormValueType} from "@/pages/Admin/InterfaceInfo/components/CreateForm";




const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  const [selectedRowsState, setSelectedRows] = useState<API.InterfaceInfo[]>([]);

  /**
   * @en-US Add node
   * @zh-CN 添加接口
   * @param fields
   */
  const handleAdd = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('正在添加');
    try {
      await addInterfaceInfoUsingPost({
        ...fields,
      });
      hide();
      message.success('添加成功');
      handleModalOpen(false)
      //自动更新表单
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('Adding failed, please try again!');
      return false;
    }
  };

  /**
   * @en-US Update interface
   * @zh-CN 更新接口
   *
   * @param fields
   */
  const handleUpdate = async (fields: FormValueType) => {
    //如果没有选中，直接返回
    if(!currentRow){
      return ;
    }
    const hide = message.loading('修改中');
    try {
      await updateInterfaceInfoUsingPost({
        id:currentRow.id,
        ...fields
      });
      hide();
      message.success('修改成功');
      //自动更新表单
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('Configuration failed, please try again!');
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 删除接口
   * @param selectedRows
   */
  const handleRemove = async (selectedRows: API.InterfaceInfo) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;
    try {
      await deleteInterfaceInfoUsingPost({
        id:selectedRows.id
      });
      hide();
      message.success('删除成功');
      //自动更新表单
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('Delete failed, please try again');
      return false;
    }
  };

  const columns: ProColumns<API.InterfaceInfo>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'text',
      search:false,
      hideInForm:true,
      width: 48,
    },
    {
      title: '接口名称',
      dataIndex: 'name',
      valueType: 'text',
      formItemProps: {
        rules:[{
          required:true,
          //不设置就是默认提示
          //message:''
        }]
      }
    },
    {
      title: '描述',
      dataIndex: 'description',
      valueType: 'textarea',
      //显示缩略
      ellipsis: true,
    },
    {
      title: '请求方法',
      dataIndex: 'method',
      valueType: 'select',
      valueEnum: {
        'get': {text: 'GET'},
        'post': {text: 'POST'},
        'delete': {text: 'DELETE'},
        'put': {text: 'PUT'},
        'patch': {text: 'PATCH'},
        'head': {text: 'HEAD'},
        'options': {text: 'OPTIONS'},
      },
      formItemProps: {
        rules:[{
          required:true,
          //不设置默认提示
          //message:''
        }]
      }
    },
    {
      title: '接口地址',
      dataIndex: 'url',
      valueType: 'textarea',
      formItemProps: {
        rules:[{
          required:true,
          //不设置默认提示
          //message:''
        }]
      }
    },
    {
      title: '请求头',
      dataIndex: 'requestHeader',
      valueType: 'textarea',
      formItemProps: {
        rules:[{
          required:true,
        }]
      }
    },
    {
      title: '响应头',
      dataIndex: 'responseHeader',
      valueType: 'textarea',
      formItemProps: {
        rules:[{
          required:true,
        }]
      }
    },
    {
      title: '状态',
      dataIndex: 'status',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '关闭',
          status: 'Default',
        },
        1: {
          text: '开启',
          status: 'Processing',
        },
        // 2: {
        //   text: '已上线',
        //   status: 'Success',
        // },
        // 3: {
        //   text: '异常',
        //   status: 'Error',
        // },
      },
    },
    {
      title:'创建时间',
      dataIndex:'createTime',
      valueType:"dateTime",
      hideInSearch:true
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateRange',
      hideInTable: true,
      hideInForm: true,
      search: {
        transform: (value) => {
          return {
            startTime: value[0],
            endTime: value[1],
          };
        },
      },
    },
    {
      title:'更新时间',
      dataIndex:'updateTime',
      valueType:"dateTime",
      hideInForm:true,
      hideInSearch:true
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleUpdateModalOpen(true);
            setCurrentRow(record);
          }}
        >
          修改
        </a>,
        <a key="config" onClick={()=>{
          handleRemove(record);
        }}>
          删除
        </a>,
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.InterfaceInfo, API.PageParams>
        headerTitle={'接口列表'}
        actionRef={actionRef}
        rowKey="key"
        pagination={{
          pageSize: 5,
          onChange: (page) => console.log(page),
        }}
        search={{
          labelWidth: 'auto',
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalOpen(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={ async (params: U & {
          pageSize?: number;
          current?: number;
          keyword?: string;
        }, sort: Record<string, SortOrder>, filter: Record<string, (string | number)[] | null>)=>{
          const res=await listInterfaceInfoByPageUsingGet({
            ...params
          })
         if (res.data){
           return{
             data: res.data.records || [],
             success: true,
             total: res.data.total,
        }}
        }}
        columns={columns}

      />


      <UpdateForm
        columns={columns}
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalOpen(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
       visible={updateModalOpen}
        values={currentRow || {}}
      />

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.id && (
          <ProDescriptions<API.InterfaceInfo>
            title={currentRow?.id}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<API.InterfaceInfo>[]}
          />
        )}
      </Drawer>

      {/* 创建一个CreateModal组件，用于在点击新增按钮时弹出 */}
      <CreateForm
        columns={columns}
        // 当取消按钮被点击时,设置更新模态框为false以隐藏模态窗口
        onCancel={() => {
          handleModalOpen(false);
        }}
        // 当用户点击提交按钮之后，调用handleAdd函数处理提交的数据，去请求后端添加数据(这里的报错不用管,可能里面组件的属性和外层的不一致)
        onSubmit={(values) => {
          handleAdd(values);
        }}
        // 根据更新窗口的值决定模态窗口是否显示
        visible={createModalOpen}
      />
    </PageContainer>
  );
};
export default TableList;
