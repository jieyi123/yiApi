
import ReactECharts from 'echarts-for-react';
import {listTopInvokeInterfaceInfoUsingGet} from "@/services/yiapi-backend/analysisController";
import {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-components";

/**
 * 接口分析
 * @constructor
 */
const InterfaceAnalysis: React.FC = () => {
  const [data, setData] = useState<API.InterfaceInfoVO[]>([]);

  useEffect(() => {
    try {
      listTopInvokeInterfaceInfoUsingGet().then(res => {
        if (res.data) {
          setData(res.data);
        }
      })
    } catch (e: any) {

    }
    // todo 从远程获取数据
  }, [])

// 映射：{ value: 1048, name: 'Search Engine' },
  const chartData = data.map(item => {
    return {
      value: item.totalNum,
      name: item.description,
    }
  })

  // ECharts图表的配置选项
  const option = {
    title: {
      text: '调用接口次数TOP5',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '80%',
        data: chartData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  return (
    <PageContainer >
      {/* 使用 ReactECharts 组件，传入图表配置 */}
      <ReactECharts
                    option={option} />
    </PageContainer>
  );
};
export default InterfaceAnalysis;
