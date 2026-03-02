## 2026.3.2

### 目前实现的所有接口

1. #### **Halos请求样本信息**

>URL:http://172.18.4.89:8030/his/V3/sample/get/{oldSampleNum}
>
>必需字段：
>
>1. oldSampleNum（原样本编号）

2. #### **Halos推送JSON结果数据**

>URL:http://172.18.4.89:8030/his/V3/result/pushResult
>
>必需字段：
>
>1. sampleId(样本编号)
>2. poolingSubId(子文库号)
>3. slideId(芯片号)
>4. laneId(Lane ID)
>5. dnbId(DNB ID)
>6. sampleType(样本类型)
>7. shipmentCondition(运输条件)
>8. collectDate(取样日期)
>9. receivedDate(收样日期)
>10. gestationalWeeks（孕周）
>11. fetusType(胎儿类型)
>12. tubeType(管道类型)
>13. oldSampleNum(原样本编号)

3. #### **Halos推送PDF报告文件**

>URL:http://172.18.4.89:8030/his/V3/result/pushPdfReport
>
>必需传递请求参数：
>file(multipart/form-data的key)
>
>PDF报告文件默认存储路径为：D:/fileStorage/{yyyy/MM/dd}/pdf，根路径（D:/fileStorage）可在配置文件里修改

4. #### 推送WORD报告文件

>URL:http://172.18.4.89:8030/his/V3/result/pushWordReport
>
>必需传递请求参数：
>file(multipart/form-data的key)
>
>word报告文件默认存储路径为：D:/fileStorage/{yyyy/MM/dd}/word，根路径（D:/fileStorage）可在配置文件里修改



### 其他非文档要求的已实现接口

#### 1. 样本表接口

| 接口说明       | 请求方法 | 接口URL                                                  |
| -------------- | -------- | -------------------------------------------------------- |
| 新增样本信息   | POST     | `http://172.18.4.89:8030/his/V3/lis/sample/add`          |
| 修改样本信息   | PUT      | `/his/V3/lis/sample/update`                              |
| 删除样本信息   | DELETE   | `http://172.18.4.89:8030/his/V3/lis/sample/delete/{oid}` |
| 根据ID查询样本 | GET      | `http://172.18.4.89:8030/his/V3/lis/sample/get/{oid}`    |
| 分页查询样本   | POST     | `http://172.18.4.89:8030/his/V3/lis/sample/list`         |

#### 2. 患者表接口

| 接口说明       | 请求方法 | 接口URL                                                   |
| -------------- | -------- | --------------------------------------------------------- |
| 新增患者信息   | POST     | `http://172.18.4.89:8030/his/V3/lis/patient/add`          |
| 修改患者信息   | PUT      | `http://172.18.4.89:8030/his/V3/lis/patient/update`       |
| 删除患者信息   | DELETE   | `http://172.18.4.89:8030/his/V3/lis/patient/delete/{oid}` |
| 根据ID查询患者 | GET      | `http://172.18.4.89:8030/his/V3/lis/patient/get/{oid}`    |
| 分页查询患者   | POST     | `http://172.18.4.89:8030/his/V3/lis/patient/list`         |

#### 3. 检查表接口

| 接口说明       | 请求方法 | 接口URL                                                      |
| -------------- | -------- | ------------------------------------------------------------ |
| 新增检查信息   | POST     | `http://172.18.4.89:8030/his/V3/lis/examination/add`         |
| 修改检查信息   | PUT      | `http://172.18.4.89:8030/his/V3/lis/examination/update`      |
| 删除检查信息   | DELETE   | `http://172.18.4.89:8030/his/V3/lis/examination/delete/{oid}` |
| 根据ID查询检查 | GET      | `http://172.18.4.89:8030/his/V3/lis/examination/get/{oid}`   |
| 分页查询检查   | POST     | `http://172.18.4.89:8030/his/V3/lis/examination/list`        |

### 其他

1. log文件配置路径D:/service/BGI/node8030/log
2. 单个日志文件最大10MB。超过这个大小，即使还在同一天，也会立即创建一个新的日志文件（序号`%i`会+1）。
3. 最多保留**30天**的日志文件。30天前的文件会被自动删除。
4. 所有日志文件总大小上限为1GB。当总大小超过1GB时，会删除最旧的日志文件，直到总大小低于1GB。
5. INFO_FILE包含**INFO级别及以上** (包括 INFO, WARN, ERROR) 的所有日志
6. ERROR_FILE专门记录 ERROR 级别的严重错误日志
7. 目前样本文件与样本数据的映射依靠样本文件名（以样本编号命名）